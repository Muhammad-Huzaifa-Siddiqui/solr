package com.huz.solr.repository;

import com.huz.solr.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends SolrCrudRepository<User, Long> {

    @Query("first_name:?0~0.8 OR email:?0~0.8 OR phone_1:?0~0.8 OR first_name:?0 OR email:?0 OR phone_1:?0")
    Page<User> searchUser(String searchTerm, Pageable pageable);
}
