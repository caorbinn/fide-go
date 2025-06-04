package app.fide_go.controllers;

import app.fide_go.model.*;
import app.fide_go.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("fide_go/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProfileService profileService;
    @Autowired
    public IPhoneService phoneService;
    @Autowired
    public IEmailService emailService;

    @PostMapping("/insert")
    public ResponseEntity<User> insertUser(@RequestBody User user){
        ResponseEntity<User> response;
        Optional<User> userInserted = userService.insert(user);

        if(userInserted.isPresent()){
            response = new ResponseEntity<>(userInserted.get(), HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody User user){
        ResponseEntity<Boolean> response;
        Optional<User> userFound = userService.findById(user.getId());

        if(userFound.isPresent()){
            userService.update(user);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String id){
        ResponseEntity<Boolean> response;
        Optional<User> userFound = userService.findById(id);

        if(userFound.isPresent()){
            profileService.delete(userFound.get().getProfile().getId()); // También se elimina el perfil asociado
            userService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> all = userService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/get_by_phone/{phonenumber}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phonenumber){
        ResponseEntity<User> response;
        Optional<Phone> phoneFound = phoneService.findByPhoneNumber(phonenumber);
        Optional<User> user;

        if(phoneFound.isPresent()){
            user = userService.findByPhone(phoneFound.get());
            response = new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/get_by_email/{email}")
    public ResponseEntity<User> getUserByEmailName(@PathVariable String email){
        ResponseEntity<User> response;
        Optional<Email> emailFound = emailService.findByEmail(email);
        Optional<User> user;

        if(emailFound.isPresent()){
            user = userService.findByEmail(emailFound.get());
            response = new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping("/redeemOffer/{userId}")
    public ResponseEntity<String> redeemOffer(@PathVariable String userId, @RequestBody Offers offer){
        ResponseEntity<String> response;
        Optional<User> userFound = userService.findById(userId);

        if(userFound.isPresent()){
            Optional<String> code = userService.redeemOffer(userFound.get(), offer);
            if(code.isPresent()){
                response = new ResponseEntity<>(code.get(), HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }
}
