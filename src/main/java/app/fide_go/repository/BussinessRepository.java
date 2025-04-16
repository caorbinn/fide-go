package app.fide_go.repository;

import app.fide_go.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BussinessRepository extends MongoRepository<Bussiness, String> {
    Optional<Bussiness> findByBussinessName(String name);
}
