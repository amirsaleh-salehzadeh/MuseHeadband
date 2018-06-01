var wsUri = "ws://" + document.location.host
		+ "/MuseInteraxon/EEGStreamSocket/5";
var websocket;
var isStreaming = false;
var rawData = [ 0, 0, 0, 0 ];

var resolution = 1364;
var eegGraphs;

var graphData = [
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0) ];

function refreshPods() {
	eegGraphs = graphData.map(function(podData, podIndex) {
		return $('#pod' + podIndex).plot(formatFlotData(podData), {
			colors : [ "'" + $('#pod' + podIndex).css('color') + "'" ],
			lines : {
				show : true,
				lineWidth : 1
			},
			xaxis : {
				show : false,
				min : 0,
				max : resolution
			},
			yaxis : {
				min : $('#pod' + podIndex + 'min').val(),
				max : $('#pod' + podIndex + 'max').val()
			},
			grid : {
				borderColor : "#444",
				borderWidth : 0
			}
		}).data("plot");
	});
}

var formatFlotData = function(data) {
	return [ data.map(function(val, index) {
		return [ index, val ];
	}) ];
};

var updateGraph = function(eegData) {
	graphData.map(function(data, index) {
		graphData[index] = graphData[index].slice(1);
		graphData[index].push(eegData[index]);

		eegGraphs[index].setData(formatFlotData(graphData[index]));
		eegGraphs[index].draw();
	});

};

function onMessage(evt) {
	if (evt.data != "" && evt.data != null && evt.data != "null") {
		var dataTMP = JSON.parse(evt.data);
		var index = dataTMP.Concentration;
		if (index != null) {
			plotSignals(dataTMP);

		}
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
	updateBar("X", measureAccel(data.ACC_X) / 100);
	updateBar("Y", measureAccel(data.ACC_Y) / 100);
	updateBar("Z", measureAccel(data.ACC_Z) / 100);
	updateBar("D", medi / 100);
	updateBar("F", conc / 100);
	updateBar("delta", data._delta);
	updateBar("alpha", data._alpha);
	updateBar("gamma", data._gamma);
	updateBar("theta", data._teta);
	updateBar("beta", data._beta);
	rawData[0] = data.EEG1;
	rawData[1] = data.EEG2;
	rawData[2] = data.EEG3;
	rawData[3] = data.EEG4;
	updateGraph(rawData);
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
	res = (inpt + 2000) / 40;
	return res;
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

function binaryToString(str) {
	var binaryCode = [];
	for ( var i = 0; i < str.length; i++) {
		binaryCode.push(String.fromCharCode(parseInt(str[i], 2)));
	}
	return binaryCode.join("");
}

function onError(evt) {
	console.log('<span style="color: red;">ERROR:</span> ' + evt.data);
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
	 $.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StartMeasuringAccelData");
	 movePointer2Corners();
}
var pointerX, pointerY, movePointerInterval;
function movePointer(x, y) {
	pointerX = 0;
	pointerY = 0;
	movePointerInterval = setInterval(movePointerFromLeftToRight, 100);
	$("#trackingDot").css("left", x + "px");
	$("#trackingDot").css("top", y + "px");
}

function movePointerFromLeftToRight() {
	pointerX += 20;
	if (pointerX > $(window).width() - 22) {
		pointerY += 40;
		pointerX = 0;
		if (pointerY > $(window).height() - 20) {
			clearInterval(movePointerInterval);
			movePointerInterval = setInterval(movePointer2Ways, 100);
			pointerX = 0;
			pointerY = 0;
		}
	}
	$("#trackingDot").css("left", pointerX + "px");
	$("#trackingDot").css("top", pointerY + "px");
}

var go2Ways = true;// To determine either we going or coming back.
function movePointer2Ways() {
	if (go2Ways) {
		pointerX += 20;
		if (pointerX > $(window).width() - 22) {
			pointerY += 40;
			go2Ways = false;
		}
	} else {
		pointerX -= 20;
		if (pointerX < 0) {
			pointerY += 40;
			go2Ways = true;
		}
	}
	$("#trackingDot").css("left", pointerX + "px");
	$("#trackingDot").css("top", pointerY + "px");
	if (pointerY >= $(window).height() - 22) {
		clearInterval(movePointerInterval);
		$("#trackingDot").css("left", "50%");
		$("#trackingDot").css("top", "50%");
		movePointer2Corners();
	}
}

function movePointer2Corners() {
	setTimeout(function() {
		$("#trackingDot").css("left", "0");
		$("#trackingDot").css("top", "0");
	}, 2500);
	setTimeout(function() {
		$("#trackingDot").css("left", "50%");
		$("#trackingDot").css("top", "50%");
	}, 4500);
	setTimeout(function() {
		$("#trackingDot").css("left", $(window).width() - 22);
		$("#trackingDot").css("top", "0");
	}, 6500);
	setTimeout(function() {
		$("#trackingDot").css("left", "0");
		$("#trackingDot").css("top", $(window).height() - 22);
	}, 8500);
	setTimeout(function() {
		$("#trackingDot").css("left", "50%");
		$("#trackingDot").css("top", "50%");
	}, 10500);
	setTimeout(function() {
		$("#trackingDot").css("left", $(window).width() - 22);
		$("#trackingDot").css("top", $(window).height() - 22);
	}, 12500);
	setTimeout(function() {
		$("#trackingDot").css("left", "0");
		$("#trackingDot").css("top", "0");
		 $.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StopMeasuringAccelData");
	}, 14500);
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

function stopSoundTraining() {
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StopHeadband");
	websocket.close();
}

$(document).ready(function() {
	refreshPods();
});
