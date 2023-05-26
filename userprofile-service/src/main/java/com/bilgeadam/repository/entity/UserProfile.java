package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class UserProfile extends Base implements Serializable{
    @Id
    private String id;
    private Long userId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;

    @Builder.Default //bir property' ye başlangıç değeri atandığında kullanılır, new'lendiğinde kullanılmaz
    private EStatus status = EStatus.PENDING;

    //follow, follower
    private List<String> follows = new ArrayList<>();  //followId
    private List<String> followers = new ArrayList<>(); //userId
}
