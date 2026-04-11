package com.enotes.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "questionnaires")
public class Questionnaire {
    
    @Id
    private String id;
    
    @DBRef
    private UserDtls userDtls;
    
    private Integer totalScore;
    
    private Integer wellnessScore;
    
    private String assessment;
    
    private LocalDateTime createdAt;
    
    public Questionnaire() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Questionnaire(UserDtls userDtls, Integer totalScore, Integer wellnessScore, String assessment) {
        this.userDtls = userDtls;
        this.totalScore = totalScore;
        this.wellnessScore = wellnessScore;
        this.assessment = assessment;
        this.createdAt = LocalDateTime.now();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public UserDtls getUserDtls() {
        return userDtls;
    }
    
    public void setUserDtls(UserDtls userDtls) {
        this.userDtls = userDtls;
    }
    
    public Integer getTotalScore() {
        return totalScore;
    }
    
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
    
    public Integer getWellnessScore() {
        return wellnessScore;
    }
    
    public void setWellnessScore(Integer wellnessScore) {
        this.wellnessScore = wellnessScore;
    }
    
    public String getAssessment() {
        return assessment;
    }
    
    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Questionnaire{" +
                "id='" + id + '\'' +
                ", userDtls=" + userDtls +
                ", totalScore=" + totalScore +
                ", wellnessScore=" + wellnessScore +
                ", assessment='" + assessment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
