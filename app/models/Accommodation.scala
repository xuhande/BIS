package models

import play.api.Logger
import controllers.Paypal
import scala.collection.JavaConverters._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.libs.json.Writes._
import play.api.libs.functional.syntax._

case class Accommodation(val name: String,
                         val arrivalDate: DateTime,
                         val numberNights: Int,
                         val peopleCottage: Int,
                         val arrivalTime: String,
                         val milk: String,
                         val bread: String,
                         val telephone: String,
                         val email: String,
                         val request: String,
                         val amount: Double
                          ) {
  def amountString: String = "%.2f".format(amount)
}

object Accommodation {
  val pattern = "dd-MM-yyyy"

  implicit val dateFormat =
    Format[DateTime](Reads.jodaDateReads(pattern), Writes.jodaDateWrites(pattern))

  implicit val accFormat: Format[Accommodation] = (
    (__ \ "name").format[String] and
      (__ \ "arrivalDate").format[DateTime] and
      (__ \ "numberNights").format[Int] and
      (__ \ "peopleCottage").format[Int] and
      (__ \ "arrivalTime").format[String] and
      (__ \ "milk").format[String] and
      (__ \ "bread").format[String] and
      (__ \ "telephone").format[String] and
      (__ \ "email").format[String] and
      (__ \ "request").format[String] and
      (__ \ "amount").format[Double]
    )(Accommodation.apply, unlift(Accommodation.unapply))
}