package tests.funcional.base;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;

@Slf4j
public class Base {

    @BeforeAll
    public static void setup() {
        log.info("Iniciando os testes de API");
        RestAssured.baseURI = "https://serverest.dev/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }
}
