package app.fide_go.service;

import app.fide_go.model.Bussiness;

import java.util.Optional;

public interface IBussinessService {
    Optional<Bussiness> insert(Bussiness bussiness);
    boolean update(Bussiness bussiness);
    boolean delete(String id);
    Optional<Bussiness> findByBussinessName(String name);
    Optional<Bussiness> findById(String id);
}
