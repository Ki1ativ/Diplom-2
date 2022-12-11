import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserApi {

    private final String REGISTER = "/auth/register";
    private final String USER = "/auth/user";
    private final String LOGIN = "/auth/login";


    @Step("Создать случайного пользователя")
    public ValidatableResponse createRandomUser(CreateUsers user) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Удалить пользователя")
    public ValidatableResponse deleteUser(CreateUsers user, String token) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .baseUri(Config.BASE_URL)
                .body(user)
                .when()
                .delete(USER)
                .then().log().all();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(UserCredential credentials) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Изменить данные пользователя")
    public ValidatableResponse changeDataUser(CreateUsers user, String token) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .baseUri(Config.BASE_URL)
                .body(user)
                .when()
                .patch(USER)
                .then().log().all();
    }

}
