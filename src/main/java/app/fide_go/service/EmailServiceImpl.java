package app.fide_go.service;

import app.fide_go.errors.RollBackException;
import app.fide_go.model.Email;
import app.fide_go.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailServiceImpl implements IEmailService{
    @Autowired
    EmailRepository emailDAO;

    /**
     * @param email email object to be inserted
     * @return Optional of email.
     */
    @Override
    public Optional<Email> insert(Email email) {

        if(emailDAO.findByEmail(email.getEmail()).isEmpty()){
            //I assign the id automatically.
            email.setId(UUID.randomUUID().toString());
            return Optional.of(emailDAO.save(email));
        }else{
            throw new RollBackException("The email cannot be inserted into database because already exists");
        }
    }


    /**
     * @param email Object type email
     * @return boolean
     */
    @Override
    public boolean update(Email email) {
        boolean succes = false;

        if(emailDAO.existsById(email.getId()) && emailDAO.findByEmail(email.getEmail()).isEmpty()){
            emailDAO.save(email);
            succes = true;
        }

        return succes;
    }


    /**
     * @param id String of id`s email Object to delete.
     * @return boolean.
     */
    @Override
    public boolean delete(String id) {
        boolean succes = false;

        if(emailDAO.existsById(id)){
            emailDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }


    /**
     * @param email String of email to find.
     * @return Optional of email.
     */
    @Override
    public Optional<Email> findByEmail(String email) {
        return emailDAO.findByEmail(email);
    }


    /**
     * @param id String of Email Object to find
     * @return Optional of Object founded
     */
    @Override
    public Optional<Email> findById(String id) {
        return emailDAO.findById(id);
    }

}
