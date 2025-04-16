package app.fide_go.repository;

import app.fide_go.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

}
