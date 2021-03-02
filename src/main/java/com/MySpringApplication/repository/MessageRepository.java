package com.MySpringApplication.repository;

import com.MySpringApplication.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;



public interface MessageRepository extends CrudRepository<Message, Long> {
    Page<Message> findByTag(String filter, Pageable pageable);
    Page<Message> findAll(Pageable pageable);
}
