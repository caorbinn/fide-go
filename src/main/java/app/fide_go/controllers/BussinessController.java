package app.fide_go.controllers;

import app.fide_go.model.Bussiness;
import app.fide_go.service.IBussinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("fide_go/bussiness")
public class BussinessController {
    @Autowired
    private IBussinessService bussinessService;

    /**
     * @param bussiness bussiness object to be insert
     * @return boolean, if user have been updated correctly return true
     */
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertBussiness(@RequestBody Bussiness bussiness){
        ResponseEntity<Boolean> response;
        Optional<Bussiness> bussinessFounded = bussinessService.findById(bussiness.getId());

        if(bussinessFounded.isEmpty()){
            bussinessService.insert(bussiness);
            response = new ResponseEntity<>(true, HttpStatus.OK);

        }else{
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }


    /**
     * @param bussiness bussiness object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateEmail(@RequestBody Bussiness bussiness){
        ResponseEntity<Boolean> response;
        Optional<Bussiness> bussinessFounded = bussinessService.findById(bussiness.getId());

        if(bussinessFounded.isPresent()){
            if(bussinessService.update(bussiness)){
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
    public ResponseEntity<Boolean> deleteBussiness(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<Bussiness> bussinessFounded= bussinessService.findById(id);

        if(bussinessFounded.isPresent()){
            bussinessService.delete(id);
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
    public ResponseEntity<Bussiness> getBussinessById(@RequestParam("id") String id)
    {
        ResponseEntity<Bussiness> response;
        Optional<Bussiness> bussinessFounded = bussinessService.findById(id);

        response = bussinessFounded.map(bussiness -> new ResponseEntity<>(bussiness, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        return response;
    }

}
