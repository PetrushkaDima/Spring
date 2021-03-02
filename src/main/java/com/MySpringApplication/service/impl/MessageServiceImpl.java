package com.MySpringApplication.service.impl;

import com.MySpringApplication.model.Message;
import com.MySpringApplication.repository.MessageRepository;
import com.MySpringApplication.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Override
    public Page<Message> getAllMessage(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public Page<Message> getMessageByTag(String filter, Pageable pageable) {
        return messageRepository.findByTag(filter, pageable);
    }
}
