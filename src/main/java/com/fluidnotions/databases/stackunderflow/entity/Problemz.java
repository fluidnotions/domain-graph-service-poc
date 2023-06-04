package com.fluidnotions.databases.stackunderflow.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "problemz")
public class Problemz {

    @Id
    private UUID id;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    private String title;
    private String content;
    private String tags;

//    @OneToMany(mappedBy = "problemz")
//    @OrderBy("creationTimestamp desc")
//    private List<Solutionz> solutions;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Userz createdBy;

//    public List<Solutionz> getSolutions() {
//        return solutions;
//    }
//
//    public void setSolutions(List<Solutionz> solutions) {
//        this.solutions = solutions;
//    }

    public Userz getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Userz createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
