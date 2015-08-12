package models

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.calendar.CalendarScopes
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.calendar.model._

import java.security.spec.PKCS8EncodedKeySpec
import java.security.KeyFactory

import play.api.Play
import com.google.api.client.util.Base64
import play.api.libs.functional.syntax._
import play.api.libs.json._
import collection.JavaConversions._
import collection.JavaConverters._
import com.google.api.client.util.DateTime
import java.util.Date
import java.util.TimeZone

import scala.collection.mutable

//import calendar.Event
import play.api.Logger
import com.google.api.services.calendar.model.Event.ExtendedProperties
import java.util.HashMap


case class Event(
                  id: String,
                  title: String,
                  location: Option[String] = None,
                  description: Option[String] = None,
                  start: org.joda.time.DateTime,
                  end: Option[org.joda.time.DateTime] = None,
                  url: String,
                  notified: Boolean = false)

object GoogleCalendar {
//  private val accountId = "32256304225-okkepik6a9a4ssn60rile8cbdid0ja6g@developer.gserviceaccount.com";
//  private val privateKey = "-----BEGIN PRIVATE KEY-----\n" +
//    "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALMxSirr68mtmDHO\n" +
//    "orbS73P12IrnEY6GsW0YdiHzDeJXtuhXHKPFXw9dOVvQyF5N57/KSr5H4laEOAd1\n" +
//    "L6j+w1UTCaYE2zqGCcYtCShLVYY5gl6R6eG1VCQ2FxOzfMaF2AZAKaodsGGzyNZD\n" +
//    "rPi8Q7zelcq8/5cdXc3JW7rkW/9DAgMBAAECgYEArcERpeT10a2eck1nwddvkQXG\n" +
//    "X4oHoChDSwH5Kpo/0EY1eb1ea/8qWf+PatRcNfVczsAdEb0qX7H9kl2zRS8jv27v\n" +
//    "LOcbrpDK16Rg4Glq9cYR5glWcJdfbAsnobWDvckLeMlt0Y6UpIlyZ+x3bhoIrEMk\n" +
//    "56myVb3Sf59MVHSCHgECQQDe7nXcAkDoE/MOxI8Z9RGh5+yml1ALmi3K5FHYM5pc\n" +
//    "Z7UaYkv2U3x3XmVyAor0EVHOgtoPLuzhKfleaROjP1BBAkEAzcXl/qCFjCH5iWPz\n" +
//    "42N0s2rDiemk7YRi83EZG7P2JS38NUUPPivQ/RfBWB82GlmdJS2pYYpYux0ivV7D\n" +
//    "B/pugwJAJvAxx97g5WhuoNq2QAttLAFtdV+ijJQ+gVzF6uSAhrlaiaQ3rmvAprzn\n" +
//    "LTXCfYT07AQxAEvq9h01wEbMNkxdwQJBAMGMv8U9n+ArpNgCWEkuJ3sLlVmHl0aC\n" +
//    "G8D4RLG2GHToSC6jAq9r72vgiFAnK3ClkhaCWKwSwVtfEGonVDOQ75kCQHuQ6TKJ\n" +
//    "HQn9xCnnxwkxQ6frGGouQS/KUw+rz35HvmBak+/BHODp9CIHVi2Wf8Zm67DS8Yxq\n" +
//    "n53oSGRuGHgt6xQ=\n" +
//    "-----END PRIVATE KEY-----";
//  private val applicationName = "test";
//  private val calendarId = "hyperflix.tv_5p5n0227bv72o1ofqtv7hukcp0@group.calendar.google.com";

  private val accountId = "1049035851423-ebnemcastifucupi3ch05om6ohfiinvk@developer.gserviceaccount.com";
  private val privateKey = "-----BEGIN PRIVATE KEY-----\n" +
    "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJhMu3TXi8niZ2E\n" +
    "HyKxf8gke/qGEwOOEr4y7UWmZ0OepClaE0G3tk7eF4GPr18O9V4Lhve3pE+lq6pV\n" +
    "lMU+2+yVpCiUgAnK1WIwmR/HRjF3o5MhFspHlWC5gjd9aYmB0YIFUw5qe/8E7bSN\n" +
    "QhrCzSQ9LkaXaOjfouhIiv0noUhlAgMBAAECgYBAsS+fQQDvaQ9nqPsBwg3tsA2m\n" +
    "lelfaK8ze5TBpc/dnjM7LAe/1biwcatkuY+Ikgw6x0Nzo45btGHFrLP/tQRc9b8O\n" +
    "Ffa2jy5M9onl4t17jLwLObg7nifiNH26hzPlFcGYlVtUQv9o4OIvr9Br43lYAikN\n" +
    "PKeFgv0xCCaKToeLIQJBAOML35uQMGOBvr8IDOz+XpCsfSez1MdOI2p7xYWGE1yi\n" +
    "hy2h1xi7MPEq94h1sYHnttndMO+1ynOpm9QpMiH7MqMCQQDJII8OxX4tHLS1/rc0\n" +
    "5CWDBEULh8fZrmBq24uBr3UB3prHR0zhAhPIVQrkOfmKQOX7pbTUMkbkk9ZveaJ4\n" +
    "JNFXAkEAkgK2wHIPzE5J/swMk/RZmCx1KC0r7yg++mGyetXoabBTwnLLwynOpal+\n" +
    "GpGln+cF3kGzENUV5x/yCJSdmuDShQJAa6OTLHKhnn8Yb7ZMUnAvazjjwtH+MQ/T\n" +
    "5hxtQxCl7EQ4TaJpEJrN/48T7yTf2g/1hYIfjtkBe99rCens+6OorQJBAJKj3vyh\n" +
    "LQIHC6CsIc//i3Vr+LVNsU/L9VjHLk9Isla4xdqPpUEtWqcR8uzcD8jFPjEnSAD3\n" +
    "z3Xy8hxFAKCnGnM=\n" +
    "-----END PRIVATE KEY-----\n";
  private val applicationName = "My Project";
  private val calendarId = "vynfields.com_67o19gfkvk25hqck7hvfa9mqqs@group.calendar.google.com";


  lazy val calendarService: Option[com.google.api.services.calendar.Calendar] = {
    val HTTP_TRANSPORT = new NetHttpTransport()
    val JSON_FACTORY = new JacksonFactory()
    val pk = privateKey.replace("-----BEGIN PRIVATE KEY-----\n", "").replace("-----END PRIVATE KEY-----", "");

    val encoded = Base64.decodeBase64(pk);


    val keyFactory = KeyFactory.getInstance("RSA");
    val ks = new PKCS8EncodedKeySpec(encoded);

    val key = keyFactory.generatePrivate(ks);

    val test = CalendarScopes.CALENDAR_READONLY;
    val credential = new GoogleCredential.Builder()
      .setTransport(HTTP_TRANSPORT)
      .setJsonFactory(JSON_FACTORY)
      .setServiceAccountId(accountId)
      .setServiceAccountScopes(List[String](CalendarScopes.CALENDAR).asJavaCollection)
      .setServiceAccountPrivateKey(key)
      .build();

    Option(new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(
      applicationName).build())
  }

  def get() = {
    val now = new DateTime(new Date(), TimeZone.getTimeZone("Asia/Chongqing"))//Asia/Chongqing   Pacific/Auckland
    //calendarService.get.events().list(calendarId).setSingleEvents(true).setTimeMin(now).setOrderBy("startTime").execute().getItems().toList
    val cal = calendarService.get.events().list(calendarId).setSingleEvents(true).setTimeMin(now).setOrderBy("starttime").execute().getItems()
    println(cal)
    val callist = scala.collection.JavaConversions.asScalaBuffer(cal).toList

    val li = mutable.MutableList[String]();

    for (i <- callist) {

      val stDt = new org.joda.time.DateTime(i.getStart.getDate.toString)

      val endDt = new org.joda.time.DateTime(i.getEnd.getDate.toString)

      val p = new org.joda.time.Period(stDt, endDt);

      for (j <- 0 until p.getDays) {
        println(stDt.plusDays(j).toString("yyyy-MM-dd"))
        li += stDt.plusDays(j).toString("yyyy-MM-dd")
      }
    }
    li.toList
  }

  def set(summ: String, desc: String, stDt: String, endDt: String) = {
    val event = new com.google.api.services.calendar.model.Event();

    event.setSummary(summ);
    event.setDescription(desc);

    val attendees = List[EventAttendee](new EventAttendee().setEmail("info@vynfields.com").setResponseStatus("accepted"));
    event.setAttendees(attendees);
    event.setExtendedProperties(new ExtendedProperties())

    event.setStart(new EventDateTime().setDate(new DateTime(stDt)).setTimeZone("Pacific/Auckland"));
    event.setEnd(new EventDateTime().setDate(new DateTime(endDt)).setTimeZone("Pacific/Auckland"));

    val createdEvent = calendarService.get.events().insert(calendarId, event).setSendNotifications(true).execute();
    //val createdEvent = calendarService.get.events().insert(calendarId, event).execute();
  }


  private def toEvent(googleEvent: com.google.api.services.calendar.model.Event) = {
    Event(googleEvent.getId(), googleEvent.getSummary(), Option(googleEvent.getLocation()), Option(googleEvent.getDescription()), googleEvent.getStart(), Option(googleEvent.getEnd()), googleEvent.getHtmlLink(), false)
  }

  /**
   * Implicit conversion (no explanatary call needed)
   */
  implicit def toDateTime(googleDateTime: com.google.api.services.calendar.model.EventDateTime): org.joda.time.DateTime = {
    if (googleDateTime != null && googleDateTime.getDateTime() != null) {
      new org.joda.time.DateTime(googleDateTime.getDateTime().getValue())
    } else {
      new org.joda.time.DateTime(googleDateTime.getDate().getValue()).withTimeAtStartOfDay()
    }
  }
}