function readingStart() {
	$(document).click(function() {
		$("#distractionPopup").popup("open");
	});
	sessionStamp = setInterval(intervalTimeStamp, 200);

}

function readingPause() {
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
}