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
import play.api.data.Form
import play.api.data.Forms.tuple
import play.api.data.Forms.text
import play.api.i18n.Messages

object Contact extends Controller  {
//  case class Messages(name: String,email:String)
val MessageForm = Form(
  //    mapping(
//  "name" -> text,
//  "email" -> text
  //    )(Messages.apply, Messages.unapply)
    tuple(
  "name" -> text,
  "email" -> text,
  "message" -> text
  )
  )




  def index = Action { implicit request =>
          Ok(views.html.contact(request.flash))
  }

  def sendMail = Action { implicit request =>
  val (name, email,message) = MessageForm.bindFromRequest.get
          val s = new models.mail.Mail (
            from = ("xuboy1@126.com", "225836"),
            to = Seq("hande.xu@yzp2p.com"),
            subject = "test email",
            message = "test email"
          )
  println(s)
        Redirect(routes.Contact.index).flashing("Success" -> Messages("您已成功发送邮件给我们了，请您留意您的邮箱."))
  }

}