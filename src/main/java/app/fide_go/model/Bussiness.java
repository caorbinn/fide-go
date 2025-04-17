package app.fide_go.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Class representing a business, containing offers, has a unique identifier.
 *
 * Clase que representa un negocio, que contiene ofertas, tiene un identificador unico.
 *
 * @version 1.0
 * @author Caleb Espinoza Valencia
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Document(collection="bussiness")
public class Bussiness {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String bussinessName;
    private String bussinessDescription;
    private String bussinessAddress;
    @DBRef
    private List<Offers> offers;


    // Getter para el campo id
    public String getId() {
        return id;
    }

    // Setter para el campo id
    public void setId(String id) {
        this.id = id;
    }

    // Getter para el campo bussinessName
    public String getBussinessName() {
        return bussinessName;
    }

    // Setter para el campo bussinessName
    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    // Getter para el campo bussinessDescription
    public String getBussinessDescription() {
        return bussinessDescription;
    }

    // Setter para el campo bussinessDescription
    public void setBussinessDescription(String bussinessDescription) {
        this.bussinessDescription = bussinessDescription;
    }

    // Getter para el campo bussinessAddress
    public String getBussinessAddress() {
        return bussinessAddress;
    }

    // Setter para el campo bussinessAddress
    public void setBussinessAddress(String bussinessAddress) {
        this.bussinessAddress = bussinessAddress;
    }

    // Getter para el campo offers
    public List<Offers> getOffers() {
        return offers;
    }

    // Setter para el campo offers
    public void setOffers(List<Offers> offers) {
        this.offers = offers;
    }
}
