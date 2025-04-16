package app.fide_go.service;

import app.fide_go.errors.RollBackException;
import app.fide_go.model.Phone;
import app.fide_go.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PhoneServiceImpl implements IPhoneService {
    @Autowired
    PhoneRepository phoneDAO;


    /**
     * @param phone Phone object to be inserted.
     * @return an optional with the phone, otherwise the optional is empty.
     */
    @Override
    public Optional<Phone> insert( Phone phone) {


        if(phone.getId() == null && phoneDAO.findByPhoneNumber(phone.getPhoneNumber()).isEmpty()){
            //I assign the id automatically.
            phone.setId(UUID.randomUUID().toString());

            return Optional.of(phoneDAO.save(phone));
        }else {
            throw new RollBackException("The phone cannot be inserted into database because the phone already exists into database");
        }
    }


    /**
     * @param phone Phone Object to be updated
     * @return boolean
     */
    @Override
    public boolean update(Phone phone) {
        boolean succes = false;

        if(phoneDAO.existsById(phone.getId()) && phoneDAO.findByPhoneNumber(phone.getPhoneNumber()).isEmpty()){
            phoneDAO.save(phone);
            succes = true;
        }

        return succes;
    }


    /**
     * @param id String of id`s phone Object to delete.
     * @return boolean.
     */
    @Override
    public boolean delete(String id) {
        boolean succes = false;

        if(phoneDAO.existsById(id)){
            phoneDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }


    /**
     * @param phoneNumber String of number phone to find
     * @return an optional with the phone, otherwise the optional is empty.
     */
    @Override
    public Optional<Phone> findByPhoneNumber(String phoneNumber) {
        return phoneDAO.findByPhoneNumber(phoneNumber);
    }


    /**
     * @param id String of Phone Object to find
     * @return Optional of Object founded
     */
    @Override
    public Optional<Phone> findById(String id) {
        return phoneDAO.findById(id);
    }

}
