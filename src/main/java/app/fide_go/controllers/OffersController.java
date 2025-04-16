package app.fide_go.controllers;

import app.fide_go.model.Email;
import app.fide_go.model.Offers;
import app.fide_go.service.IEmailService;
import app.fide_go.service.IOffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("fide_go/offers")
public class OffersController {
    @Autowired
    private IOffersService offersService;

    /**
     * @param offers offers object to be insert
     * @return boolean, if user have been updated correctly return true
     */
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertOffers(@RequestBody Offers offers){
        ResponseEntity<Boolean> response;
        Optional<Offers> offersFounded = offersService.findById(offers.getId());

        if(offersFounded.isEmpty()){
            offersService.insert(offers);
            response = new ResponseEntity<>(true, HttpStatus.OK);

        }else{
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }


    /**
     * @param offers offers object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateOffers(@RequestBody Offers offers){
        ResponseEntity<Boolean> response;
        Optional<Offers> offersFounded = offersService.findById(offers.getId());

        if(offersFounded.isPresent()){
            if(offersService.update(offers)){
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
    public ResponseEntity<Boolean> deleteOffers(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<Offers> offersFounded=offersService.findById(id);

        if(offersFounded.isPresent()){
            offersService.delete(id);
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
    public ResponseEntity<Offers> getOffersById(@RequestParam("id") String id)
    {
        ResponseEntity<Offers> response;
        Optional<Offers> offersFounded = offersService.findById(id);

        response = offersFounded.map(email -> new ResponseEntity<>(email, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        return response;
    }

}
