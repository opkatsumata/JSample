import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest, WithApplication}

class PersonAPISpec extends Specification {

  "PersonAPI#register" should {

    "register person" in new WithApplication {
      //val Some(result) = //route(FakeRequest(POST, "/api/v2/person"))
      val Some(result) = route(FakeRequest(POST, "/api/v2/person",
        FakeHeaders(Seq(CONTENT_TYPE -> Seq("application/json"))),
        Json.parse( """{"age":24, "name":{"first":"FirstName", "last":"LastName"}}""")))
      status(result) mustEqual OK
      contentAsString(result) mustEqual """{"age":24,"name":{"first":"FirstName","last":"LastName"}}"""
    }
  }


}
