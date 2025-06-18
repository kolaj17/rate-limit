package al.lhind.tab.claim.dto;

public class ClaimError {
    private String message;

    private ClaimError() {

    }

    private ClaimError(String message) {
        this.message = message;
    }

    public static ClaimError of(String message) {
        return new ClaimError(message);
    }

    public String getMessage() {
        return message;
    }
}
