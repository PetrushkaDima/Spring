package com.MySpringApplication.service.impl;

import com.MySpringApplication.model.Message;
import com.MySpringApplication.repository.MessageRepository;
import com.MySpringApplication.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Override
    public Iterable<Message> getAllMessage() {
        return messageRepository.findAll();
    }

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public Iterable<Message> getMessageByTag(String filter) {
        return messageRepository.findByTag(filter);
    }
}
