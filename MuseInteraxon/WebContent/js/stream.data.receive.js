var wsUri = "ws://" + document.location.host
		+ "/MuseInteraxon/EEGStreamSocket/5";
var websocket;

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
	var bytes = "";
	if (evt.data != "" && evt.data != null && evt.data != "null")
	// bytes = parseInt(evt.data, 2).toString(10);
	// if (bytes.length > 0)
	{
		// var dataTMP = JSON.parse(binaryToString(evt.data));
		var dataTMP = JSON.parse(evt.data);
		var index = dataTMP.Concentration;
		if (index != null) {
			plotSignals(dataTMP);

		}
	}
}

function plotSignals(data) {
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

// For testing purposes
var output = document.getElementById("output");

function writeToScreen(message) {
	if (output == null) {
		output = document.getElementById("output");
	}
	// output.innerHTML += message + "";
	// console.log(message + "");
}

function sendText(json) {
	websocket.send(json);
}

var canvas2, context2, v, w, h;

function startTraining() {
	connectToTheSocket();
	// setTimeout(playNoise, 6000);
	// setTimeout(playNoise, 9000);
	// setTimeout(playNoise, 14000);
	// setTimeout(playNoise, 16000);
	// setTimeout(playNoise, 20000);
	// setTimeout(playNoise, 44000);
	// setTimeout(playNoise, 34000);
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StartHeadband");
	// streamEEG = setInterval(function() {
	// sendText(convertToBinary(canvas2.toDataURL('image/jpeg', 1.0)));
	// }, 30);

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
	// $.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StopSoundTraining");
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StopHeadband");
	// clearInterval(streamEEG);
	websocket.close();
	// audioContext.close();
	// webgazer.end();
}

$(document).ready(function() {
	refreshPods();
});
