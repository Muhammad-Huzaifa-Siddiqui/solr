package com.huz.solr.repository.userepo;

import com.huz.solr.event.UserTxEvent;
import com.huz.solr.model.UserTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

@Repository
public class UserTxRepository{
    @Autowired
    UserTxRepo repository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    public void save(UserTx userTx) {
        repository.save(userTx);
        eventPublisher.publishEvent(new UserTxEvent(this, userTx));
    }
}
