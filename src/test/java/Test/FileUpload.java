package Test;

import java.io.File;

import file.payloads;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class FileUpload {
	public static void main(String[] args) {
		
		File file= new File("path/to/your/file.png");
		RestAssured.baseURI="https://www.anc.com";
		
		String rs=given().log().all().contentType(ContentType.MULTIPART)
		.multiPart("file","file")
		.multiPart("Description","Test file upload")
		.when().body(payloads.post()).post("/abc").then().assertThat().statusCode(201)
		.extract().response().toString();
		
		
	}

}
