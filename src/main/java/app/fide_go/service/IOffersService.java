package app.fide_go.service;

import app.fide_go.model.Email;
import app.fide_go.model.Offers;

import java.util.Optional;

public interface IOffersService {
    Optional<Offers> insert(Offers offers);
    boolean update(Offers offers);
    boolean delete(String id);
    Optional<Offers> findByTitle(String title);
    Optional<Offers> findById(String id);
}
