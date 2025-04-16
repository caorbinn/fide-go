package app.fide_go.repository;

import app.fide_go.model.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EmailRepository extends MongoRepository<Email, String> {
    Optional<Email> findByEmail(String email);
}
