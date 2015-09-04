package controllers

import models.{Name, Person}
import play.api.libs.json.{Format, Json, JsError}
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

class PersonAPI extends Controller {

  def register = Action(parse.json) {
    _.body.validate[Person].map { p =>
      //register
      Ok(Json.toJson(p))
    }.recoverTotal { e =>
      BadRequest(JsError.toJson(e))
    }
  }
}

object Formatter {
  implicit val PersonJsonFormatter: Format[Person] = (
    (__ \ "age").format[Int] and
      (__ \ "name").format[Name]
    )(Person.apply, unlift(Person.unapply))
}