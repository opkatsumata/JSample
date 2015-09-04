package controllers

import models.Person
import play.api.libs.json.{Json, JsError}
import play.api.mvc._

class PersonAPI extends Controller {

  def register = Action(parse.json) {
    _.body.validate[Person].map { p =>
      //register
      Ok(Json.toJson(p))
    }.recoverTotal { e =>
      BadRequest(JsError.toFlatJson(e))
    }
  }
}
