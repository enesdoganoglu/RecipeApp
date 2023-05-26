package com.bilgeadam.service;


import com.bilgeadam.mapper.IUserProfileMapper;
import com.bilgeadam.rabbitmq.model.RegisterModel;
import com.bilgeadam.repository.IUserProfileRepository;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;



@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {
    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public UserProfileService(IUserProfileRepository userProfileRepository,  JwtTokenProvider jwtTokenProvider) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }



    public Boolean createUserWithRabbitMq(RegisterModel model){
        try {
            save(IUserProfileMapper.INSTANCE.fromRegisterModelToUserProfile(model));
            return true;
        }catch (Exception e){
            throw new RuntimeException("Beklenmeyen bir hata oluştu.");
        }
    }




}
