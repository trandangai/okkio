package io.okkio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestUserDto {
    private Long id;
    private String username;
    private String status;
    private String password;
    private String oldPassword;
    private String email;
    private int roleId;
    private String avatar;
    private String token;
    private String activationKey;
    private String fullName;
    private String firstName;
    private String lastName;
    private String birthday;
    private String gender;
    private String address;
    private int provinceId;
    private int districtId;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phoneNumber;
    private String socialFacebook;
    private String socialInstagram;
    private String socialTwitter;
}