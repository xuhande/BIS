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
import org.apache.commons.mail.EmailAttachment
import com.typesafe.plugin._
import play.api.Play.current
import play.api.Play
import play.Logger
object Contact extends Controller  {
val MessageForm = Form(
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
    receiptMessage(name,email,message)
        Redirect(routes.Contact.index).flashing("Success" -> Messages("您已成功发送邮件给我们了，请您留意您的邮箱."))
  }



  def receiptMessage(name:String,email:String,Message:String) =  {

val mail = use[MailerPlugin].email
    mail.setSubject("Vycos 联系消息")
    mail.addRecipient("Vycos <hande.xu@pixitek.com>") //发送到目的地址<请修改接收地址>
    mail.addFrom("Vycos <xuboy1@126.com>") //系统发送的邮箱,必须与conf邮箱一致
    mail.send("","<html><head><title>"+name+"</title></head><body><strong>客户名称：</strong>"+name+"<br /><br /><strong>客户邮箱：</strong>"+email+"<br /><br /><strong>留言内容：</strong>v"+Message+"</body>")  //  以html方式发送邮件
  }

}