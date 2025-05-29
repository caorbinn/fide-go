package app.fide_go.controllers;

import app.fide_go.model.Email;
import app.fide_go.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para gestionar operaciones relacionadas con direcciones de correo electrónico.
 * Permite insertar, actualizar, eliminar y buscar objetos de tipo Email.
 *
 * REST Controller for managing operations related to email addresses.
 * Allows inserting, updating, deleting, and retrieving Email objects.
 */
@RestController
@RequestMapping("fide_go/emails")
public class EmailController {

    @Autowired
    private IEmailService emailService;

    /**
     * Inserta un nuevo email si es válido.
     *
     * Inserts a new email if it is valid.
     *
     * @param email Objeto Email a insertar / Email object to insert
     * @return ResponseEntity con true si se insertó correctamente, false si ocurrió un error / true if inserted, false on error
     */
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertEmail(@RequestBody Email email){
        ResponseEntity<Boolean> response;
        Optional<Email> emailInserted = emailService.insert(email);

        if(emailInserted.isPresent()){
            response = new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Actualiza un email existente.
     *
     * Updates an existing email.
     *
     * @param email Objeto Email actualizado / Updated Email object
     * @return ResponseEntity con true si se actualizó correctamente, false si no / true if updated, false otherwise
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateEmail(@RequestBody Email email){
        ResponseEntity<Boolean> response;
        Optional<Email> emailFounded = emailService.findById(email.getId());

        if(emailFounded.isPresent()){
            if(emailService.update(email)){
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
     * Elimina un email por su ID.
     *
     * Deletes an email by its ID.
     *
     * @param id ID del email a eliminar / ID of the email to delete
     * @return ResponseEntity con true si se eliminó, false si no se encontró / true if deleted, false if not found
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteEmail(@PathVariable String id){
        ResponseEntity<Boolean> response;
        Optional<Email> emailFounded = emailService.findById(id);

        if(emailFounded.isPresent()){
            emailService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Busca un email por su ID.
     *
     * Finds an email by its ID.
     *
     * @param id ID del email a buscar / ID of the email to find
     * @return ResponseEntity con el objeto Email si se encuentra, o 404 si no / Email object if found, or 404 if not
     */
    @GetMapping("/get")
    public ResponseEntity<Email> getEmailById(@RequestParam("id") String id) {
        ResponseEntity<Email> response;
        Optional<Email> emailFounded = emailService.findById(id);

        response = emailFounded
                .map(email -> new ResponseEntity<>(email, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        return response;
    }
}
