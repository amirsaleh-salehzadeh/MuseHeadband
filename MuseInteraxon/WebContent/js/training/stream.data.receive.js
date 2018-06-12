var wsUri = "ws://" + document.location.host
		+ "/MuseInteraxon/EEGStreamSocket/5";
var websocket;
var isStreaming = false;

function onMessage(evt) {
	if (evt.data != "" && evt.data != null && evt.data != "null") {
		var dataTMP = JSON.parse(evt.data);
		var index = dataTMP.Concentration;
		if (index != null) {
			plotSignals(dataTMP);
		}
	}
}

function getColor(l) {
	switch (l) {
	case 0:
		return '#00ff00';
		break;
	case 1:
		return '#00ff00';
		break;
	case 2:
		return '#FF9900';
		break;
	case 3:
		return '#FF9900';
		break;
	case 4:
		return '#ff0000';
		break;
	default:
		return '#ff0000';
	}
}

function plotSignals(data) {
	isStreaming = true;
	var conc = Math.round(data.Concentration);
	var medi = Math.round(data.Meditation);
	if (conc == null)
		conc = 0;
	if (medi == null)
		medi = 0;
	updateBar("X", (data.ACC_X + 1)/2);
	updateBar("Y", (data.ACC_Y + 1)/2);
	updateBar("Z", (data.ACC_Z + 1)/2);
	updateBar("D", medi / 100);
	updateBar("F", conc / 100);
	updateBar("delta", data._delta);
	updateBar("alpha", data._alpha);
	updateBar("gamma", data._gamma);
	updateBar("theta", data._teta);
	updateBar("beta", data._beta);
	updateBar("MWPrediction",data.predictions.dl);
	if (data.foreheadConneted)
		$("#hs3").css('background-color', 'green');
	else
		$("#hs3").css('background-color', 'red');
	if (data.blink)
		$("#blinkContainer").css('visibility', 'visible');
	else
		$("#blinkContainer").css('visibility', 'hidden');
	$("#battVal").html(Math.round((data.battery / 100)) + "%");
	for ( var int = 0; int < data.horseShoes.length; int++) {
		if (int < 2) {
			$("#hs" + (int + 1)).css('background-color',
					getColor(data.horseShoes[int]));
		} else if (int >= 2) {
			$("#hs" + (int + 2)).css('background-color',
					getColor(data.horseShoes[int]));
		}
	}
}
function measureAccel(inpt) {
	var res = 0;
	res = (inpt + 2000) / 20;
	return res;
}

function binaryToString(str) {
	var binaryCode = [];
	for ( var i = 0; i < str.length; i++) {
		binaryCode.push(String.fromCharCode(parseInt(str[i], 2)));
	}
	return binaryCode.join("");
}

function onError(evt) {
	alert('ERROR: ' + evt.data);
}

function onOpen() {
	console.log("Connected to " + wsUri);
}

function onClose(evt) {
	console.log("closing websockets: " + evt.data);
	websocket.close();
}

function sendText(json) {
	websocket.send(json);
}

var canvas2, context2, v, w, h;

function startTraining() {
	 connectToTheSocket();
	 $.get("http://localhost:8080/MuseInteraxon/REST/GetWS/ConnectHeadband");
}

function connectToTheSocket() {
	websocket = new WebSocket(wsUri);
	websocket.onmessage = function(evt) {
		onMessage(evt);
	};
	websocket.onerror = function(evt) {
		onError(evt);
	};
	websocket.onopen = function(evt) {
		onOpen(evt);
	};
	websocket.onclose = function(evt) {
		onClose(evt);
	};
}

function convertToBinary(dataURI) {
	var byteString = atob(dataURI.split(',')[1]);
	var ab = new ArrayBuffer(byteString.length);
	var ia = new Uint8Array(ab);
	for ( var i = 0; i < byteString.length; i++) {
		ia[i] = byteString.charCodeAt(i);
	}
	var bb = new Blob([ ab ]);
	return bb;
}

function convertToBinary(dataURI) {
	var byteString = atob(dataURI.split(',')[1]);
	var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]
	var ab = new ArrayBuffer(byteString.length);
	var ia = new Uint8Array(ab);
	for ( var i = 0; i < byteString.length; i++) {
		ia[i] = byteString.charCodeAt(i);
	}
	var bb = new Blob([ ab ]);
	return bb;
}

function finishRecording() {
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StopHeadband");
	if(websocket!= null && websocket.readyState !== websocket.CLOSED) {
		websocket.close();
	}
}
