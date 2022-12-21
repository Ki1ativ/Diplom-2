import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    CreateUsers user;
    UserApi userClient;
    private String token;

    @Before
    public void setup() {
        user = CreateUsers.getUser();
        userClient = new UserApi();
        token = userClient.createRandomUser(user)
                .extract().path("accessToken");
    }

    @After
    public void delete() {
        userClient.deleteUser(user, token);
    }
    @Test
    @DisplayName("Залогиниться под существующим пользователем")
    public void validLoginUserTest() {
        UserCredential credentials = UserCredential.from(user);
        userClient.loginUser(credentials)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Нельзя залогиниться с неверным логином и паролем")
    public void invalidLoginUserTest() {
        UserCredential credentials = UserCredential.from(user);
        credentials.setEmail("ErrorEmail");
        credentials.setPassword("ErrorPassword");
        userClient.loginUser(credentials)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

}
