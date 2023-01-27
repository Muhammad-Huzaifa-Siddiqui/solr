package com.huz.solr.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "user_rec")
public class User {

    @Id
    @Indexed(name = "id", type = "long")
    private long id;

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
}
