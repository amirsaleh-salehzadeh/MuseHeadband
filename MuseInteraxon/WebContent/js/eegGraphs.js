var streamEEG;
function startEEG() {
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StartHeadband");
	streamEEG = setInterval(function() {
		plotEEGSignals();
	}, 5);
}

function stopEEG() {
	$.get("http://localhost:8080/MuseInteraxon/REST/GetWS/StopHeadband");
	clearInterval(streamEEG);
}

function plotEEGSignals() {
	$.ajax({
		url : "http://localhost:8080/MuseInteraxon/REST/GetWS/GetEEG",
		dataType : "json",
		cache : false,
		async : true,
		success : function(data) {
			rawData[0] = data.EEG1;
			rawData[1] = data.EEG2;
			rawData[2] = data.EEG3;
			rawData[3] = data.EEG4;
			rawData[4] = data.lowFreq;
			rawData[5] = data.alpha;
			rawData[6] = data.beta;
			rawData[7] = data.gamma;
			rawData[8] = data.teta;
			rawData[9] = data.delta;
			rawData[10] = data._alpha;
			rawData[11] = data._beta;
			rawData[12] = data._gamma;
			rawData[13] = data._teta;
			rawData[14] = data._delta;
			rawData[15] = data.ACC_X;
			rawData[16] = data.ACC_Y;
			rawData[17] = data.ACC_Z;
			if (data.Concentration != null)
				rawData[18] = data.Concentration;
			else
				rawData[18] = 0;
			if (data.Meditation != null)
				rawData[19] = data.Meditation;
			else
				rawData[19] = 0;
			if (data.foreheadConneted)
				$("#hs3").css('background-color', 'green');
			else
				$("#hs3").css('background-color', 'red');
			if (data.blink)
				$("#blinkContainer").css('display', 'block');
			else
				$("#blinkContainer").css('display', 'none');
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

function showDiv(x) {
	$('.description#' + x).css('display', 'block');
}
function hideDiv(x) {
	$('.description#' + x).css('display', 'none');
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
var rawData = [ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ];

var resolution = 1364;
var eegGraphs;

var graphData = [
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0) ];

$(document).ready(function() {
	$(".selectorCollapsible").on("collapsibleexpand", function() {
		refreshPods();
	});
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