package com.fluidnotions.databases.stackunderflow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
//@Table(name = "solutionz")
public class Solutionz {

    @Id
    private UUID id;

    @CreationTimestamp
    private OffsetDateTime creationTimestamp;

    private String content;
    private String category;
    private int voteGoodCount;
    private int voteBadCount;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Userz createdBy;

    @ManyToOne
    @JoinColumn(name = "problemz_id", nullable = false)
    private Problemz problemz;

    public Userz getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Userz createdBy) {
        this.createdBy = createdBy;
    }

    public Problemz getProblemz() {
        return problemz;
    }

    public void setProblemz(Problemz problemz) {
        this.problemz = problemz;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OffsetDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(OffsetDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getVoteGoodCount() {
        return voteGoodCount;
    }

    public void setVoteGoodCount(int voteGoodCount) {
        this.voteGoodCount = voteGoodCount;
    }

    public int getVoteBadCount() {
        return voteBadCount;
    }

    public void setVoteBadCount(int voteBadCount) {
        this.voteBadCount = voteBadCount;
    }

}
