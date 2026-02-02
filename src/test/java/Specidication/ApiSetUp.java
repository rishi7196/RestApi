package Specidication;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ApiSetUp {	
	
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	
	@BeforeClass
	public void SetUp()
	{
		requestSpec= new RequestSpecBuilder()
				.setBaseUri("https://reqres.in/")
				.addHeader("Content-Type", "application/json")
				.build();
		
		responseSpec= new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType("application/json; charset=utf-8")
				.build();
				
	}
	
	@Test
	public void getUser()
	{
	    String response= given().log().all().spec(requestSpec).when()
	     .get("api/users?page=2")
	        .then()
	            .log().body()
	            .spec(responseSpec).extract().response().asString();
	    System.out.println(response);
	    JsonPath js= new JsonPath(response);
	   List<Integer>id= js.getList(response);
	   System.out.println(id);
	 	     
	}
	
	@Test
	public void getUser1()
	{
	    String response= given().log().all().spec(requestSpec).when()
	     .get("api/users?page=2")
	        .then()
	            .log().body()
	            .spec(responseSpec).extract().response().asString();
	    System.out.println(response);
	    JsonPath js= new JsonPath(response);
	    List<Map<String, Object>> users = js.getList("data");
		for(Map<String, Object>user :users)
		{
			int id=(int) user.get("id");
			String name=(String) user.get("first_name");
			System.out.println("ID: "+id+" "+name);
		}
	}
}