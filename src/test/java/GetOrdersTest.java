import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersTest {

    CreateUsers user;
    UserApi userClient;
    OrdersAPI orderClient;
    private String token;


    @Before
    public void setup() {
        user = CreateUsers.getUser();
        userClient = new UserApi();
        orderClient = new OrdersAPI();
        token = userClient.createRandomUser(user)
                .extract().path("accessToken");
    }

    @After
    public void delete() {
        userClient.deleteUser(user, token);
    }


    @Test
    @DisplayName("Получить заказ  авторизованным пользователем")
    public void getAuthorizedUserOrdersTest() {
        orderClient.getOrder(token)
                .statusCode(200);
    }

    @Test
    @DisplayName("Получить заказ неавторизованным пользователем")
    public void getNonAuthorizedUserOrdersTest() {
        orderClient.getOrder("")
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
