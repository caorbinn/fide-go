package app.fide_go.service;

import app.fide_go.model.Phone;
import app.fide_go.errors.RollBackException;
import app.fide_go.model.*;
import app.fide_go.repository.EmailRepository;
import app.fide_go.repository.PhoneRepository;
import app.fide_go.repository.UserRepository;
import app.fide_go.repository.OffersRepository;
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
    @Autowired
    private OffersRepository offersDAO;

    @Override
    @Transactional(rollbackFor = RollBackException.class)
    public Optional<User> insert(User user) {

        Phone phoneUser = user.getPhone();
        Email emailUser = user.getEmail();
        Profile profileUser = user.getProfile();

        if (profileUser != null && phoneUser != null && emailUser != null) {
            if (phoneDAO.findByPhoneNumber(phoneUser.getPhoneNumber()).isPresent()
                    || emailDAO.findByEmail(emailUser.getEmail()).isPresent()) {
                throw new RollBackException("The user " + user.getUsername() + " cannot be inserted into the database because the phone number or email already exists");
            }

            try {
                Optional<Profile> profileInserted = profileService.insert(profileUser);
                Optional<Phone> phoneInserted = phoneService.insert(phoneUser);
                Optional<Email> emailInserted = emailService.insert(emailUser);

                if (profileInserted.isPresent() && phoneInserted.isPresent() && emailInserted.isPresent()) {

                    user.setProfile(profileInserted.get());
                    user.setPhone(phoneInserted.get());
                    user.setEmail(emailInserted.get());
                    user.setIsAdmin(false);

                    if (user.getId() == null) {
                        user.setId(UUID.randomUUID().toString());
                    }

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

    @Override
    public boolean update(User user) {
        boolean succes=false;

        if(userDAO.existsById(user.getId())){
            userDAO.save(user);
            succes=true;
        }
        return succes;
    }

    @Override
    public boolean delete(String id) {
        boolean succes = false;
        Optional<User> userFound= userDAO.findById(id);

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

    @Override
    public Optional<User> findById(String id) {
        return userDAO.findById(id);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public Optional<User> findByPhone(Phone phone) {
        return userDAO.findByPhone(phone);
    }

    @Override
    public Optional<User> findByProfileId(String profileId) {
        return userDAO.findByProfile(profileService.findById(profileId).get());
    }

    @Override
    public List<User> findAll() {return userDAO.findAll();}

    @Override
    public long registeredUsers() {
        return userDAO.count();
    }

    @Override
    @Transactional(rollbackFor = RollBackException.class)
    public Optional<String> redeemOffer(User user, Offers offer) {
        Optional<User> userFounded = userDAO.findById(user.getId());
        if (userFounded.isPresent()) {
            Optional<Offers> offerFounded = offersDAO.findById(offer.getId());
            if (offerFounded.isPresent()) {
                Optional<Integer> result = profileService.discountPoints(userFounded.get(), offerFounded.get().getPoints());
                if (result.isPresent() && result.get() >= 0) {
                    return Optional.ofNullable(offerFounded.get().getRedeemCode());
                } else {
                    return Optional.empty();
                }
            } else {
                throw new RollBackException("The offer doesn't exist");
            }
        } else {
            throw new RollBackException("The user doesn't exist");
        }
    }
}
