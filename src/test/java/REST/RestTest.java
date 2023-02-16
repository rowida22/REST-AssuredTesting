package REST;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class RestTest {
    // again

    String URL = "https://reqres.in/";
    String APIPath = "api/user";

    @Test(priority = 1)
    @Description("Get NOT FOUND")
    public void TC_01_Get() {
        given().baseUri(URL).when().get("api/unknown/23")
                .then().log().all()
                .assertThat().statusCode(404);

    }

    @Test(priority = 1)
    @Description("Get All User and Check for year in first user")
    public void TC_02_Get() {
        given().baseUri(URL).when().get(APIPath)
                .then().log().all()
                .body("data[0].year", equalTo(2000))
                .assertThat().statusCode(200);
    }

    @Test(priority = 1)
    @Description("Add Some New Data in users check on Status code 201")
    public void TC_03_Post() {
        given().baseUri(URL)
                .header("ContentType", ContentType.JSON)
                .and().body("{\n" +
                        "    \"name\": \"Ahmed\",\n" +
                        "    \"job\": \"Tester\"\n" +
                        "}")
                .when().post(APIPath).then().log().all()
                .assertThat().statusCode(201);

    }

    @Test(priority = 1)
    @Description("Delete User with id = 6 with status code NOT Content")
    public void TC_04_Delete() {
        given().baseUri(URL)
                .header("ContenType", ContentType.JSON)
                .when().delete("api/users/6").then().log().all()
                .assertThat().statusCode(204);

    }

    @Test(priority = 1)
    @Description("Edit some data in id user = 5")
    public void TC_05_Patch() {
        given().baseUri(URL).header("ContentType", ContentType.JSON)
                .and().body("{\n" +
                        "    \"name\": \"mansour\",\n" +
                        "    \"job\": \"AI\"\n" +
                        "}")
                .when().patch("api/users/5").then().log().all()
                .assertThat().statusCode(200);
    }

    @Test(dependsOnMethods = "TC_05_Patch", priority = -1)
    public void TC_06_Get_Patch() {
        given().baseUri(URL).when().get("api/users/5").then().log().all();

    }

    @Test(priority = 1)
    @Description("Bad request")
    public void TC_07_Post() {
        given().baseUri(URL).header("ContentType", ContentType.JSON).and()
                .body("{\n" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}")
                .when().post("api/register").then().log().all()
                .assertThat().statusCode(400).body("error", Matchers.equalTo("Missing email or username"));
    }

}
