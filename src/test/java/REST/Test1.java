package REST;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class Test1 {
    @Test
    public void test(){
       given().baseUri("https://63e4f8598e1ed4ccf6ea5892.mockapi.io/api/V1")
               .when().get("users")
               .then().log().all()
               .assertThat().statusCode(200)
               .assertThat().body("[1].name",is(equalTo("Myra Miller")),
               "name",hasItems("Lionel Bergnaum","Rita Koelpin"))
               .assertThat().body("name",not(hasItem("Ruwida")))
//               .assertThat().body("name",containsInAnyOrder("Lonnie Boyle")); //It should contain the all name and arranged

    }


}
