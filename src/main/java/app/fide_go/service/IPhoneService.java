package app.fide_go.service;

import app.fide_go.model.Phone;

import java.util.Optional;
import java.util.Set;


public interface IPhoneService {
    Optional<Phone> insert(Phone phone);
    boolean update(Phone phone);
    boolean delete(String id);
    Optional<Phone> findByPhoneNumber(String phoneNumber);
    Optional<Phone> findById(String id);
}
