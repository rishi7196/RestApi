package Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;


public class ApiPagination {
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://reqres.in";
		String res=given().log().all()
				.header("Content-Type","application/json")
	    .queryParam("page",1)
	    .queryParam("Size", 10)
		.when()
		.get("/api/users?page=2")
		.then().assertThat()
		.statusCode(200)
		.extract()
		.response().toString();
		
		
	}

}
