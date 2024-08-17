package com.example.social.Contoller;

import com.example.social.domain.Message;
import com.example.social.domain.View;
import com.example.social.dto.EventType;
import com.example.social.dto.ObjectType;
import com.example.social.repo.MessageRepo;
import com.example.social.util.WsSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepo messageRepo;
    private final BiConsumer<EventType, Message> wsSender;

    @Autowired
    public MessageController(MessageRepo messageRepo, SimpMessagingTemplate messagingTemplate, WsSender wsSender) {
        this.messageRepo = messageRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, View.IdName.class);
    }

    @GetMapping
    @JsonView(View.IdName.class)
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(View.FullMessage.class)
    public Message getMessageById(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message saveMessage(@RequestBody Message message) {
        message.setCreationDateTime(LocalDateTime.now());
        Message updatedMessage = messageRepo.save(message);

        wsSender.accept(EventType.CREATE, updatedMessage);

        return updatedMessage;
    }

    @PutMapping("{id}")
    public Message updateMessage(
            @PathVariable("id") Message messageFromDb,
            @RequestBody Message message) {

        BeanUtils.copyProperties(message, messageFromDb, "id");

        Message updatedMessage = messageRepo.save(messageFromDb);

        wsSender.accept(EventType.UPDATE, updatedMessage);

        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message) {
        messageRepo.delete(message);

        wsSender.accept(EventType.REMOVE, message);

    }

//    @MessageMapping("/changeMessage")
//    @SendTo("/topic/activity")
//    public Message change(Message message) {
//        return messageRepo.save(message);
//    }
}
