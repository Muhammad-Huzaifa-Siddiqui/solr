package com.huz.solr.service;

import com.huz.solr.model.User;
import com.huz.solr.model.UserTx;
import com.huz.solr.model.dto.UserDto;
import com.huz.solr.repository.UserRepository;
import com.huz.solr.repository.userepo.UserTxRepository;
import com.huz.solr.transformer.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    Transformer transformer;

    @Autowired
    UserTxRepository userTxRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final String SOLR_URL = "http://localhost:8983/solr/user_rec";

    private static final Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");


    HttpSolrClient solrClient = new HttpSolrClient.Builder(SOLR_URL).build();

    public UserDto addUser(UserTx userTx){
        userTx.setUuid(UUID.randomUUID().toString());
        userTxRepository.save(userTx);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userTx, UserDto.class);
    }

    public Page<UserDto> searchUsers(String searchTerm, int page, int size) {
        ModelMapper modelMapper = new ModelMapper();
        Pageable pageable = PageRequest.of(page, size);
        List<UserDto> userDtos = new ArrayList<>();
        if(pattern.matcher(searchTerm).matches()){
            User cacheUser = (User) redisTemplate.opsForValue().get("solrUser:"+searchTerm);
            UserDto userDto = modelMapper.map(cacheUser, UserDto.class);
            userDtos.add(userDto);
            assert cacheUser != null;
            log.info(String.format("User : %s fetched from cache", cacheUser.getEmail()));
        }else{
            Page<User> users =userRepository.searchUser(searchTerm, pageable);
            log.info("User fetched from solr");
            userDtos = users.getContent().stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
        }
        return  new PageImpl<>(userDtos);
    }

    public void importDataToSolr() throws SolrServerException, IOException {
        List<UserTx> users = userTxRepository.findAll();
       try{
           for(UserTx dBUser : users){
               solrClient.add(transformer.transform(dBUser));
           }
           solrClient.commit();
       }finally {
           solrClient.close();
       }
    }
}
