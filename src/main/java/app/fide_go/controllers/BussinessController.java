package app.fide_go.controllers;

import app.fide_go.model.Bussiness;
import app.fide_go.service.IBussinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para manejar operaciones relacionadas con negocios (Bussiness).
 * Proporciona endpoints para insertar, actualizar, eliminar y obtener negocios.
 *
 * REST controller for handling operations related to businesses.
 * Provides endpoints to insert, update, delete, and fetch business entities.
 */

@RestController
@RequestMapping("fide_go/bussiness")
public class BussinessController {
    @Autowired
    private IBussinessService bussinessService;

    /**
     * Inserta un nuevo negocio si no existe uno con el mismo ID.
     *
     * Inserts a new business if there isn't one with the same ID.
     *
     * @param bussiness Objeto de negocio a insertar / Business object to insert
     * @return ResponseEntity con true si se insertó correctamente, false si ya existía / true if inserted, false if already exists
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
     * Actualiza los datos de un negocio existente.
     *
     * Updates an existing business.
     *
     * @param bussiness Objeto de negocio con los datos actualizados / Updated business object
     * @return ResponseEntity con true si se actualizó correctamente, false en caso contrario / true if updated, false otherwise
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
     * Elimina un negocio por su ID.
     *
     * Deletes a business by its ID.
     *
     * @param id ID del negocio a eliminar / ID of the business to delete
     * @return ResponseEntity con true si se eliminó, false si no se encontró / true if deleted, false if not found
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
     * Obtiene un negocio por su ID.
     *
     * Retrieves a business by its ID.
     *
     * @param id ID del negocio a buscar / ID of the business to find
     * @return ResponseEntity con el negocio encontrado o 404 si no existe / the business if found, 404 otherwise
     */

    @GetMapping("/get")
    public ResponseEntity<Bussiness> getBussinessById(@RequestParam("id") String id)
    {
        ResponseEntity<Bussiness> response;
        Optional<Bussiness> bussinessFounded = bussinessService.findById(id);

        response = bussinessFounded.map(bussiness -> new ResponseEntity<>(bussiness, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        return response;
    }

    /**
     * Obtiene la lista de todos los negocios.
     *
     * Retrieves the list of all businesses.
     *
     * @return Lista de objetos Bussiness / List of Bussiness objects
     */
    @GetMapping("/getall")
    public ResponseEntity<List<Bussiness>> getAllBussinesses() {
        return new ResponseEntity<>(bussinessService.getAllBussinesses(), HttpStatus.OK);
    }

}
