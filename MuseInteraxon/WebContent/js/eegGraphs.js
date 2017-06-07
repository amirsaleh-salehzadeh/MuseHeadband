function plotEEGSignals() {
	$.ajax({
		url : "http://localhost:8090/MuseInteraxon/REST/GetWS/GetEEG",
		dataType : "json",
		cache : false,
		async : false,
		success : function(data) {
			rawData[0] = data.EEG1;
			rawData[1] = data.EEG2;
			rawData[2] = data.EEG3;
			rawData[3] = data.EEG4;
		}
	});
	updateGraph(rawData);
}
setInterval(function() {
	plotEEGSignals();
}, 3);
var rawData = [ 0, 0, 0, 0 ];

var range = 2000;
var resolution = 1364;
var eegGraphs;

var graphData = [ Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0),
		Array.apply(null, Array(resolution)).map(Number.prototype.valueOf, 0) ];

$(document).ready(function() {

	eegGraphs = graphData.map(function(podData, podIndex) {
		return $('#pod' + podIndex).plot(formatFlotData(podData), {
			colors : [ '#8aceb5' ],
			xaxis : {
				show : false,
				min : 0,
				max : resolution
			},
			yaxis : {
				min : 0,
				max : range,
			},
			grid : {
				borderColor : "#427F78",
				borderWidth : 1
			}
		}).data("plot");
	});

});

var formatFlotData = function(data) {
	return [ data.map(function(val, index) {
		return [ index, val ]
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