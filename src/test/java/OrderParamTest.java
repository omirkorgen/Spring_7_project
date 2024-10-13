import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderChecks;
import org.example.order.OrderManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class OrderParamTest {

    private OrderChecks checks = new OrderChecks();
    private OrderManager orderManager = new OrderManager();
    private List<String> color;

    public OrderParamTest(List<String> color){
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] dataGen() {
        return new Object[][] {
                {List.of("GRAY", "YELLOW")},
                {List.of("YELLOW")},
                {List.of("")},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void testCreateOrder() {
        Order order = Order.generateOrder(color);
        ValidatableResponse response = orderManager.createOrder(order);
        checks.creationSuccessfullyOrder(response);
    }
}
