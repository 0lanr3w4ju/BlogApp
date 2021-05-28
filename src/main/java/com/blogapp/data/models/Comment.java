package com.blogapp.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    public Comment(String commentatorsName, String content) {
        this.setCommenter(commentatorsName);
        this.setContent(content);
    }
    @Id
    @GeneratedValue
    private UUID id;

    private String commenter;
    @Column(nullable = false, length = 150)
    private String content;
    @CreationTimestamp
    private LocalDateTime dateCreated;
}
