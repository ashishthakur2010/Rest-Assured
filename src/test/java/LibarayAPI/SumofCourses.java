import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumofCourses {

    @Test
    public void sumofCourses() {
        //JsonPath js = new JsonPath(Payload.coursesDetails());
        JsonPath js= ReusableMethods.js(Payload.coursesDetails());
        int courseCount = js.getInt("courses.size()");
        int sumOfallCourses=0;
        for(int i=0; i<courseCount; i++){
            sumOfallCourses+= js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
        }
        int parchmentAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sumOfallCourses,parchmentAmount);

    }
}
