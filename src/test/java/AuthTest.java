import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class AuthTest {


    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static RegistrationDto userActive = new RegistrationDto("active", "en");
    private static RegistrationDto userBlocked = new RegistrationDto("blocked", "en");

    @BeforeAll
    static void setUpAll() {

        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(userActive) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(userBlocked) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void authoTest1() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=login] input").setValue(this.userActive.getLogin());
        $("span[data-test-id=password] input").setValue(this.userActive.getPassword());
        $("[data-test-id=action-login]").click();
        $x("//*[contains(text(), 'Личный кабинет')]").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    public void authoTest2() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=login] input").setValue(this.userBlocked.getLogin());
        $("span[data-test-id=password] input").setValue(this.userBlocked.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Пользователь заблокирован"));
    }

    @Test
    public void authoTest3() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=login] input").setValue(this.userActive.getLogin() + "1");
        $("span[data-test-id=password] input").setValue(this.userActive.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка!")).shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    public void authoTest4() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=login] input").setValue(this.userActive.getLogin());
        $("span[data-test-id=password] input").setValue(this.userActive.getPassword() + "P");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка!")).shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    public void authoTest5() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=login] input").setValue(this.userActive.getLogin());
        $("[data-test-id=action-login]").click();
        $x("//*[contains(@class,'input__sub')]").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
}

