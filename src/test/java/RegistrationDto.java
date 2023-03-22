import com.github.javafaker.Faker;

import java.util.Locale;

public class RegistrationDto {
    String login;
    String password;
    String status;

    public RegistrationDto(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public RegistrationDto(String status, String locale) {
        this.status = status;
        Faker faker = new Faker(new Locale(locale));
        this.login = faker.name().fullName();
        this.password = faker.pokemon().name();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}
