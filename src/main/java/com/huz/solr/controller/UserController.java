package com.huz.solr.controller;

import com.huz.solr.model.User;
import com.huz.solr.service.UserService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchUsers(@RequestParam("searchTerm") String searchTerm,
                                                  @RequestParam("page") int page,
                                                  @RequestParam("size") int size) {
        Page<User> users = userService.searchUsers(searchTerm, page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/sync")
    public ResponseEntity<?> syncUsers() throws SolrServerException, IOException {
        userService.importDataToSolr();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
