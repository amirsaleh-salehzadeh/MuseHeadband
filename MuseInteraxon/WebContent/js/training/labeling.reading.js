var timeSnaps = [];
function readingStart() {
	$(document).click(function() {
		$("#distractionPopup").popup("open");
	});
	$.ajax({
		url : "http://localhost:8080/MuseInteraxon/REST/GetWS/StartRecording",
		dataType : "json",
		async : true,
		success : function(data) {
			return;
		}
	});
	var d = new Date();
	var n = d.getMilliseconds() + (d.getSeconds() * 1000) + + (d.getMinutes() * 60000);
	timeSnaps.push(d.getMilliseconds());
	console.log(d.getMilliseconds());
	sessionStamp = setInterval(intervalTimeStamp, 50);
}

function readingPause() {
	var n = d.getMilliseconds() + (d.getSeconds() * 1000) + + (d.getMinutes() * 60000);
	timeSnaps.push(d.getMilliseconds());
	console.log(d.getMilliseconds());
	$(document).click(function() {
		$("#distractionPopup").popup("open");
	});
}

function readingDone() {
	$(document).click(function() {
		$("#distractionPopup").popup("open");
	});
}

var sessionStamp;
function intervalTimeStamp() {
	// var evalDivHeight = ($("#sizer").context.height() *
	// $("#page-indicator").context.top)
	// / $("#pageContents").height();
	// $("#evaluationDiv").height(evalDivHeight);
	console.log($("#pdfContext").contents().find("div#sizer").attr("id"));
	var faceFeatures = $('#tags').val().split(";");
	sendText(JSON.stringify(faceFeatures));
}