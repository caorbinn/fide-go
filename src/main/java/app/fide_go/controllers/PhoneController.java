package app.fide_go.controllers;

import app.fide_go.model.Phone;
import app.fide_go.service.IPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("fide_go/phones")
public class PhoneController {
    @Autowired
    private IPhoneService phoneService;


    /**
     * @param phone Phone Object to insert
     * @return Boolean
     */
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertPhone(@RequestBody Phone phone){
        ResponseEntity<Boolean> response;
        Optional<Phone> phoneInserted = phoneService.insert(phone);

        if(phoneInserted.isPresent()){
            response = new ResponseEntity<>(true, HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * @param phone Phone object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updatePhone(@RequestBody Phone phone){
        ResponseEntity<Boolean> response;
        Optional<Phone> phoneFounded = phoneService.findById(phone.getId());

        if(phoneFounded.isPresent()){
            if(phoneService.update(phone)){
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
    public ResponseEntity<Boolean> deletePhone(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<Phone> phoneFound=phoneService.findById(id);

        if(phoneFound.isPresent()){
            phoneService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return response;
    }


    /**
     * @param id String representing the phone's id to be found.
     * @return ResponseEntity of Phone object
     */
    @GetMapping("/get")
    public ResponseEntity<Phone> getPhoneById(@RequestParam("id") String id)
    {
        ResponseEntity<Phone> response;
        Optional<Phone> phoneFounded = phoneService.findById(id);

        if(phoneFounded.isPresent()){
            response= new ResponseEntity<>(phoneFounded.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


}
