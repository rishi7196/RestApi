package Test;
import static io.restassured.RestAssured.*;

import java.io.File;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostCallJPG {
	
	@Test
	public void jpg()
	{
		File file = new File("path/to/your/image.jpg");
		RestAssured.baseURI="https://reqres.in";
		String response=given().log().all().contentType(ContentType.MULTIPART)
		.multiPart("file",file)
		.when().post("/abc")
		.then().assertThat().statusCode(201).extract().response().toString();
	}
	
	public void form()
	{
		File file = new File("path/to/your/image.jpg");
		RestAssured.baseURI="https://reqres.in";
		String response=given().log().all().contentType(ContentType.MULTIPART)
		.formParam("username", "user1")
		.when().post("/abc")
		.then().assertThat().statusCode(201).extract().response().toString();
	}
}
	