package com.huz.solr.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
@Data
public class UserTx {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "company")
    private String company;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "county")
    private String county;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "zip_postal_code ")
    private String zipPostalCode;

    @Column(name = "phone_1 ")
    private String phone1;

    @Column(name = "phone_2 ")
    private String phone2;

    @Column(name = "email")
    private String email;

    @Column(name = "web")
    private String web;
}
