package app.fide_go.service;

import app.fide_go.errors.RollBackException;
import app.fide_go.model.*;
import app.fide_go.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements IProfileService {

    @Autowired
    ProfileRepository profileDAO;

    @Override
    public Optional<Profile> insert(Profile profile) {

        if(profile.getId() == null){
            profile.setId(UUID.randomUUID().toString());
        }

        return Optional.of(profileDAO.save(profile));
    }

    @Override
    public boolean update(Profile profile) {
        boolean success = false;

        if(profileDAO.existsById(profile.getId())){
            profileDAO.save(profile);
            success = true;
        }

        return success;
    }

    @Override
    public boolean delete(String id) {

        boolean succes = false;

        Optional<Profile> profileFound = profileDAO.findById(id);

        if(profileFound.isPresent()){
            profileDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }

    @Override
    public Optional<Profile> findById(String id) {
        return profileDAO.findById(id);
    }

    @Override
    public Optional<Integer> discountPoints(User user,int points) {
        Optional<Profile> userProfile=profileDAO.findById(user.getProfile().getId());
        Optional<Integer> pointsSucces;

        if(userProfile.isPresent()){
            if(userProfile.get().getPointsUser() >= points){//If the user have more points than the offers points, he can discount from his profile
                userProfile.get().setPointsUser(userProfile.get().getPointsUser()-points);
                profileDAO.save(userProfile.get());
                pointsSucces = Optional.of(userProfile.get().getPointsUser());
            }else{
                pointsSucces= Optional.of(-1);
            }
        }else{
            throw new RollBackException("The User profile doesnt exist");
        }

        return pointsSucces;
    }
}
