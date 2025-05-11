package app.fide_go.service;


import app.fide_go.model.Phone;
import app.fide_go.errors.RollBackException;
import app.fide_go.model.*;
import app.fide_go.repository.EmailRepository;
import app.fide_go.repository.PhoneRepository;
import app.fide_go.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userDAO;
    @Autowired
    private IPhoneService phoneService;
    @Autowired
    private PhoneRepository phoneDAO;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private EmailRepository emailDAO;
    @Autowired
    private IProfileService profileService;

    /**
     * @param user user object to be inserted
     * @return Optional of User
     */
    @Override
    @Transactional(rollbackFor = RollBackException.class)
    public Optional<User> insert(User user) {

        Phone phoneUser = user.getPhone();
        Email emailUser = user.getEmail();
        Profile profileUser = user.getProfile();

        if (profileUser != null && phoneUser != null && emailUser != null) {
            // Verificar si el teléfono y el correo electrónico ya existen
            if (phoneDAO.findByPhoneNumber(phoneUser.getPhoneNumber()).isPresent()
                    || emailDAO.findByEmail(emailUser.getEmail()).isPresent()) {
                throw new RollBackException("The user " + user.getUsername() + " cannot be inserted into the database because the phone number or email already exists");
            }

            try {
                // Insertar perfil
                Optional<Profile> profileInserted = profileService.insert(profileUser);
                Optional<Phone> phoneInserted = phoneService.insert(phoneUser);
                Optional<Email> emailInserted = emailService.insert(emailUser);

                if (profileInserted.isPresent() && phoneInserted.isPresent() && emailInserted.isPresent()) {

                    // Asignar entidades persistidas al usuario
                    user.setProfile(profileInserted.get());
                    user.setPhone(phoneInserted.get());
                    user.setEmail(emailInserted.get());

                    // Asignar ID al usuario si no lo tiene
                    if (user.getId() == null) {
                        user.setId(UUID.randomUUID().toString());
                    }

                    // Guardar el usuario
                    return Optional.of(userDAO.save(user));
                } else {
                    throw new RollBackException("One or more user-related objects could not be inserted");
                }

            } catch (Exception e) {
                throw new RollBackException("The user " + user.getUsername() + " cannot be inserted into the database because an error occurred: " + e.getMessage());
            }

        } else {
            throw new RollBackException("The user " + user.getUsername() + " cannot be inserted into the database because he/she must have a profile, email, and phone number");
        }
    }



    /**
     * @param user user object to be updated
     * @return boolean.
     */
    @Override
    public boolean update(User user) {
        boolean succes=false;

        if(userDAO.existsById(user.getId())){
            userDAO.save(user);
            succes=true;
        }
        return succes;
    }


    /**
     * This function deletes the user, but first it deletes the objects associated with the user.
     *
     * Esta función se encarga de eliminar el usuario, pero antes elimina los objetos asociados a el.
     *
     * @param id String representing the id of the user you want to delete.
     * @return boolean.
     */
    @Override
    public boolean delete(String id) {
        boolean succes = false;
        Optional<User> userFound= userDAO.findById(id);

        // si el usuario existe, elimino el telefono, el email.
        // if the user exists, I delete the associated phone, email.
        if(userFound.isPresent()){
            if (userFound.get().getPhone() != null) {
                phoneService.delete(userFound.get().getPhone().getId());
            }
            if (userFound.get().getEmail() != null){
                emailService.delete(userFound.get().getEmail().getId());
            }
            userDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }


    /**
     * @param id ObjectId of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<User> findById(String id) {
        return userDAO.findById(id);
    }

    /**
     * This function search by email`s user default (only 1 email)
     * @param email
     * @return
     */
    @Override
    public Optional<User> findByEmail(Email email) {
        return userDAO.findByEmail(email);
    }

    /**
     * This function search by email`s user default (only 1 email)
     * @param phone
     * @return
     */
    @Override
    public Optional<User> findByPhone(Phone phone) {
        return userDAO.findByPhone(phone);
    }


    /**
     * @param profileId Profile object to be found
     * @return Optional of Profile
     */
    @Override
    public Optional<User> findByProfileId(String profileId) {
        return userDAO.findByProfile(profileService.findById(profileId).get());
    }


    /**
     * @return List of users.
     */
    @Override
    public List<User> findAll() {return userDAO.findAll();}


    /**
     * @return long.
     */
    @Override
    public long registeredUsers() {
        return userDAO.count();
    }

}



