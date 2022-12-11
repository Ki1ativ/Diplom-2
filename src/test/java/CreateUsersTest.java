import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;


public class CreateUsersTest {

    CreateUsers user;
    UserApi userClient;
    private String token;

    @Before
    public void setup() {
        user = CreateUsers.getUser();
        userClient = new UserApi();
    }

    @After
    public void delete() {
        try {
            UserCredential credentials = UserCredential.from(user);
            token = userClient.loginUser(credentials)
                    .extract().path("accessToken");
            userClient.deleteUser(user, token);
        } catch (IllegalArgumentException exception) {
            System.out.println("User not created");
        }
    }
    @Test
    @DisplayName("Создать нового пользователя")
    public void createNewUser() {
        userClient.createRandomUser(user)
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Нельзя создать пользователя, который уже зарегистрирован")
    public void createIdenticalUserTest() {
        userClient.createRandomUser(user);
        userClient.createRandomUser(user)
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создать пользователя без имени")
    public void createUserWithoutNameTest() {
        user.setName("");
        userClient.createRandomUser(user)
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
