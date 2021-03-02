package com.MySpringApplication.service;

import com.MySpringApplication.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MessageService {
    Page<Message> getAllMessage(Pageable pageable);
    void saveMessage(Message message);
    Page<Message> getMessageByTag(String filter, Pageable pageable);
}
