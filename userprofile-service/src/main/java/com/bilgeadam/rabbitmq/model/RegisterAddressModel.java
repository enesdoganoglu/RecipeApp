package com.bilgeadam.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterAddressModel implements Serializable {

    private Long userId;
    private String avenue;
    private String neighbourhood;
    private String district;
    private String province;
    private String country;
    private String apartmentNo;
    private String houseNo;
    private String zipCode;
}
