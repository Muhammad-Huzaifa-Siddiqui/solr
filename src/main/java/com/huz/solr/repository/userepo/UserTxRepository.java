package com.huz.solr.repository.userepo;

import com.huz.solr.event.UserTxEvent;
import com.huz.solr.model.UserTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@EnableTransactionManagement
public class UserTxRepository{
    @Autowired
    UserTxRepo repository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Transactional
    public void save(UserTx userTx) {
        try{
            repository.save(userTx);
            eventPublisher.publishEvent(new UserTxEvent(this, userTx));
        }catch (Exception e){
            throw new RuntimeException("Rolling Back Transaction...", e);
        }
    }

    public List<UserTx> findAll(){
        return repository.findAll();
    }
}
