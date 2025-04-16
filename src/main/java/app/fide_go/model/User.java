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
 * Clase que representa un usuario con los datos basicos para su registro,
 * se declara que el correo electronico debe de ser unico.
 *
 * @version 1.0
 * @author Caleb Espinoza Valencia
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Document(collection="users")
public class User {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String username;
    @DBRef
    @Indexed(unique = true)
    private Phone phone;
    @DBRef
    @Indexed(unique = true)
    private Email email;
    @DBRef
    @Indexed(unique = true)
    private Profile profile;

    // Getter para el campo id
    public String getId() {
        return id;
    }

    // Setter para el campo id
    public void setId(String id) {
        this.id = id;
    }

    // Getter para el campo username
    public String getUsername() {
        return username;
    }

    // Setter para el campo username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter para el campo phone
    public Phone getPhone() {
        return phone;
    }

    // Setter para el campo phone
    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    // Getter para el campo email
    public Email getEmail() {
        return email;
    }

    // Setter para el campo email
    public void setEmail(Email email) {
        this.email = email;
    }

    // Getter para el campo profile
    public Profile getProfile() {
        return profile;
    }

    // Setter para el campo profile
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
