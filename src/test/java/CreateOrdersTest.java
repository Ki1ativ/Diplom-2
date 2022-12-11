import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrdersTest {
    CreateUsers user;
    UserApi userClient;
    OrdersAPI orderClient;
    private String token;
    private List<String> list;


    @Before
    public void setup() {
        user = CreateUsers.getUser();
        userClient = new UserApi();
        orderClient = new OrdersAPI();
                token = userClient.createRandomUser(user)
                .extract().path("accessToken");
        list = orderClient.getIngredients()
                .extract().path("data._id");
    }

    @After
    public void delete() {
        userClient.deleteUser(user, token);
    }

    @Test
    @DisplayName("Создать заказ с авторизацией")
    public void createOrderWithAuthorizationTest() {
        Orders order = new Orders(list);
        orderClient.createOrder(order, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создать заказ без авторизации")
    public void createOrderWithoutAuthorizationTest() {
        Orders order = new Orders(list);
        token = "";
        orderClient.createOrder(order, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создать заказ с ингредиентами")
    public void createOrderWithIngredientsTest() {
        Orders order = new Orders(list);
        orderClient.createOrder(order, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создать заказ без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        list = List.of();
        Orders order = new Orders(list);
        orderClient.createOrder(order, token)
                .statusCode(400)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создать заказ с неверным хешем ингредиентов")
    public void createOrderWithIncorrectHashIngredientsTest() {
        list = List.of("incorrectHashIngredients", "incorrectHashIngredients");
        Orders order = new Orders(list);
        orderClient.createOrder(order, token)
                .statusCode(500);
    }

}
