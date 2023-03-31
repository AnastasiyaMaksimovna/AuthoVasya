import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {

        }
    }


    public static RegistrationDto registrationDto(String status, String locale) {
        Faker faker = new Faker(new Locale(locale));
        return new RegistrationDto(
                faker.name().fullName(),
                faker.pokemon().name(),
                status
        );
    }

}
