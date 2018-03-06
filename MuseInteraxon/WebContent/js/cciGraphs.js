var streamEEG;
function startEEG() {
	refreshPods();
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StartHeadband");
	streamEEG = setInterval(function() {
		plotEEGSignals();
	}, 5);
//	webgazer.setRegression("threadedRidge");// https://webgazer.cs.brown.edu
}

function stopEEG() {
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StopHeadband");
	clearInterval(streamEEG);
	webgazer.end();
}

function plotEEGSignals() {
	$.ajax({
		url : "http://localhost:8080/MuseInteraxon/REST/GetWS/GetEEG",
		dataType : "json",
		cache : true,
		async : true,
		success : function(data) {
			var conc = Math.round(data.Concentration);
			var medi = Math.round(data.Meditation);
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
	});
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

$(document).ready(
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