package com.enotes.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.enotes.entity.Notes;

public interface NotesRepository extends MongoRepository<Notes, String> {
	@Query("{userDtls._id: ?0}")
	List<Notes> findByUserDtlsId(String userId);
}
