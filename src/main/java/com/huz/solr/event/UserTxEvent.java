package com.huz.solr.event;

import com.huz.solr.model.UserTx;
import org.springframework.context.ApplicationEvent;

public class UserTxEvent extends ApplicationEvent {
    private UserTx userTx;
    public UserTxEvent(Object source, UserTx user) {
        super(source);
        this.userTx = user;
    }

    public UserTx getUser() {
        return userTx;
    }
}
