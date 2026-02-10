package Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class Test {
	

	public static void main(String[] args) {
		
		RestAssured.baseURI="https://reqres.in";
		
		String res=given().log().all().header("ContentType","application/json")
				.header("x-api-key", "reqres_5e5feddb161f432298669b3dc4a852aa")
		.when().get("api/users?page=2")
		.then().assertThat().statusCode(200).extract().asString();
		System.out.println(res);
		
		JsonPath js= new JsonPath(res);
		int pagesize=js.getInt("page");
		System.out.println(pagesize);
		//
		String ids=js.getString("data.find{it.id==7}");
		System.out.println(ids);
		
		
		
	}
	
	

}
