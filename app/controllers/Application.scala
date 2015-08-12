package controllers

import play.api._

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import views._
import models._
import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}
import play.api.libs.json.Json


object Application extends Controller  {



  def index = Action {
      implicit request =>
        Ok(views.html.index(null))
    }
//  def jsons = Action{
//
//      implicit request =>
//      var video = Map[String,String]("1" -> "a","2" -> "b","3" -> "c");
//
//        Ok(Json.toJson(video))
//  }


}