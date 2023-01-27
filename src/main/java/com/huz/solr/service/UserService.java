package com.huz.solr.service;

import com.huz.solr.config.SolrConfig;
import com.huz.solr.model.User;
import com.huz.solr.model.UserTx;
import com.huz.solr.repository.UserRepository;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    String solrUrl = "http://localhost:8983/solr/user_rec";
    HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build();

    public Page<User> searchUsers(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchUser(searchTerm, pageable);
    }

    public void importDataToSolr() throws SolrServerException, IOException {
        List<UserTx> users = jdbcTemplate.query("SELECT * from tbl_user",
                new BeanPropertyRowMapper<>(UserTx.class));
       try{
           for(UserTx DBUser : users){
               SolrInputDocument document = new SolrInputDocument();
               document.addField("id", DBUser.getId());
               document.addField("first_name", DBUser.getFirstName());
               document.addField("last_name", DBUser.getLastName());
               document.addField("company", DBUser.getCompany());
               document.addField("address", DBUser.getAddress());
               document.addField("city", DBUser.getCity());
               document.addField("county", DBUser.getCounty());
               document.addField("state_province", DBUser.getStateProvince());
               document.addField("zip_postal_code", DBUser.getZipPostalCode());
               document.addField("phone_1", DBUser.getPhone1());
               document.addField("phone_2", DBUser.getPhone2());
               document.addField("email", DBUser.getEmail());
               document.addField("web", DBUser.getWeb());
               solrClient.add(document);
           }
           solrClient.commit();
       }finally {
           solrClient.close();
       }
    }
}
