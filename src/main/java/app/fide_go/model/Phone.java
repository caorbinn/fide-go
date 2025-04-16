package app.fide_go.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class that represents a telephone, it has a string phoneNumber that must be unique and a boolean
 * that informs if it has already been validated or not.
 *
 * Clase que representa un telefono, esta tiene un string numero de telefono que debe de ser unico
 * y un boolean que informa si ya ha sido validado o no.
 *
 * @version 1.0
 * @author Caleb Espinoza Valencia
 */

@Data
@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "phones")
public class Phone {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Indexed(unique = true)
    private String phoneNumber;
    private boolean isVerified;
    // Getter para el campo id
    public String getId() {
        return id;
    }

    // Setter para el campo id
    public void setId(String id) {
        this.id = id;
    }

    // Getter para el campo phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter para el campo phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter para el campo isVerified
    public boolean isVerified() {
        return isVerified;
    }

    // Setter para el campo isVerified
    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }

}
