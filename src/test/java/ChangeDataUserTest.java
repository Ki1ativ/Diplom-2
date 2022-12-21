import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeDataUserTest {
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
    @DisplayName("Изменить почту пользователя с авторизацией")
    public void successChangeEmailTest() {
        user.setEmail("changedEmail");
        userClient.changeDataUser(user, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменить пароль пользователя с авторизацией")
    public void successChangePasswordTest() {
        user.setPassword("changedPassword");
        userClient.changeDataUser(user, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменить имя пользователя с авторизацией")
    public void successChangeNameTest() {
        user.setName("changedName");
        userClient.changeDataUser(user, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Нельзя изменить почту пользователя без авторизации")
    public void notSuccessChangeEmailTest() {
        user.setEmail("changedEmail");
        token = "";
        userClient.changeDataUser(user, token)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Изменить пароль пользователя без авторизации")
    public void notSuccessChangePasswordTest() {
        user.setPassword("changedPassword");
        token = "";
        userClient.changeDataUser(user, token)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Изменить имя пользователя без авторизации")
    public void notSuccessChangeNameTest() {
        user.setName("changedName");
        token = "gogrperonbfdmbs";
        userClient.changeDataUser(user, token)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

}
