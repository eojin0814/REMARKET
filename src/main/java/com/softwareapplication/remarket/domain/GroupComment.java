package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Builder
@Getter
@Setter
@DynamicInsert
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class GroupComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id; //공동 구매 댓글 id

    @Column(name = "comment_content", nullable = false)
    private String CommentContent; //댓글 내용

    @Column(name = "group_id", nullable = false)
    private Long groupId; //공동구매 게시글 id

    @Column(name = "user_id", nullable = false)
    private Long userId; //작성자 id
}