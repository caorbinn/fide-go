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

/**
 * Controlador REST para gestionar los perfiles de usuario en la aplicación.
 * Permite insertar, actualizar, eliminar, consultar perfiles y aplicar descuentos de puntos.
 *
 * REST controller for managing user profiles in the application.
 * Allows inserting, updating, deleting, retrieving profiles and applying point discounts.
 */
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
     * Inserta un nuevo perfil en la base de datos.
     *
     * Inserts a new profile into the database.
     *
     * @param profile Objeto Profile a insertar / Profile object to insert
     * @return ResponseEntity con el perfil insertado o error 400 si falló / the inserted profile or 400 if failed
     */
    @PostMapping("/insert")
    public ResponseEntity<Optional<Profile>> insertProfile(@RequestBody Profile profile){
        ResponseEntity<Optional<Profile>> response;
        Optional<Profile> profileInserted = profileService.insert(profile);

        if(profileInserted.isPresent()){
            response = new ResponseEntity<>(profileInserted, HttpStatus.OK);
        } else {
            response  = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Actualiza los datos de un perfil existente.
     *
     * Updates an existing profile.
     *
     * @param profile Objeto Profile actualizado / Updated Profile object
     * @return ResponseEntity con true si fue actualizado, false si no existe / true if updated, false if not found
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateProfile(@RequestBody Profile profile){
        ResponseEntity<Boolean> response;
        Optional<Profile> profileFound = profileService.findById(profile.getId());

        if(profileFound.isPresent()){
            profileService.update(profile);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Elimina un perfil por su ID.
     *
     * Deletes a profile by its ID.
     *
     * @param id ID del perfil a eliminar / ID of the profile to delete
     * @return ResponseEntity con true si fue eliminado, false si no existe / true if deleted, false if not found
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteProfile(@PathVariable String id){
        ResponseEntity<Boolean> response;
        Optional<Profile> profileFound = profileService.findById(id);

        if(profileFound.isPresent()){
            profileService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Obtiene un perfil por su ID.
     *
     * Retrieves a profile by its ID.
     *
     * @param id ID del perfil a buscar / ID of the profile to retrieve
     * @return ResponseEntity con el perfil si se encuentra, o 404 si no existe / profile if found, or 404 if not
     */
    @GetMapping("/get")
    public ResponseEntity<Profile> getProfileById(@RequestParam("id") String id){
        ResponseEntity<Profile> response;
        Optional<Profile> profileFounded = profileService.findById(id);

        if(profileFounded.isPresent()){
            response = new ResponseEntity<>(profileFounded.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Descuenta puntos del perfil de un usuario específico.
     * - Si el usuario no existe, devuelve -2.
     * - Si no tiene puntos suficientes, devuelve -1.
     * - Si el descuento es exitoso, devuelve la cantidad de puntos actualizada.
     *
     * Deducts points from a specific user's profile.
     * - If user doesn't exist, returns -2.
     * - If not enough points, returns -1.
     * - If successful, returns updated points.
     *
     * @param points Cantidad de puntos a descontar / Amount of points to discount
     * @param user Objeto User al que se le descontarán puntos / User object to discount points from
     * @return ResponseEntity con puntos restantes, -1 o -2 según el resultado / remaining points, -1 or -2 depending on outcome
     */
    @PostMapping("/discountPoints/{points}")
    public ResponseEntity<Integer> insertEmail(@PathVariable int points, @RequestBody User user){
        ResponseEntity<Integer> response;
        Optional<User> userFounded = userService.findById(user.getId());

        if(userFounded.isPresent()){
            if(profileService.discountPoints(userFounded.get(), points).get() != -1){
                response = new ResponseEntity<>(user.getProfile().getPointsUser(), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
            }
        } else {
            response = new ResponseEntity<>(-2, HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
