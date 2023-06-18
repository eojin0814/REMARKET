package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@DynamicInsert
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CommunityComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id; //커뮤니티 댓글 id

    @Column(name = "comment_content", nullable = false)
    private String commentContent; //댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommunityComment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<CommunityComment> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community; //커뮤니티 게시글 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateCommentContent(String commentContent){
        this.commentContent = commentContent;
    }
    public void updateParent(CommunityComment parent){
        this.parent = parent;
        parent.getChildren().add(this);
    }
}
