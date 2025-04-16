package app.fide_go.service;

import app.fide_go.model.Profile;
import app.fide_go.model.User;

import java.util.Optional;

public interface IProfileService {
    Optional<Profile> insert(Profile profile);
    boolean update(Profile profile);
    boolean delete(String id);
    Optional<Profile> findById(String id);
    Optional<Integer> discountPoints(User user, int points);
}
