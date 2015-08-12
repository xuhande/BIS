//
//var itemNumber=new Array;
//var productName=new Array;
//var formName=new Array;
//var productDescription=new Array;
//var priceWeb=new Array;
//var priceRRP=new Array;
//var itemMultiplier=new Array;
//
//itemNumber[0]="1.";
//productName[0]="2011 Peche de Noire - Dry Rose";
//productDescription[0]="Single bottle each 750 mls";
//formName[0]="dryRose";
//priceWeb[0]=27.50;
//priceRRP[0]=29.00;
//itemMultiplier[0]=1;
//
//itemNumber[1]="2.";
//productName[1]="2013 Pinot Rose";
//productDescription[1]="Single bottle each 750 mls";
//formName[1]="pinotRose";
//priceWeb[1]=27.50;
//priceRRP[1]=29.00;
//itemMultiplier[1]=1;
//
//itemNumber[2]="3.";
//productName[2]="2010 Dry Riesling";
//productDescription[2]="Single bottle each 750 mls";
//formName[2]="dryRiesling";
//priceWeb[2]=27.50;
//priceRRP[2]=29.00;
//itemMultiplier[2]=1;
//
//itemNumber[3]="4.";
//productName[3]="2013 Classic Riesling";
//productDescription[3]="Single bottle each 750 mls";
//formName[3]="classicRiesling";
//priceWeb[3]=27.50;
//priceRRP[3]=29.00;
//itemMultiplier[3]=1;
//
//itemNumber[4]="5.";
//productName[4]="2012 Bliss - Sparkling Riesling";
//productDescription[4]="Single bottle each 750 mls";
//formName[4]="bliss";
//priceWeb[4]=29.50;
//priceRRP[4]=31.00;
//itemMultiplier[4]=1;
//
//itemNumber[5]="6.";
//productName[5]="2011 Pinot Noir";
//productDescription[5]="Single bottle each 750 mls";
//formName[5]="pinotNoir";
//priceWeb[5]=37.00;
//priceRRP[5]=39.00;
//itemMultiplier[5]=1;
//
//itemNumber[6]="7.";
//productName[6]="2011 Pinot Noir - Half Bottle";
//productDescription[6]="Single bottle each 375 mls";
//formName[6]="pinotNoirHalf";
//priceWeb[6]=19.50;
//priceRRP[6]=20.50;
//itemMultiplier[6]=1;
//
//itemNumber[7]="8.";
//productName[7]="2011 Reserve Pinot Noir";
//productDescription[7]="Single bottle each 750 mls";
//formName[7]="reservePinotNoir11";
//priceWeb[7]=65.00;
//priceRRP[7]=69.00;
//itemMultiplier[7]=1;
//
//itemNumber[8]="9.";
//productName[8]="2009 Reserve Pinot Noir";
//productDescription[8]="Single bottle each 750 mls";
//formName[8]="reservePinotNoir09";
//priceWeb[8]=65.00;
//priceRRP[8]=69.00;
//itemMultiplier[8]=1;
//
//itemNumber[9]="10.";
//productName[9]="2012 Mad Rooster";
//productDescription[9]="Single bottle each 750 mls";
//formName[9]="madRooster";
//priceWeb[9]=32.00;
//priceRRP[9]=34.00;
//itemMultiplier[9]=1;
//
//var fieldID = 0;
//
//
//var prodSubTotal = 0; //总价格数
//
//function DP2S ( fPrice ) {
//  // convert to string, and round up 2 dp
//  var sCurrency = "" + ( fPrice + 0.00500000001 );
//
//  // find .
//  var nPos = sCurrency.indexOf ( '.' );
//
//  if (nPos<0) {
//    sCurrency += '.00';
//  } else {
//    // hack off 3rd dp
//    sCurrency = sCurrency.slice ( 0, nPos + 3);
//    // add up to 2 trailing zeroes if necessary
//    var nZero = 3 - ( sCurrency.length - nPos );
//    for ( var i=0; i<nZero; i++)
//    sCurrency += '0';
//  }
//
//  return sCurrency;
//
//}
//function IsNumeric(sText)
//
//{
//  var ValidChars = "0123456789.";
//  var IsNumber=true;
//  var Char;
//
//
//  for (i = 0; i < sText.length && IsNumber == true; i++)
//  {
//    Char = sText.charAt(i);
//    if (ValidChars.indexOf(Char) == -1)
//    {
//      IsNumber = false;
//    }
//  }
//  return IsNumber;
//
//};
//
//function calcProdSubTotal() {
//
//  var prodSubTotal = 0;
//  $(".row-total-input").each(function(){
//
//    var valString = $(this).val() || 0;
//
//    prodSubTotal += parseFloat(valString);
//
//  });
//
//  $("#product-subtotal").text(prodSubTotal);
//  console.log(prodSubTotal);
//};
//
//function calcTotalPallets() {
//
//  var totalPallets = 0;
//  var thisValue ;
//  $(".num-pallets-input").each(function() {
//
//    var thisValue = $(this).val();
//
//    if ( (IsNumeric(thisValue)) &&  (thisValue != '') ) {
//
//      totalPallets += parseFloat(thisValue);
//
//    };
//
//  });
//
//  $("#total-pallets-input").val(totalPallets);
//  console.log(totalPallets);
//};
//
////function calcShippingTotal() {
////
////    var totalPallets = $("#total-pallets-input").val() || 0;
////    var shippingRate = $("#shipping-rate").text() || 0;
////    var shippingTotal = totalPallets * shippingRate;
////
////    $("#shipping-subtotal").val(shippingTotal);
////
////};
//
//function calcOrderTotal() {
//
//  var orderTotal = 0;
//  var productSubtotal = $("#product-subtotal").text() || 0;
//  var shippingSubtotal = $("#shipping-subtotal option").val() || 0;
//  var shippingHidden = $("#shipping-hidden").val();
//  var orderTotal = parseFloat(productSubtotal) + parseFloat(shippingSubtotal) +parseFloat(shippingHidden);
//  var orderTotalNice = orderTotal;
//  $("#order-total").text(orderTotalNice);
//  $("#amount").val(orderTotalNice);
//  $("#shipping-subtotal").change(function(){
//
//    var shippingSubtotal=$("#shipping-subtotal").val() || 0;
//    $("#product-freight").text(shippingSubtotal);
//    if(shippingSubtotal != 0){
//      $("#shipping-hidden").attr("value",shippingSubtotal);
//      var orderTotal = parseFloat(productSubtotal) + parseFloat(shippingSubtotal);
//      var orderTotalNice =orderTotal;
//      $("#order-total").text(orderTotalNice);
//      $("#amount").val(orderTotalNice);
//
//    }else{
//      $("#shipping-hidden").attr("value",0);
//      var orderTotal = parseFloat(productSubtotal) - parseFloat(shippingSubtotal);
//      var orderTotalNice =orderTotal;
//      $("#order-total").text(orderTotalNice);
//      $("#amount").val(orderTotalNice);
//    }
//  });
//};