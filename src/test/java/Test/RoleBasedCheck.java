package Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

public class RoleBasedCheck {
		
	String adminToken = "ADMIN_TOKEN";
    String managerToken = "MANAGER_TOKEN";
    String userToken = "USER_TOKEN";
    
    @Test
    public void adminShouldAccessUsers() {
    	RestAssured.basePath="";

        given()
            .header("Authorization", "Bearer " + adminToken)
        .when()
            .get("/api/users")
        .then()
            .statusCode(200);
    }
    
    @Test
    public void userShouldNotAccessAdminAPI() {

        given()
            .header("Authorization", "Bearer " + userToken)
        .when()
            .get("/api/admin/users")
        .then()
            .statusCode(403);
    }
    
    
	

}
