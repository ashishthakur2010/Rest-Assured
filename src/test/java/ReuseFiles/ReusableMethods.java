package ReuseFiles;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class ReusableMethods {

    private static SessionFilter session = new SessionFilter();

     public static JsonPath js(String responce){
         JsonPath js = new JsonPath(responce);
         return js;
     }
                    //relaxedHTTPSValidation(): in 19line will BYpass Https validation.

    public static SessionFilter Jiralogin() {

        String loginResponce = given().relaxedHTTPSValidation().log().all().header("Content-Type", "application/json")
                .body("{ \"username\": \"sokimotoko5star\", \"password\": \"Aooale@12\" }")
                .filter(session)
                .when().post("/rest/auth/1/session")
                .then().log().all().statusCode(200).extract().response().body().asString();

        return session;
    }
    public static String CreateIssue() {
        String issueId =given().log().all().header("Content-Type", "application/json")//.header("Cookie",sessionName+"="+sessionId)
                .body(Payload.CreateIssue())
                .filter(session).when().post("/rest/api/2/issue")
                .then().log().all().assertThat().statusCode(201).extract().path("id");
        //System.out.println(issueId); ;
        return issueId;
    }
    public static void DeleteIssue(String issueId) {
        String id =issueId;
        given().log().all().pathParams("id",id).header("Content-Type", "application/json")
                .filter(session).when().delete("rest/api/2/issue/{id}") //here {id} is mapped from "id"
                .then().log().all().assertThat().statusCode(204);
    }
}
