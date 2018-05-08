var wsUri = "ws://" + document.location.host
		+ "/MuseInteraxon/EEGStreamSocket/5";
var websocket;

function onMessage(evt) {
	var bytes = "";
	if (evt.data != "" && evt.data != null && evt.data != "null")
	// bytes = parseInt(evt.data, 2).toString(10);
	// if (bytes.length > 0)
	{
		// var dataTMP = JSON.parse(binaryToString(evt.data));
		var dataTMP = JSON.parse(evt.data);
		var index = dataTMP.indexOf("Concentration");
		if (index > 1) {
			plotEEGSignals(dataTMP);
		}
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

// For testing purposes
var output = document.getElementById("output");

function writeToScreen(message) {
	if (output == null) {
		output = document.getElementById("output");
	}
	// output.innerHTML += message + "";
	// console.log(message + "");
}

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

function sendText(json) {
	websocket.send(json);
}

var streamEEG;
var canvas2, context2, v, w, h;
function startEEG() {
	refreshPods();
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StartHeadband");
	v = document.getElementById('webgazerVideoFeed');
	canvas2 = document.getElementById('webgazerVideoCanvas');
	context2 = canvas2.getContext('2d');
	w = canvas2.width;
	h = canvas2.height;
	streamEEG = setInterval(function() {
		context2.drawImage(v, 0, 0, w, h);
		sendText(convertToBinary(canvas2.toDataURL('image/jpeg', 1.0)));
	}, 30);
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

function stopEEG() {
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StopHeadband");
	clearInterval(streamEEG);
	websocket.close();
	// webgazer.end();
}

function plotEEGSignals(dataStream) {
	var conc = Math.round(dataStream.Concentration);
	var medi = Math.round(dataStream.Meditation);
	if (conc == null)
		conc = 0;
	rawData[0] = conc;
	if (medi == null)
		medi = 0;
	$("#concBar").html("Focused " + conc + " %");
	if (conc > 15)
		$("#concBar").css("width", conc + "%");
	$("#dreamBar").html("Dreaming " + medi + " %");
	if (medi > 15)
		$("#dreamBar").css("width", medi + "%");
	if (dataStream.foreheadConneted)
		$("#hs3").css('background-color', 'green');
	else
		$("#hs3").css('background-color', 'red');
	if (dataStream.blink)
		$("#blinkContainer").css('visibility', 'visible');
	else
		$("#blinkContainer").css('visibility', 'hidden');
	$("#battVal").html(Math.round((dataStream.battery / 100)) + "%");
	for ( var int = 0; int < dataStream.horseShoes.length; int++) {
		if (int < 2) {
			$("#hs" + (int + 1)).css('background-color',
					getColor(dataStream.horseShoes[int]));
		} else if (int >= 2) {
			$("#hs" + (int + 2)).css('background-color',
					getColor(dataStream.horseShoes[int]));
		}
	}
	updateGraph(rawData);
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
var rawData = [ 0 ];

var resolution = 10000;
var eegGraphs;

var graphData = [ Array.apply(null, Array(resolution)).map(
		Number.prototype.valueOf, 0) ];

$(document)
		.ready(
				function() {
					refreshPods();
					$("#textContainer").height(
							$(window).height()
									- ($("#pod0").height() + $("#headerDiv")
											.height()));
					$("#textContainer").css("top",
							$("#pod0").height() + $("#headerDiv").height());
				});

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
				min : 0,
				max : 100
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