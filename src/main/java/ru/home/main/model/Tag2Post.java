package ru.home.main.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tag2post")
@Data
public class Tag2Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "tag_id", nullable = false)
    private int tagId;
}