package main.java.com.checkoutapi.strategy;

public class PaymentResult {
    private final boolean approved;
    private final String authorizationCode;
    private final String message;

    public PaymentResult (boolean approved, String authorizationCode, String message) {
        this.approved = approved;
        this.authorizationCode = authorizationCode;
        this.message = message;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PaymentResult{approved=" + approved
                + ", authorizationCode='" + authorizationCode + "'"
                + ", message='" + message + "'}";
    }
}
