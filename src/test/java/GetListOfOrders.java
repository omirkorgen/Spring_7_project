import io.restassured.response.ValidatableResponse;
import org.example.order.OrderChecks;
import org.example.order.OrderManager;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class GetListOfOrders {

    private OrderChecks checks = new OrderChecks();
    private OrderManager orderManager = new OrderManager();

    @Test
    @DisplayName("Получения списка заказов")
    public void testGetListOfOrders() {
        ValidatableResponse response = orderManager.getListOfOrder();
        checks.getListOfOrdersSuccessfully(response);
    }
}
