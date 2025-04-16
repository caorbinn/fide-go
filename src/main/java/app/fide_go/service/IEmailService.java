package app.fide_go.service;

import app.fide_go.model.Email;

import java.util.Optional;
import java.util.Set;

public interface IEmailService {
    Optional<Email> insert(Email email);
    boolean update(Email email);
    boolean delete(String id);
    Optional<Email> findByEmail(String email);
    Optional<Email> findById(String id);
}
