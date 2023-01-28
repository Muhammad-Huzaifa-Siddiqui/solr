package com.huz.solr.repository;

import com.huz.solr.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends SolrCrudRepository<User, Long> {

    @Query("{!boost bf=first_name^2}first_name:?0~0.8 OR {!boost bf=email^1.5}email:?0~0.8 " +
            "OR {!boost bf=last_name^1.5}last_name:?0~0.8 OR first_name:?0 " +
            "OR last_name:?0 OR (email:?0 AND email:*\"?0\"*)")
    Page<User> searchUser(String searchTerm, Pageable pageable);
}
