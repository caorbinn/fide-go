package app.fide_go.controllers;

import app.fide_go.model.Email;
import app.fide_go.model.Phone;
import app.fide_go.model.Profile;
import app.fide_go.model.User;
import app.fide_go.service.IEmailService;
import app.fide_go.service.IPhoneService;
import app.fide_go.service.IProfileService;
import app.fide_go.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("fide_go/profiles")
public class ProfileUserController {
    @Autowired
    IProfileService profileService;
    @Autowired
    IEmailService emailService;
    @Autowired
    IPhoneService phoneService;
    @Autowired
    IUserService userService;


    /**
     * @param profile Profile object to be inserted.
     * @return Optional of profile.
     */
    @PostMapping("/insert")
    public ResponseEntity<Optional<Profile>> insertProfile(@RequestBody Profile profile){
        ResponseEntity<Optional<Profile>> response;
        Optional<Profile> profileInserted= profileService.insert(profile);

        if(profileInserted.isPresent()){
            response = new ResponseEntity<>(profileInserted, HttpStatus.OK);
        }else{
            response  = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * @param profile Profile object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateProfile(@RequestBody Profile profile){
        ResponseEntity<Boolean> response;
        Optional<Profile> profileFound = profileService.findById(profile.getId());

        if(profileFound.isPresent()){
            profileService.update(profile);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }


    /**
     * @param id String of Object to be deleted
     * @return ResponseEntity of boolean
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteProfile(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<Profile> profileFound=profileService.findById(id);

        if(profileFound.isPresent()){
            profileService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return response;
    }


    /**
     * @param id String representing the profile's id to be found.
     * @return ResponseEntity of Profile object
     */
    @GetMapping("/get")
    public ResponseEntity<Profile> getProfileById(@RequestParam("id") String id)
    {
        ResponseEntity<Profile> response;
        Optional<Profile> profileFounded = profileService.findById(id);

        if(profileFounded.isPresent()){
            response= new ResponseEntity<>(profileFounded.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }



    /**
     * @param points String that represent the id of profileUser
     * @param user Email Object to insert
     * @return Boolean
     */
    @PostMapping("/discountPoints/{points}")
    public ResponseEntity<Integer> insertEmail(@PathVariable int points, @RequestBody User user){
        ResponseEntity<Integer> response;
        Optional<User> userFounded = userService.findById(user.getId());
        // if return -2 is because the user doesnt exist
        // if return -1 is because the user doesnt have points
        if(userFounded.isPresent()){
            if(profileService.discountPoints(userFounded.get(),points).get() != -1){
                response = new ResponseEntity<>(user.getProfile().getPointsUser(), HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(-1,HttpStatus.BAD_REQUEST);
            }
        }else{
            response = new ResponseEntity<>(-2,HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
