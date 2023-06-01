package com.bilgeadam.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Comment {
    @Id
    private String commentId;
    private String username;
    private String recipeId;
    private String comment;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate commentDate;
}
