package com.huz.solr.listener;

import com.huz.solr.event.UserTxEvent;
import com.huz.solr.model.User;
import com.huz.solr.model.UserTx;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserListener implements ApplicationListener<UserTxEvent> {
    @Autowired
    SolrTemplate solrTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onApplicationEvent(UserTxEvent event) {
        UserTx userTx = event.getUser();
        ModelMapper modelMapper = new ModelMapper();
        User solrUser = modelMapper.map(userTx, User.class);
        saveSolrUserDocument(solrUser);

    }

    private void saveSolrUserDocument(User solrUser){

        solrTemplate.saveBean("user_rec", solrUser);
        solrTemplate.commit("user_rec");
        saveSolrUserInCache(solrUser);
    }
    private void saveSolrUserInCache(User solrUser){
        redisTemplate.opsForValue().set("solrUser:"+solrUser.getEmail(), solrUser);
        log.info(String.format("User : %s saved to cache", solrUser.getUuid()));
    }
}
