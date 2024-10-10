package com.grupo02.toctoc.DTOs;

import lombok.Data;

@Data
public class UserUpdate {
    private String name;
    private String lastname;
    private String profileImage;
    private String bannerImage;
    private String bio;
    private int gender;

}
