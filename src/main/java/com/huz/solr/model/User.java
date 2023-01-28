package com.huz.solr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.persistence.Column;
import java.io.Serializable;

@SolrDocument(collection = "user_rec")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {


    @Id
    @Indexed(name = "id", type = "long")
    private long id;

    @Indexed(name = "uuid", type = "string")
    private String uuid;

    @Indexed(name = "first_name", type = "string")
    private String firstName;

    @Indexed(name = "last_name", type = "string")
    private String lastName;

    @Indexed(name = "company", type = "string")
    private String company;

    @Indexed(name = "address", type = "string")
    private String address;

    @Indexed(name = "city", type = "string")
    private String city;

    @Indexed(name = "county", type = "string")
    private String county;

    @Indexed(name = "state", type = "string")
    private String state;

    @Indexed(name = "zip_code", type = "string")
    private String zipCode;

    @Indexed(name = "email", type = "string")
    private String email;

    @Indexed(name = "web", type = "string")
    private String web;
}
