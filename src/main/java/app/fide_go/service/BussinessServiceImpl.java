package app.fide_go.service;

import app.fide_go.errors.RollBackException;
import app.fide_go.model.Bussiness;
import app.fide_go.model.Email;
import app.fide_go.repository.BussinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BussinessServiceImpl implements IBussinessService{
    @Autowired
    BussinessRepository BussinesDAO;

    /**
     * @param bussiness object to be inserted
     * @return Optional of bussiness.
     */
    @Override
    public Optional<Bussiness> insert(Bussiness bussiness) {

        //email.getId() == null &&
        if(BussinesDAO.findById(bussiness.getId()).isEmpty()){
            //I assign the id automatically.
            bussiness.setId(UUID.randomUUID().toString());
            return Optional.of(BussinesDAO.save(bussiness));
        }else{
            throw new RollBackException("The bussiness cannot be inserted into database because already exists");
        }
    }


    /**
     * @param bussiness Object type bussiness
     * @return boolean
     */
    @Override
    public boolean update(Bussiness bussiness) {
        boolean succes = false;

        if(BussinesDAO.existsById(bussiness.getId())){
            BussinesDAO.save(bussiness);
            succes = true;
        }

        return succes;
    }


    /**
     * @param id String of id`s email Object to delete.
     * @return boolean.
     */
    @Override
    public boolean delete(String id) {
        boolean succes = false;

        if(BussinesDAO.existsById(id)){
            BussinesDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }


    /**
     * @param name String of bussiness to find.
     * @return Optional of bussiness.
     */
    @Override
    public Optional<Bussiness> findByBussinessName(String name) {
        return BussinesDAO.findByBussinessName(name);
    }


    /**
     * @param id String of bussiness Object to find
     * @return Optional of Object founded
     */
    @Override
    public Optional<Bussiness> findById(String id) {
        return BussinesDAO.findById(id);
    }

}
