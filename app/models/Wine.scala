package models
import play.api.libs.json._

trait Wine {
  def name: String;

  def desc: String = "";

  def year: Int;

  def price: Double;

  def priceString: String = "%.2f".format(price)

  def formCode: String = name.replaceAllLiterally(" ", "");

  def label: String = if (desc != "") {year + " " + name + " - " + desc} else {year + " " + name};
}

case class PinotRose2013(val name: String = "Pinot Rose",
                         val year: Int = 2013,
                         val price: Double = 29.00) extends Wine {}

case class DryRose2013(val name: String = "Peche de Noire",
                       override val desc: String = "Dry Rose",
                       val year: Int = 2013,
                       val price: Double = 29.00) extends Wine {}

case class ClasssicRiesling2013(val name: String = "Classic Riesling",
                                val year: Int = 2013,
                                val price: Double = 29.00) extends Wine {}

case class DryRiesling2013(val name: String = "Dry Riesling",
                           val year: Int = 2013,
                           val price: Double = 29.00) extends Wine {}

case class Bliss2013(val name: String = "Bliss",
                     override val desc: String = "Sparkling Riesling",
                     val year: Int = 2013,
                     val price: Double = 31.00) extends Wine {}

case class MadRooster2012(val name: String = "Mad Rooster",
                          val year: Int = 2012,
                          val price: Double = 34.00) extends Wine {}

case class PinotNoir2011(val name: String = "Pinot Noir",
                         val year: Int = 2011,
                         val price: Double = 39.00) extends Wine {}

case class ReservePinotNoir2011(val name: String = "Reserve Pinot Noir",
                                val year: Int = 2011,
                                val price: Double = 69.00) extends Wine {}

case class abc(val name:String = "test trait",
               val year:Int =2014,
               val price:Double = 123
               ) extends Wine {}

