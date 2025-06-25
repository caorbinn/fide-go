package app.fide_go.dto;

public class RedeemOfferResponse {
    private String redeemCode;
    private int points;

    public RedeemOfferResponse() {}

    public RedeemOfferResponse(String redeemCode, int points) {
        this.redeemCode = redeemCode;
        this.points = points;
    }

    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}