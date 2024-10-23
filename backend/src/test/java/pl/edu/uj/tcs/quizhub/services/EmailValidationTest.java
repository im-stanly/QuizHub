package pl.edu.uj.tcs.quizhub.services;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import pl.edu.uj.tcs.quizhub.services.implementations.EmailValidationImpl;
import pl.edu.uj.tcs.quizhub.services.interfaces.EmailValidation;

public class EmailValidationTest {
    EmailValidation emailValidation;

    EmailValidationTest() {
        emailValidation = new EmailValidationImpl();
    }

    @Test
    void correctEmail() {
        String email = "username@domain.com";
        Assert.isTrue(emailValidation.isEmailValid(email));
    }

    @Test
    void correctEmail2() {
        String email = "user.name@domain.com";
        Assert.isTrue(emailValidation.isEmailValid(email));
    }

    @Test
    void correctEmail3() {
        String email = "user-name@domain.com";
        Assert.isTrue(emailValidation.isEmailValid(email));
    }

    @Test
    void correctEmail4() {
        String email = "username@domain.co.in";
        Assert.isTrue(emailValidation.isEmailValid(email));
    }

    @Test
    void correctEmail5() {
        String email = "user_name@domain.com";
        Assert.isTrue(emailValidation.isEmailValid(email));
    }

    @Test
    void dotAfterUsernameFail1() {
        String email = "username.@domain.com";
        Assert.isTrue(!emailValidation.isEmailValid(email));
    }

    @Test
    void dotInBeginningFail2() {
        String email = ".user.name@domain.com";
        Assert.isTrue(!emailValidation.isEmailValid(email));
    }

    @Test
    void dotInEndFail3() {
        String email = "user-name@domain.com.";
        Assert.isTrue(!emailValidation.isEmailValid(email));
    }

    @Test
    void noDomainFail4() {
        String email = "username@.com";
        Assert.isTrue(!emailValidation.isEmailValid(email));
    }
}
