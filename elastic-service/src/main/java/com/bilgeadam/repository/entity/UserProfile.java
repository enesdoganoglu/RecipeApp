package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(indexName = "user_profile")
public class UserProfile extends Base implements Serializable{
    @Id
    private String id;
    private Long userId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String avenue;
    private String neighbourhood;
    private String district;
    private String province;
    private String country;
    private String apartmentNo;
    private String houseNo;
    private String zipCode;
    @Builder.Default
    private EStatus status = EStatus.PENDING;


}
