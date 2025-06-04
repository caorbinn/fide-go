package app.fide_go.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private String bussinessId;
    private String urlImageOffer;
    // Code shown when the offer is redeemed
    private String redeemCode;

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

    // Getter para el campo bussinessId
    public String getBussinessId() {
        return bussinessId;
    }

    // Setter para el campo bussinessId
    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    // Getter para el campo urlImageOffer
    public String getUrlImageOffer() {
        return urlImageOffer;
    }

    // Setter para el campo urlImageOffer
    public void setUrlImageOffer(String urlImageOffer) {
        this.urlImageOffer = urlImageOffer;
    }

    // Getter para el campo redeemCode
    public String getRedeemCode() {
        return redeemCode;
    }

    // Setter para el campo redeemCode
    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }
}
