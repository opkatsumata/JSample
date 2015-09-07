import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest, WithApplication}

class PersonAPISpec extends Specification {

  "PersonAPI#register" should {

    "register person" in new WithApplication {
      val Some(result) = route(FakeRequest(POST, "/api/register",
        FakeHeaders(Seq(CONTENT_TYPE -> "application/json")),
        Json.parse( """{"age":24, "name":{"first":"FirstName", "last":"LastName"}, "favoriteNumbers":[3,17]}""")))
      status(result) mustEqual OK
      contentAsString(result) mustEqual """{"age":24,"name":{"first":"FirstName","last":"LastName"},"favoriteNumbers":[3,17]}"""
    }

    "register person with bloodType" in new WithApplication {
      val Some(result) = route(FakeRequest(POST, "/api/register",
        FakeHeaders(Seq(CONTENT_TYPE -> "application/json")),
        Json.parse( """{"age":24, "name":{"first":"FirstName", "last":"LastName"}, "bloodType":"X", "favoriteNumbers":[3,17]}""")))
      status(result) mustEqual OK
      contentAsString(result) mustEqual """{"age":24,"name":{"first":"FirstName","last":"LastName"},"bloodType":"X","favoriteNumbers":[3,17]}"""
    }

    "display json parse error caused by missing favoriteNumbers" in new WithApplication {
      val Some(result) = route(FakeRequest(POST, "/api/register",
        FakeHeaders(Seq(CONTENT_TYPE -> "application/json")),
        Json.parse( """{"age":24, "name":{"first":"FirstName", "last":"LastName"}, "typo!!!":[3,17]}""")))
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual """{"obj.favoriteNumbers":[{"msg":["error.path.missing"],"args":[]}]}"""
    }

    "display json parse error caused by PersonJsonFormatter" in new WithApplication {
      val Some(result) = route(FakeRequest(POST, "/api/register",
        FakeHeaders(Seq(CONTENT_TYPE -> "application/json")),
        Json.parse( """{"typo!!!":24, "name":{"first":"FirstName", "last":"LastName"}, "favoriteNumbers":[3,17]}""")))
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual """{"obj.age":[{"msg":["error.path.missing"],"args":[]}]}"""
    }

    "display json parse error caused by NameJsonFormatter" in new WithApplication {
      val Some(result) = route(FakeRequest(POST, "/api/register",
        FakeHeaders(Seq(CONTENT_TYPE -> "application/json")),
        Json.parse( """{"age":24, "name":{"first":"FirstName", "typo!!!":"LastName"}, "favoriteNumbers":[3,17]}""")))
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual """{"obj.name.last":[{"msg":["error.path.missing"],"args":[]}]}"""
    }
  }
}
