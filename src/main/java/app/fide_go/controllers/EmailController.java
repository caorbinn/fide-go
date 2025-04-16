package app.fide_go.controllers;

import app.fide_go.model.Email;
import app.fide_go.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("fide_go/emails")
public class EmailController {
    @Autowired
    private IEmailService emailService;

    /**
     * @param email Email object to be insert
     * @return boolean, if user have been updated correctly return true
     */
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertEmail(@RequestBody Email email){
        ResponseEntity<Boolean> response;
        Optional<Email> emailFounded = emailService.findById(email.getId());

        if(emailFounded.isEmpty()){
            emailService.insert(email).isPresent();
            response = new ResponseEntity<>(true, HttpStatus.OK);

        }else{
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }


    /**
     * @param email Email object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateEmail(@RequestBody Email email){
        ResponseEntity<Boolean> response;
        Optional<Email> emailFounded = emailService.findById(email.getId());

        if(emailFounded.isPresent()){
            if(emailService.update(email)){
                response = new ResponseEntity<>(true, HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(false, HttpStatus.BAD_GATEWAY);
            }
            
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
    public ResponseEntity<Boolean> deleteEmail(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<Email> emailFounded=emailService.findById(id);

        if(emailFounded.isPresent()){
            emailService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return response;
    }


    /**
     * @param id String representing the email's id to be found.
     * @return ResponseEntity of Email object
     */
    @GetMapping("/get")
    public ResponseEntity<Email> getEmailById(@RequestParam("id") String id)
    {
        ResponseEntity<Email> response;
        Optional<Email> emailFounded = emailService.findById(id);

        response = emailFounded.map(email -> new ResponseEntity<>(email, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        return response;
    }

}
