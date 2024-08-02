package core.controller.carrinho;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ExcluirCarrinhoRequest {

    public static Response montarDadosExcluirCarrinho(String token) {

        return given()
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .delete("/carrinhos/cancelar-compra");
    }
}
