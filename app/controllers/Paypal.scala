package controllers

import org.joda.time.DateTime
import urn.ebay.apis.eBLBaseComponents._
import urn.ebay.apis.CoreComponentTypes.BasicAmountType
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import views._
import models._
import scala.collection.JavaConverters._
import urn.ebay.api.PayPalAPI._
import java.io._
import play.api.libs.json._
import models.Accommodation
import scalax.file._
import scala.collection.mutable.MutableList

/**
 * Created by harry on 7/06/14.
 */
object Paypal extends Controller {
  private val sdkConfig = Map[String, String]("mode" -> "live",
    "acct1.UserName" -> "info_api1.vynfields.com",
    "acct1.Password" -> "PH5P9TSZGE39DAS6",
    "acct1.Signature" -> "AFcWxV21C7fd0v3bYYYRCpSSRl31AdoX8DeZYrslqcrgZP3YcuODHqFu"
  );
  private val paypalURL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="
  private val baseURL = "http://www.vynfields.com"
  /*testing*/
//  private val sdkConfig = Map[String, String]("mode" -> "sandbox",
//    "acct1.UserName" -> "info-facilitator_api1.vynfields.com",
//    "acct1.Password" -> "1404123752",
//    "acct1.Signature" -> "Aud1ZIiychIFooBYxqQIYjPtlfbJAG3Yd38gMa3kYVC-2iTYUsCas--o"
//  )
//  private val paypalURL = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="
//  private val baseURL = "http://localhost:9000"

  private val service = new PayPalAPIInterfaceServiceService(sdkConfig.asJava)
  private val paypalVersion = "104.0"
  private val paypalSale = PaymentActionCodeType.fromValue("Sale");
  private val paypalCurrency = CurrencyCodeType.fromValue("NZD");
  private val paypalLandingPage = LandingPageType.BILLING;

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try {
      op(p)
    } finally {
      p.close()
    }
  }

  def PaypalWine(wo: WinesOrder) = {
    val winesOrder = wo.order
    val pd = new PaymentDetailsType();
    pd.setPaymentAction(paypalSale);

    val lineItems = MutableList[PaymentDetailsItemType]();
    var total = 0.0;
    var freight = wo.freight.toInt
    for (w <- winesOrder) {
      if (w._2 > 0) {
        val item = new PaymentDetailsItemType();
        val amt = new BasicAmountType();
        amt.setCurrencyID(paypalCurrency);
        amt.setValue(w._1.priceString);
        item.setAmount(amt);
        item.setQuantity(w._2);
        item.setName(w._1.label);
        lineItems += item;
        total = total + w._1.price * w._2;
      }
    }
    val item2 = new PaymentDetailsItemType();
    val amt2 = new BasicAmountType();
    amt2.setCurrencyID(paypalCurrency);
    amt2.setValue(wo.freight.toString);
    item2.setAmount(amt2);
    item2.setQuantity(1);
    item2.setName("Shipping Costs");
    lineItems += item2;
    total = total + freight;

    pd.setPaymentDetailsItem(lineItems.asJava);

    val ot = new BasicAmountType();
    ot.setCurrencyID(paypalCurrency);
    ot.setValue("%.2f".format(total));
    pd.setOrderTotal(ot);

    redirectToPaypal(List[PaymentDetailsType](pd), baseURL + "/wines", wo = wo)
  }

  def PaypalAcc(a: Accommodation) = {
    val pd = new PaymentDetailsType();
    pd.setPaymentAction(paypalSale);

    val item = new PaymentDetailsItemType();
    val amt = new BasicAmountType();
    amt.setCurrencyID(paypalCurrency);
//    amt.setValue(a.amountString);
    amt.setValue("100.00");

    item.setAmount(amt);
    item.setQuantity(1);
    //item.setName("$100 charges (rest to be paid on arrival):");
    item.setName("Vynfields cottage: " + a.arrivalDate.toString("dd MMM yyyy") + " for " + a.numberNights + " night(s)")
    //item.setDescription("Luxury accommodation for " + a.peopleCottage + " people on " + a.arrivalDate.toString("dd MMM yyyy")
    //  + " for " + a.numberNights + " night(s)")
    item.setDescription("$100 charges (rest to be paid on arrival)")
    val lineItems = List[PaymentDetailsItemType](item);

    pd.setPaymentDetailsItem(lineItems.asJava);

    val ot = new BasicAmountType();
    ot.setCurrencyID(paypalCurrency);
//    ot.setValue(a.amountString);
    ot.setValue("100.00");
    pd.setOrderTotal(ot);
    redirectToPaypal(List[PaymentDetailsType](pd), baseURL + "/accommodation", acc = a)
  }

  private def redirectToPaypal(pdl: List[PaymentDetailsType], url: String, acc: Accommodation = null, wo: WinesOrder = null) = {
    val secrdt = new SetExpressCheckoutRequestDetailsType();
    secrdt.setReturnURL(url + "/paypal");
    secrdt.setCancelURL(url);

    secrdt.setPaymentDetails(pdl.asJava);
    secrdt.setLandingPage(paypalLandingPage);

    val secrt = new SetExpressCheckoutRequestType(secrdt);
    secrt.setVersion(paypalVersion);

    val secr = new SetExpressCheckoutReq();
    secr.setSetExpressCheckoutRequest(secrt);

    val scres = service.setExpressCheckout(secr);

    val token = scres.getToken

    val file = new File("storage/" + token + ".txt")
    var data = "";

    if (acc != null) {
      data = Json.toJson(acc).toString()

    }
    else if (wo != null) {
      data = wo.toString()
    }

    printToFile(file)(p => {
      p.println(data)
    })

    Redirect(paypalURL + token)
  }

  def pay(token: String, payerid: String) = {
    val getExpressCheckoutDetailsRequestType = new GetExpressCheckoutDetailsRequestType();
    getExpressCheckoutDetailsRequestType.setToken(token);
    getExpressCheckoutDetailsRequestType.setVersion("104.0");

    val getExpressCheckoutDetailsReq = new GetExpressCheckoutDetailsReq();
    getExpressCheckoutDetailsReq.setGetExpressCheckoutDetailsRequest(getExpressCheckoutDetailsRequestType);


    val getExpressCheckoutDetailsResponse = service.getExpressCheckoutDetails(getExpressCheckoutDetailsReq);
    val a = getExpressCheckoutDetailsResponse.getGetExpressCheckoutDetailsResponseDetails.getPaymentDetails

    val doreqtype = new DoExpressCheckoutPaymentRequestType()
    val b = new DoExpressCheckoutPaymentRequestDetailsType()
    b.setPayerID(payerid)
    b.setPaymentDetails(a)
    b.setToken(token)

    doreqtype.setDoExpressCheckoutPaymentRequestDetails(b)

    val doreq = new DoExpressCheckoutPaymentReq()
    doreq.setDoExpressCheckoutPaymentRequest(doreqtype)

    val c = service.doExpressCheckoutPayment(doreq)

    if (c.getAck.getValue == "Success") {
      true
    }
    else {
      val x = scala.collection.JavaConversions.asScalaBuffer(c.getErrors()).toList

      for (d <- x) {
        println("Error: " + token + " " + d.getLongMessage)
      }
      false
    }
  }
}