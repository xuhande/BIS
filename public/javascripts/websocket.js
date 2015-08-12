//
//var wsUri = "ws://localhost:9002/websocket";
//var output;
//
// function init() {
//  output = document.getElementById("output");
//
//       testWebSocket();
// }
//function testWebSocket() {
//   websocket = new WebSocket(wsUri);
//
//websocket.onopen = function(evt) {
//         onOpen(evt)
//   };
//   websocket.onclose = function(evt) {
//     onClose(evt)
//   };
//websocket.onmessage = function(evt) {
//     onMessage(evt) };
//      websocket.onerror = function(evt) {
//     onError(evt)
//     };
//
//   }
//       var sendMessage = function() {
//    chatSocket.send(JSON.stringify( ))
//
//    }
//   function onOpen(evt) {
//     writeToScreen("CONNECTED");
//        //doSend("WebSocket rocks");
//        //sendMessage("String")
//     }
//
//   function onClose(evt) {
//      writeToScreen("DISCONNECTED");
//    }
//   function onMessage(evt) {
////    var contact = JSON.parse(evt.data);
//      writeToScreen(evt.data);
//         //websocket.close();
//      }
//    function onError(evt) {
//        writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
//    }
//    function doSend(message) {
//         writeToScreen("SENT: " + message);
//         websocket.send(message);
//     }
//     function writeToScreen(message) {
//
//    var pre = document.createElement("p");
//    pre.style.wordWrap = "break-word";
//    pre.innerHTML = message;
//   output.appendChild(pre);
//
//    }
//
//    window.addEventListener("load", init, false);
//<script>
//var wsUri = "ws://localhost:9000/ppppp";
//var output;
// function init(str) {
//  output = document.getElementById("output");
//  console.log(str);
//  testWebSocket();
// }
// function testWebSocket() {
//   websocket = new WebSocket(wsUri);
//   websocket.onopen = function(evt) {
//     onOpen(evt)
//   };
//   websocket.onclose = function(evt) {
//     onClose(evt)
//   };
//   websocket.onmessage = function(evt) {
//     onMessage(evt) }; websocket.onerror = function(evt) {
//     onError(evt)
//     };
//   }
//       var sendMessage = function() {
//        chatSocket.send(JSON.stringify(
//
//        ))
//
//    }
//   function onOpen(evt) {
//     writeToScreen("CONNECTED");
//      doSend('{"request":"user", "username":"aaa"}');
//        //doSend(evt);
//        //sendMessage("String")
//     }
//
//   function onClose(evt) {
//      writeToScreen("DISCONNECTED");
//    }
//   function onMessage(evt) {
//      writeToScreen(evt.data);
//         //websocket.close();
//      }
//    function onError(evt) {
//        writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
//    }
//    function doSend(message) {
//         writeToScreen("SENT: " + message);  websocket.send(message);
//     }
//     function writeToScreen(message) {
//
//    //var pre = document.createElement("p");
//    //pre.style.wordWrap = "break-word";
//    //pre.innerHTML = message;
//   //output.appendChild(pre);
//  // console.log(message);
//    var obj = $.parseJSON(message);
//console.log(output);
//$("#projectlist").append("");
//    $.each(obj, function(id, item) {
//
//       //$("#output").append("<table id='projectlist'></table>");
//       $("#projectlist > tbody").append("<tr><td>"+item.uuid+"</td><td>"+item.name+"</td><td>"+item.stauts+"</td><td>"+item.owner+"</td><td>"+item.description+"</td><td>"+item.created+"</td><td><a href=/project/edit/"+item.uuid+" >编辑</a><a href=/project/"+item.uuid+"/edit >删除</a></td></tr>");
//    });
//    }
//   window.addEventListener("load", init("ad"), false);
//
//</script>

/**
*author: mr.kang
*/
$(function() {
   $.fn.websock = function(options) {
      var defaults = {
         url : "",
         query : "",
         exec: function(message) {console.log(message)}
      };



      var options = $.extend(defaults,options);

          var wsUri = options.url;
          console.log(wsUri);
        function init(str) {
         testWebSocket();
        }
        function testWebSocket() {
          websocket = new WebSocket(wsUri);
          websocket.onopen = function(evt) {
            onOpen(evt)
          };
          websocket.onclose = function(evt) {
            onClose(evt)
          };
          websocket.onmessage = function(evt) {
            onMessage(evt) }; websocket.onerror = function(evt) {
            onError(evt)
            };
          }
              var sendMessage = function() {
               chatSocket.send(JSON.stringify(

               ))

           }
          function onOpen(evt) {
            //writeToScreen("CONNECTED");
            if(options.query != ""){
            console.log(options.query);
             doSend(options.query);
              }
//               doSend(evt);
               //sendMessage("String")
            }

          function onClose(evt) {
            // writeToScreen("DISCONNECTED");
           }
          function onMessage(evt) {
             writeToScreen(evt.data);
                websocket.close();
             }
           function onError(evt) {
//               writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
           }
           function doSend(message) {
               // writeToScreen("SENT: " + message);
                  websocket.send(message);

            }
            function writeToScreen(message) {
                    options.exec(message);
           }
          window.addEventListener("load", init(options.functions), false);
   }
});



