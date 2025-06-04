package app.fide_go.service;

import app.fide_go.model.*;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> insert(User user);
    boolean update(User user);
    boolean delete(String id);
    Optional<User> findById(String id);
    Optional<User> findByEmail(Email email);
    Optional<User> findByPhone(Phone phone);
    Optional<User> findByProfileId(String profileId);
    List<User> findAll();

    long registeredUsers();

    Optional<String> redeemOffer(User user, Offers offer);
}
