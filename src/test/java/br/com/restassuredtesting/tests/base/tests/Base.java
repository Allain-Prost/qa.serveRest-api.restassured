package br.com.restassuredtesting.tests.base.tests;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;

@Slf4j
public class Base {

    @BeforeClass
    public static void setup() {
        log.info("Iniciando os testes de API");
        RestAssured.baseURI = "https://serverest.dev/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }
}
