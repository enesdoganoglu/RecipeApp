package com.bilgeadam.service;


import com.bilgeadam.dto.request.PasswordChangeDto;
import com.bilgeadam.dto.request.RegisterAddressDto;
import com.bilgeadam.dto.request.UpdateUserInformationRequestDto;
import com.bilgeadam.dto.request.UserProfileUpdateRequestDto;
import com.bilgeadam.dto.response.UserProfileChangePasswordResponseDto;
import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserProfileManagerException;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.IUserProfileMapper;
import com.bilgeadam.rabbitmq.model.RegisterModel;
import com.bilgeadam.rabbitmq.producer.RegisterAddressProducer;
import com.bilgeadam.rabbitmq.producer.RegisterElasticProducer;
import com.bilgeadam.repository.IUserProfileRepository;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {
    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final CacheManager cacheManager;

    private final RegisterAddressProducer registerAddressProducer;

    private final RegisterElasticProducer registerElasticProducer;
    private final IUserManager userManager;
    private final PasswordEncoder passwordEncoder;
    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenProvider jwtTokenProvider, CacheManager cacheManager, RegisterAddressProducer registerAddressProducer, RegisterElasticProducer registerElasticProducer, IUserManager userManager, PasswordEncoder passwordEncoder) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.cacheManager = cacheManager;
        this.registerAddressProducer = registerAddressProducer;
        this.registerElasticProducer = registerElasticProducer;
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
    }


    @CacheEvict(value = "find-by-role", allEntries = true)
    public Boolean createUserWithRabbitMq(RegisterModel model){
        try {
            UserProfile userProfile = save(IUserProfileMapper.INSTANCE.fromRegisterModelToUserProfile(model));
            registerElasticProducer.sendNewUser(IUserProfileMapper.INSTANCE.fromUserProfileToElasticModel(userProfile));
            cacheManager.getCache("findAll").clear();
            return true;
        }catch (Exception e){
            throw new RuntimeException("Beklenmeyen bir hata oluştu.");
        }
    }

    @Cacheable(value = "findAll")
    public List<UserProfile> findAll() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return userProfileRepository.findAll();
    }


    public UserProfile createAddressWithRabbitMq(RegisterAddressDto dto){
        Optional<Long> userId = jwtTokenProvider.getIdFromToken(dto.getToken());
        if (userId.isEmpty()){
            throw new UserProfileManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUserId(userId.get());
        if (userProfile.isEmpty()){
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }

        save(IUserProfileMapper.INSTANCE.createFromAddressDtoToUserProfile(dto, userProfile.get()));
        registerAddressProducer.sendNewUserAddress(IUserProfileMapper.INSTANCE.fromUserProfileToRegisterAddressModel(userProfile.get()));
        return userProfile.get();
    }

    public Boolean passwordChange(PasswordChangeDto dto){
        Optional<Long> userId = jwtTokenProvider.getIdFromToken(dto.getToken());
        if (userId.isEmpty()){
            throw new UserProfileManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUserId(userId.get());
        if (userProfile.isPresent()){
            if (passwordEncoder.matches(dto.getOldPassword(), userProfile.get().getPassword())){
                String newPass = dto.getNewPassword();
                userProfile.get().setPassword(passwordEncoder.encode(newPass));
                userProfileRepository.save(userProfile.get());
                userManager.passwordChange(IUserProfileMapper.INSTANCE.fromUserProfileToUserPasswordChangeDto(userProfile.get()));
                return true;
            }else {
                throw new UserProfileManagerException(ErrorType.PASSWORD_ERROR);
            }
        }else {
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }
    }


    public Boolean activateStatus(Long userId){
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUserId(userId);
        if (userProfile.isEmpty()){
            throw new RuntimeException("User id bulunamadı");
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }

    //cache delete yerine cache'i update etmeye yaramaktadır
    //oluşan değişiklikler sonucunda cache'in update edilmesini sağlar
    @CachePut(value = "find-by-username", key = "#dto.username.toLowerCase()")
    public UserProfile update(UserProfileUpdateRequestDto dto){
        Optional<Long> userId = jwtTokenProvider.getIdFromToken(dto.getToken());
        if (userId.isEmpty()){
            throw new UserProfileManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUserId(userId.get());
        if (userProfile.isEmpty()){
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }
        //cache delete
        cacheManager.getCache("find-by-username").evict(userProfile.get().getUsername().toLowerCase());

        UpdateUserInformationRequestDto updateUserInformationRequestDto = IUserProfileMapper.INSTANCE.toUpdateUserInformation(dto);
        updateUserInformationRequestDto.setUserId(userId.get());
        userManager.updateUsernameOrEmail(updateUserInformationRequestDto);
        update(IUserProfileMapper.INSTANCE.updateFromDtoToUserProfile(dto, userProfile.get()));


        return userProfile.get();
    }

    public Boolean delete(Long userId){
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUserId(userId);
        if (userProfile.isEmpty()) {
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.DELETED);
        update(userProfile.get());
        return true;
    }

    @Cacheable(value = "find-by-username", key = "#username.toLowerCase()")
    public UserProfile findByUsername(String username){
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUsernameIgnoreCase(username);
        if (userProfile.isEmpty()){
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }
        return userProfile.get();
    }

    @Cacheable(value = "find-by-role", key = "#role.toUpperCase()")  //USER, ADMIN
    public List<UserProfile> findByRole(String role){
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //auth manager
        List<Long> userIds = userManager.findByRole(role).getBody();
        return userIds.stream().map(
                        x -> userProfileRepository.findOptionalByUserId(x)
                                .orElseThrow(() -> {throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);}))
                .collect(Collectors.toList());
    }


    //for recipe-service
    public UserProfileResponseDto findByUserProfileDto(Long userId){
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUserId(userId);
        if (userProfile.isEmpty()){
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }
        UserProfileResponseDto userProfileResponseDto = IUserProfileMapper.INSTANCE.fromUserProfileToResponseDto(userProfile.get());
        return userProfileResponseDto; //return userId, username, avatar

    }


    public Boolean forgotPassword(UserProfileChangePasswordResponseDto dto){
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUserId(dto.getUserId());
        if (userProfile.isEmpty()){
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setPassword(dto.getPassword());
        update(userProfile.get());
        return true;
    }
}
