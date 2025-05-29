package app.fide_go.controllers;

import app.fide_go.model.Phone;
import app.fide_go.service.IPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para manejar números de teléfono asociados a perfiles de usuario u otros datos.
 * Permite insertar, actualizar, eliminar y consultar números telefónicos.
 *
 * REST controller for managing phone numbers associated with user profiles or other data.
 * Provides endpoints to insert, update, delete, and retrieve phone numbers.
 */
@RestController
@RequestMapping("fide_go/phones")
public class PhoneController {

    @Autowired
    private IPhoneService phoneService;

    /**
     * Inserta un nuevo número de teléfono si es válido.
     *
     * Inserts a new phone number if valid.
     *
     * @param phone Objeto Phone a insertar / Phone object to insert
     * @return ResponseEntity con true si fue creado, false si hay error / true if created, false on error
     */
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertPhone(@RequestBody Phone phone){
        ResponseEntity<Boolean> response;
        Optional<Phone> phoneInserted = phoneService.insert(phone);

        if(phoneInserted.isPresent()){
            response = new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Actualiza un número de teléfono existente.
     *
     * Updates an existing phone number.
     *
     * @param phone Objeto Phone actualizado / Updated Phone object
     * @return ResponseEntity con true si fue actualizado, false si falló o no existe / true if updated, false otherwise
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updatePhone(@RequestBody Phone phone){
        ResponseEntity<Boolean> response;
        Optional<Phone> phoneFounded = phoneService.findById(phone.getId());

        if(phoneFounded.isPresent()){
            if(phoneService.update(phone)){
                response = new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(false, HttpStatus.BAD_GATEWAY);
            }
        } else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Elimina un número de teléfono por su ID.
     *
     * Deletes a phone number by its ID.
     *
     * @param id ID del teléfono a eliminar / ID of the phone number to delete
     * @return ResponseEntity con true si fue eliminado, false si no se encontró / true if deleted, false if not found
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePhone(@PathVariable String id){
        ResponseEntity<Boolean> response;
        Optional<Phone> phoneFound = phoneService.findById(id);

        if(phoneFound.isPresent()){
            phoneService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Busca un número de teléfono por su ID.
     *
     * Retrieves a phone number by its ID.
     *
     * @param id ID del teléfono a buscar / ID of the phone to find
     * @return ResponseEntity con el objeto Phone si se encuentra, o 404 si no / Phone object if found, or 404 if not
     */
    @GetMapping("/get")
    public ResponseEntity<Phone> getPhoneById(@RequestParam("id") String id) {
        ResponseEntity<Phone> response;
        Optional<Phone> phoneFounded = phoneService.findById(id);

        if(phoneFounded.isPresent()){
            response = new ResponseEntity<>(phoneFounded.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }
}
