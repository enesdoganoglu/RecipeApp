package com.bilgeadam.mapper;


import com.bilgeadam.dto.request.CreateNewRecipeRequestDto;
import com.bilgeadam.dto.request.UpdateRecipeRequestDto;
import com.bilgeadam.repository.entity.Recipe;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IRecipeMapper {
    IRecipeMapper INSTANCE= Mappers.getMapper(IRecipeMapper.class);

    Recipe toPost(final CreateNewRecipeRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRecipe(UpdateRecipeRequestDto dto, @MappingTarget Recipe recipe);
}
