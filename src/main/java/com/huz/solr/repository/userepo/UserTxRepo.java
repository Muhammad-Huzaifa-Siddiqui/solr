package com.huz.solr.repository.userepo;

import com.huz.solr.model.UserTx;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTxRepo extends JpaRepository<UserTx, Long> {
}
