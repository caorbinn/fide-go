package app.fide_go.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Class that represents the profile of a user, it contains the data that the user
 * wants to validate under his name.
 *
 * Clase que representa el perfil de un usuario, este contiene los datos que el usuario
 * quiere validar bajo su nombre.
 *
 * @version 1.0
 * @author Caleb Espinoza Valencia
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder


@Document(collection="profiles")
public class Profile {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String idqr;
    private String urlImageProfile;
    private int pointsUser;

    // Getter para el campo id
    public String getId() {
        return id;
    }

    // Setter para el campo id
    public void setId(String id) {
        this.id = id;
    }

    // Getter para el campo idqr
    public String getIdqr() {
        return idqr;
    }

    // Setter para el campo idqr
    public void setIdqr(String idqr) {
        this.idqr = idqr;
    }

    // Getter para el campo urlImageProfile
    public String getUrlImageProfile() {
        return urlImageProfile;
    }

    // Setter para el campo urlImageProfile
    public void setUrlImageProfile(String urlImageProfile) {
        this.urlImageProfile = urlImageProfile;
    }

    // Getter para el campo pointsUser
    public int getPointsUser() {
        return pointsUser;
    }

    // Setter para el campo pointsUser
    public void setPointsUser(int pointsUser) {
        this.pointsUser = pointsUser;
    }
}
