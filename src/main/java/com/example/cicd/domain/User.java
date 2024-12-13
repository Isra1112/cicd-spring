package com.example.cicd.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String role; // "ADMIN", "WORKER", "COMPANY"

//    @OneToMany(mappedBy = "initiator")
//    @JsonManagedReference
//    private List<ChatRoom> chatRooms;

    @OneToMany(mappedBy = "sender")
//    @JsonManagedReference
    @JsonIgnore
    private List<ChatMessage> chatMessages = new ArrayList<>();
}
