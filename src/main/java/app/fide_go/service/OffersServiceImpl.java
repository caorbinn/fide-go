package app.fide_go.service;

import app.fide_go.errors.RollBackException;
import app.fide_go.model.Bussiness;
import app.fide_go.model.Offers;
import app.fide_go.repository.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OffersServiceImpl implements IOffersService{
    @Autowired
    OffersRepository OffersDAO;

    /**
     * @param offers object to be inserted
     * @return Optional of offers.
     */
    @Override
    public Optional<Offers> insert(Offers offers) {

        //email.getId() == null &&
        if(OffersDAO.findById(offers.getId()).isEmpty()){
            //I assign the id automatically.
            offers.setId(UUID.randomUUID().toString());
            return Optional.of(OffersDAO.save(offers));
        }else{
            throw new RollBackException("The offers cannot be inserted into database because already exists");
        }
    }


    /**
     * @param offers Object type offers
     * @return boolean
     */
    @Override
    public boolean update(Offers offers) {
        boolean succes = false;

        if(OffersDAO.existsById(offers.getId())){
            OffersDAO.save(offers);
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

        if(OffersDAO.existsById(id)){
            OffersDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }


    /**
     * @param title String of title to find.
     * @return Optional of offers.
     */
    @Override
    public Optional<Offers> findByTitle(String title) {
        return OffersDAO.findByTitle(title);
    }


    /**
     * @param id String of offers Object to find
     * @return Optional of Object founded
     */
    @Override
    public Optional<Offers> findById(String id) {
        return OffersDAO.findById(id);
    }

}
