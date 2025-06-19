package ifrs.edu.br.utils;

/**
 * Validation
 */
public class Validation {

    public static void emailValidation(String email) throws RuntimeException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex))
            throw new RuntimeException("Invalid email format.");
    }
}
