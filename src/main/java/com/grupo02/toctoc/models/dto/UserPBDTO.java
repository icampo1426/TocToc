package com.grupo02.toctoc.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPBDTO {

    private String avatar;
    private String collectionId;
    private String collectionName;
    private String created;
    private String updated;
    private String email;
    private boolean emailVisibility;
    private boolean verified;
    private String id;
    private String name;
    private String username;
}
