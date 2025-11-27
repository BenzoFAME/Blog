package com.example.simpleblog.Service;

import com.example.simpleblog.Models.Message;
import com.example.simpleblog.Models.User;
import com.example.simpleblog.Repository.MessageRepository;
import com.example.simpleblog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }
    public List<User> getChatPartners(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        return messageRepository.findAllChatPartners(user);
    }
    public List<Message> getChat (String user1 , Long userId2) {
        User u1 = userRepository.findByUsername(user1).orElseThrow(()-> new RuntimeException("User not found"));
        User u2 = userRepository.findById(userId2).orElseThrow(()-> new RuntimeException("User not found"));
        return messageRepository.findByFromUserAndToUserOrFromUserAndToUserOrderByTimestamp(u1 , u2 , u2 , u1);
    }
    public void sendMessage(String fromUsername, Long toUserid, String text) {
        User fromUser = userRepository.findByUsername(fromUsername).orElseThrow(()-> new RuntimeException("User not found"));
        User toUser = userRepository.findById(toUserid).orElseThrow(()-> new RuntimeException("User not found"));
        Message newMessage = new Message();
        newMessage.setFromUser(fromUser);
        newMessage.setToUser(toUser);
        newMessage.setText(text);
        messageRepository.save(newMessage);
    }
}
