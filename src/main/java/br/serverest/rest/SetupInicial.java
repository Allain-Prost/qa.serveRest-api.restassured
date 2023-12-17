package br.serverest.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class SetupInicial {
	public static void main(String[] args) {
		Response request = RestAssured.request(Method.GET, "https://serverest.dev/usuarios");
		System.out.println(request.getBody().asString());
	}
}
