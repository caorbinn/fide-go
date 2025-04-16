package app.fide_go.controllers;

import app.fide_go.model.Email;
import app.fide_go.model.Phone;
import app.fide_go.model.Profile;
import app.fide_go.service.IEmailService;
import app.fide_go.service.IPhoneService;
import app.fide_go.service.IProfileService;
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
     * @param profileId String that represent the id of profileUser
     * @param email Email Object to insert
     * @return Boolean
     */
    @PostMapping("/insert_email/{profileId}")
    public ResponseEntity<Boolean> insertEmail(@PathVariable String profileId, @RequestBody Email email){
        ResponseEntity<Boolean> response;
        Optional<Email> emailInserted = emailService.insert(email);

        if(emailInserted.isPresent()){
            response = new ResponseEntity<>(true, HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * @param profileId String that represent the id of profileUser
     * @param phone Phone Object to insert
     * @return Boolean
     */
    @PostMapping("/insert_phone/{profileId}")
    public ResponseEntity<Boolean> insertPhone(@PathVariable String profileId, @RequestBody Phone phone){
        ResponseEntity<Boolean> response;
        Optional<Phone> phoneInserted = phoneService.insert(phone);

        if(phoneInserted.isPresent()){
            response = new ResponseEntity<>(true, HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
