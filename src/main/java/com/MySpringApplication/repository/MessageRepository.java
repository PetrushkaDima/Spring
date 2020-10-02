package com.MySpringApplication.repository;

import com.MySpringApplication.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Iterable<Message> findByTag(String filter);
}
