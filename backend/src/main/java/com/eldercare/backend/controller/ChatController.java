package com.eldercare.backend.controller;

import com.eldercare.backend.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        return message;
    }

    @MessageMapping("/chat.hello")
    @SendTo("/topic/messages")
    public ChatMessage sayHello(ChatMessage message) {
        message.setContent("안녕하세요! " + message.getSender() + "님!");
        message.setType("SYSTEM");
        return message;
    }
}