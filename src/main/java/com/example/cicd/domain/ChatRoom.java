package com.example.cicd.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private Date createdDate = new Date();

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate  startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @ManyToOne
    private User initiator;

    @ManyToMany
    private List<User> participants;
}
