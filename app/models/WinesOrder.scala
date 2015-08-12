package models
import play.api.libs.json._

/**
 * Created by harry on 28/06/14.
 */
case class WinesOrder(val name: String,
                      val phone: String,
                      val email: String,
                      val postal: String,
                      val order: Map[Wine, Int],
                      val freight: Int
                       ) {
}