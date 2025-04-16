package app.fide_go.repository;

import app.fide_go.model.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PhoneRepository extends MongoRepository<Phone, String> {
    Optional<Phone> findByPhoneNumber(String phoneNumber);
}
