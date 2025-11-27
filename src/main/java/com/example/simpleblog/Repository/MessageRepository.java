package com.example.simpleblog.Repository;

import com.example.simpleblog.Models.Message;
import com.example.simpleblog.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByFromUserAndToUserOrFromUserAndToUserOrderByTimestamp(
            User from1, User to1, User from2, User to2
    );


    @Query("SELECT DISTINCT m.fromUser FROM Message m WHERE m.toUser = :user " +
            "UNION SELECT DISTINCT m.toUser FROM Message m WHERE m.fromUser = :user")
    List<User> findAllChatPartners(@Param("user") User user);
}
