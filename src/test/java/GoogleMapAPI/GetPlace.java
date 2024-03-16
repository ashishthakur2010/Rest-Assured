package GoogleMapAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetPlace {
    //Requrement: Validate if Add Place API is working as expected
    public static void main(String[] args) throws IOException {
        //given - all input details
        //when - Submit the API
        //Then - Validate the responce
        //Read Static Data file from Json. --line no 23.
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String AddPlaceResponse = given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
                .body(ReuseFiles.Payload.AddPlace())
                .body(new String(Files.readAllBytes(Paths.get("C:\\EVORASIERA\\repos\\Rest-Assured\\src\\test\\JsonData\\Addplace.json"))))
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", equalTo("Apache/2.4.52 (Ubuntu)")).extract().response().asString();

        System.out.println("AddPlaceResponse -->" + AddPlaceResponse);
        JsonPath js = new JsonPath(AddPlaceResponse);
        String place_id = js.getString("place_id");
        System.out.println("place_id -->" + place_id);

        //Update Place
        String newAddress = "70 winter walk, USA";
        given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+place_id+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").
                when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Place
        String GetPlaceResponce =given().log().all().queryParam("key", "qaclick123").
                queryParam("place_id", place_id).
                when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        System.out.println("place_address -->" + GetPlaceResponce);

        JsonPath js1 = ReuseFiles.ReusableMethods.js(GetPlaceResponce);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress,newAddress);

    }
}