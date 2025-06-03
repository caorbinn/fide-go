package app.fide_go.controllers;

import app.fide_go.model.*;
import app.fide_go.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar usuarios en la aplicación.
 * Permite operaciones CRUD y búsquedas por teléfono o email.
 *
 * REST controller for managing users in the application.
 * Supports CRUD operations and searches by phone or email.
 */
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

    /**
     * Inserta un nuevo usuario en el sistema.
     *
     * Inserts a new user into the system.
     *
     * @param user Objeto User a insertar / User object to insert
     * @return ResponseEntity con el usuario creado o error 400 / created user or 400 error
     */

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

    /**
     * Actualiza la información de un usuario.
     *
     * Updates user information.
     *
     * @param user Objeto User actualizado / Updated User object
     * @return ResponseEntity con true si se actualizó correctamente / true if successfully updated
     */
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

    /**
     * Elimina un usuario por su ID.
     *
     * Deletes a user by their ID.
     *
     * @param id ID del usuario a eliminar / ID of the user to delete
     * @return ResponseEntity con true si se eliminó correctamente / true if successfully deleted
     */
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

    /**
     * Obtiene todos los usuarios del sistema.
     *
     * Retrieves all users in the system.
     *
     * @return Lista de objetos User / List of User objects
     */
    @GetMapping("/get_all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> all = userService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    // ==== BÚSQUEDA DE USUARIO POR DATOS DE PERFIL / USER SEARCH BY PROFILE DATA ====

    /**
     * Busca un usuario por su número de teléfono.
     *
     * Searches a user by their phone number.
     *
     * @param phonenumber Número de teléfono del usuario / User's phone number
     * @return ResponseEntity con el usuario si se encuentra, 404 si no / user if found, 404 otherwise
     */
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

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * Searches a user by their email address.
     *
     * @param email Dirección de email del usuario / User's email address
     * @return ResponseEntity con el usuario si se encuentra, 404 si no / user if found, 404 otherwise
     */
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
}
