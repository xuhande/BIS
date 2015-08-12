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




object About extends Controller  {

  def index = Action {
    implicit request =>
      Ok(views.html.about())
  }
}
