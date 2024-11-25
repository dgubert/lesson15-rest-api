package tests;

import io.qameta.allure.Owner;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Owner("Denis Gubert")
public class LoginTest extends BaseTest {

    String loginUrl = "login";

    @Test
    @DisplayName("Запрос с некорректными данными авторизации")
    void userNotFoundTest() {
        given()
                    .body("{\n" +
                        "  \"username\": \"string\",\n" +
                        "  \"email\": \"string@mail.ru\",\n" +
                        "  \"password\": \"string\"\n" +
                        "}")
                    .contentType(ContentType.JSON)
                .when()
                    .post(loginUrl)
                .then()
                    .log().body()
                    .statusCode(400)
                    .body("error", is("user not found"));
    }

    @Test
    @DisplayName("Запрос без указания body")
    void missingEmailOrUsernameTest() {
        given()
                    .contentType(ContentType.JSON)
                .when()
                    .post(loginUrl)
                .then()
                    .log().body()
                    .statusCode(400)
                    .body("error", is("Missing email or username"));
    }

    @Test
    @DisplayName("Запрос без указания Content-Type")
    void loginWithoutContentTypeTest() {
        given()
                .when()
                    .post(loginUrl)
                .then()
                    .log().all()
                    .statusCode(415);
    }
}
