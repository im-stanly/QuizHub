package pl.edu.uj.tcs.quizhub.services;

import org.junit.jupiter.api.Test;
import pl.edu.uj.tcs.quizhub.services.implementations.SecurityImpl;
import pl.edu.uj.tcs.quizhub.services.interfaces.Security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SecurityTest {

    @Test
    public void testEncodeEmptyString() {
        Security security = new SecurityImpl();
        String encodedPassword = security.encode("");

        String expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

        assertEquals(expectedHash, encodedPassword);
    }

    @Test
    public void testEncode() {
        Security security = new SecurityImpl();

        String encodedPassword = security.encode("hasl0_n1e_d0_zl4mania");

        assertEquals(64, encodedPassword.length());
    }

    @Test
    public void testEncodeNull() {
        Security security = new SecurityImpl();
        assertThrows(NullPointerException.class, () -> {
            security.encode(null);
        });
    }
}