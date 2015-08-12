package controllers

/**
 * Created by handexu on 14-6-5.
 */

import play.api._

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import views._
import models._
import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}
import org.joda.time._
import org.joda.time.format._
import play.api.libs.json._
import scalax.file._
import play.api.i18n.Messages


object Service extends Controller {


    def index = Action{
          implicit request =>
            Ok(views.html.service())
    }

//  val accForm = Form(
//    mapping(
//      "name" -> nonEmptyText,
//      "arrivalDate" -> nonEmptyText,
//      "numberNights" -> nonEmptyText,
//      "peopleCottage" -> nonEmptyText,
//      "arrivalTime" -> nonEmptyText,
//      "milk" -> nonEmptyText,
//      "bread" -> nonEmptyText,
//      "telephone" -> nonEmptyText,
//      "email" -> nonEmptyText,
//      "request" -> text,
//      "amount" -> nonEmptyText
//    )
//      ((name, arrivalDate, numberNights, peopleCottage, arrivalTime, milk, bread, telephone, email, request, amount) =>
//        Accommodation(name, DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(arrivalDate), numberNights.toInt,
//          peopleCottage.toInt, arrivalTime, milk, bread, telephone, email, request, amount.toDouble))
//      ((a: Accommodation) =>
//        Some(a.name, a.arrivalDate.toString, a.numberNights.toString(), a.peopleCottage.toString(), a.arrivalTime,
//          a.milk, a.bread, a.telephone, a.email, a.request, a.amount.toString()))
//  )
//
//  def index = Action {
//    implicit request =>
//      Ok(views.html.accommodations(accForm))
//
//  }
//
//  def json = Action {
//    implicit request =>
//      val li = GoogleCalendar.get();
//    println(li+"====================s")
//      Ok(Json.toJson(li))
//  }
//
//  def save = Action {
//    implicit request =>
//      accForm.bindFromRequest.fold(
//        formWithErrors => {
//          println(formWithErrors)
//          BadRequest(views.html.accommodations(formWithErrors))
//          Redirect(routes.Accommodations.index)
//        },
//        value => {
//          Paypal.PaypalAcc(value)
//        }
//      )
//  }
//
//  def paypal = Action {
//    implicit request =>
//      val token = request.getQueryString("token").get
//      val payerID = request.getQueryString("PayerID").get
//
//      val source = scala.io.Source.fromFile("storage/" + token + ".txt")
//      val lines = source.getLines().mkString
//      source.close()
//
//      val acc = Json.parse(lines).as[Accommodation];
//
//      if (Paypal.pay(token, payerID)) {
//        GoogleCalendar.set(acc.name + " " + token, lines,
//          acc.arrivalDate.toString("yyyy-MM-dd"), acc.arrivalDate.minusDays(acc.numberNights * -1).toString("yyyy-MM-dd"))
//        val p = Path("storage") / Path(token + ".txt")
//        p.moveTo(Path("storage") / Path("archive") / Path(token + ".txt"), false, true)
//        Redirect(routes.Accommodations.index).flashing("Success" -> Messages("We have received your information, we will confirm your booking via email, thank you."))
//      }
//      else {
//        Redirect(routes.Accommodations.index).flashing("Error" -> Messages("There appeas to be an error with your booking, please contact us directly, thank you."))
//        //        Redirect("/accommodation").flashing(
//        //          ("error", "Payment error")
//        //        )
//      }

      //      val s = new models.mail.Mail (
      //        from = ("test@test.com", "test test"),
      //        to = Seq("info@vynfields.com"),
      //        subject = "test email",
      //        message = "test email"
      //      )


    //###book the room in google calendar###

    //###archive the payment file, send email to info@vynfields.com###

    //###display booking successful information to client###


//  }

}