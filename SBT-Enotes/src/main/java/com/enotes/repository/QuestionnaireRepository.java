package com.enotes.repository;

import com.enotes.entity.Questionnaire;
import com.enotes.entity.UserDtls;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireRepository extends MongoRepository<Questionnaire, String> {
    List<Questionnaire> findByUserDtlsId(String userId);
    
    Optional<Questionnaire> findFirstByUserDtlsIdOrderByCreatedAtDesc(String userId);
}
