package app.fide_go.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Class that represents a user with the basic data for registration,
 * it is declared that the email must be unique.
 *
 * Clase que representa un oferta, que contiene un identificador unico, y ademas contiene el valor en puntos
 *
 * @version 1.0
 * @author Caleb Espinoza Valencia
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Document(collection="offers")

public class Offers {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String title;
    private String description;
    private String termsAndConditions;
    private int points;

    // Getter para el campo id
    public String getId() {
        return id;
    }

    // Setter para el campo id
    public void setId(String id) {
        this.id = id;
    }

    // Getter para el campo title
    public String getTitle() {
        return title;
    }

    // Setter para el campo title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter para el campo description
    public String getDescription() {
        return description;
    }

    // Setter para el campo description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter para el campo termsAndConditions
    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    // Setter para el campo termsAndConditions
    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    // Getter para el campo points
    public int getPoints() {
        return points;
    }

    // Setter para el campo points
    public void setPoints(int points) {
        this.points = points;
    }
}
