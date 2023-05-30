package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterAddressDto {

    private String token;
    private String avenue;
    private String neighbourhood;
    private String district;
    private String province;
    private String country;
    private String apartmentNo;
    private String houseNo;
    private String zipCode;
}
