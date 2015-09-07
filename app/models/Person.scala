package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Person(age: Int, name: Name, bloodType: Option[String], favoriteNumber: Seq[Int])

object Person {
  implicit val personJsonFormatter: Format[Person] = (
    (__ \ "age").format[Int] and
      (__ \ "name").format[Name] and
      (__ \ "bloodType").formatNullable[String] and
      (__ \ "favoriteNumbers").format[Seq[Int]]
    )(Person.apply, unlift(Person.unapply))
}
