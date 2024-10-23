package pl.edu.uj.tcs.quizhub.exception;

public class UserAccountException extends RuntimeException {
    public UserAccountException() {
        super("Account with given login not found.");
    }

    public UserAccountException(String message) {
        super(message);
    }
}
