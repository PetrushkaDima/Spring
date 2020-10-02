package com.MySpringApplication.service;

import com.MySpringApplication.model.Message;

public interface MessageService {
    Iterable<Message> getAllMessage();
    void saveMessage(Message message);
    Iterable<Message> getMessageByTag(String filter);
}
