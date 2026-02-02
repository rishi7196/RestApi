package serlizationConcept;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;




public class PostCall {
	
	@Test
	public void post()
	{
		RestAssured.baseURI="https://reqres.in";
		UserRequest request= new UserRequest();
		request.setName("john");
		request.setJob("QA Engineer");
		String res=given().log().all().contentType(ContentType.JSON)
			    .header("x-api-key", "reqres-free-v1")  // <<< required API key

		.body(request).when().post("/api/users")
		.then().assertThat().statusCode(201).extract().response().asString();
		System.out.println(res);
		
	}

}
