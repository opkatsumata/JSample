package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Person(age: Int, name: Name)

object Person {
  implicit val PersonJsonFormatter: Format[Person] = (
    (__ \ "age").format[Int] and
      (__ \ "name").format[Name]
    )(Person.apply, unlift(Person.unapply))
}
