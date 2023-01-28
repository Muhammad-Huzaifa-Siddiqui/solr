package com.huz.solr.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String firstName;

    private String lastName;

    private String company;

    private String address;

    private String city;

    private String county;

    private String state;

    private String zipCode;

    private String email;

    private String web;
}
