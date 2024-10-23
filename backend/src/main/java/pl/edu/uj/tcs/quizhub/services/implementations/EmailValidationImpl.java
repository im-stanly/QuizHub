package pl.edu.uj.tcs.quizhub.services.implementations;

import org.springframework.stereotype.Service;
import pl.edu.uj.tcs.quizhub.services.interfaces.EmailValidation;

import java.util.regex.Pattern;

@Service
public class EmailValidationImpl implements EmailValidation {
    public boolean isEmailValid(String emailToCheck) {
        return Pattern.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", emailToCheck);
    }
}
