package app.fide_go.service;

import app.fide_go.model.Offers;

import java.util.Optional;
import java.util.List;


public interface IOffersService {
    Optional<Offers> insert(Offers offers);
    boolean update(Offers offers);
    boolean delete(String id);
    Optional<Offers> findByTitle(String title);
    Optional<Offers> findById(String id);
    List<Offers> findByBussinessId(String bussinessId);
}
