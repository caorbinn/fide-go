package app.fide_go.controllers;

import app.fide_go.model.Offers;
import app.fide_go.service.IOffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para gestionar las ofertas disponibles en la aplicación.
 * Permite insertar, actualizar, eliminar y buscar ofertas por ID.
 *
 * REST controller for managing offers available in the application.
 * Allows inserting, updating, deleting, and finding offers by ID.
 */
@RestController
@RequestMapping("fide_go/offers")
public class OffersController {

    @Autowired
    private IOffersService offersService;

    /**
     * Inserta una nueva oferta si no existe ya una con el mismo ID.
     *
     * Inserts a new offer if there's no existing one with the same ID.
     *
     * @param offers Objeto de oferta a insertar / Offer object to insert
     * @return ResponseEntity con true si se insertó correctamente, false si ya existe / true if inserted, false if already exists
     */
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertOffers(@RequestBody Offers offers){
        ResponseEntity<Boolean> response;
        Optional<Offers> offersFounded = offersService.findById(offers.getId());

        if(offersFounded.isEmpty()){
            offersService.insert(offers);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Actualiza una oferta existente.
     *
     * Updates an existing offer.
     *
     * @param offers Objeto oferta con los datos actualizados / Updated offer object
     * @return ResponseEntity con true si se actualizó, false si falló o no existe / true if updated, false if failed or not found
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateOffers(@RequestBody Offers offers){
        ResponseEntity<Boolean> response;
        Optional<Offers> offersFounded = offersService.findById(offers.getId());

        if(offersFounded.isPresent()){
            if(offersService.update(offers)){
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
     * Elimina una oferta por su ID.
     *
     * Deletes an offer by its ID.
     *
     * @param id ID de la oferta a eliminar / ID of the offer to delete
     * @return ResponseEntity con true si se eliminó, false si no se encontró / true if deleted, false if not found
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteOffers(@PathVariable String id){
        ResponseEntity<Boolean> response;
        Optional<Offers> offersFounded = offersService.findById(id);

        if(offersFounded.isPresent()){
            offersService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Busca una oferta por su ID.
     *
     * Retrieves an offer by its ID.
     *
     * @param id ID de la oferta a buscar / ID of the offer to retrieve
     * @return ResponseEntity con la oferta si se encuentra, 404 si no existe / Offer object if found, 404 if not
     */
    @GetMapping("/get")
    public ResponseEntity<Offers> getOffersById(@RequestParam("id") String id) {
        ResponseEntity<Offers> response;
        Optional<Offers> offersFounded = offersService.findById(id);

        response = offersFounded
                .map(offer -> new ResponseEntity<>(offer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        return response;
    }
}
