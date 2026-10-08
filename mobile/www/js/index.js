/**
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.
*/

// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready
document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
    // Cordova is now initialized. Have fun!

    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');

    document.getElementById('clk').addEventListener("click", doCall);

    // alert(1);
}

function doCall() {
    alert("call!!");
    // 1. Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // 2. Configure it: GET-request for the URL
    xhr.open('GET', 'http://192.168.216.1:8080/api/hello', true);
    // xhr.open('GET', 'https://www.google.com/', true);

    alert('http://192.168.216.1:8080/api/hello');

    // 3. Set up a handler to process the response
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            // Request was successful
            var data = xhr.responseText;
            alert('Success:' + data);
        } else {
            // Server returned an error (e.g., 404 or 500)
            alert('Error status:', xhr.status);
        }
    };

    // 4. Handle network-level errors
    xhr.onerror = function (e) {
        alert('Network request failed');
        alert(JSON.stringify(e));
    };

    // 5. Send the request
    xhr.send();
}
