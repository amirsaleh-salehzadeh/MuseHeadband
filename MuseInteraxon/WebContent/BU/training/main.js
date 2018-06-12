var textnumber = 0;
var numberOfWords = 0;
var numberOfParagraphs = 0;
var paintingWordsTimer;
var colorCode = "green";
var coloredWordCounter = 0;
var readingPadWidth = 0;
var tenPercWidth = 0;
var lastTop = -1;
var noOfRows = 0;
var showFaceOverlay = true;
var cl;

function loadMenu() {
	$("#leftSidePanel").load("menu.html", function() {
		$("#leftSidePanel").panel("refresh");
	});
}

function startTime() {
	var today = new Date();
	var h = today.getHours();
	var m = today.getMinutes();
	var s = today.getSeconds();
	m = checkTime(m);
	s = checkTime(s);
	document.getElementById('txt').innerHTML = h + ":" + m + ":" + s;
	var t = setTimeout(startTime, 500);
}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i
	}
	; // add zero in front of numbers < 10
	return i;
}

window.onload = function() {
	PDFObject.embed("pdfFiles/origin.pdf", "#textContainer", {
		id : "pdfContext",
		page : 11
	});
	$("#leftSideMenuBTN").trigger("click");
	$(".ui-panel-wrapper").click(function() {
		$("#leftSidePanel").panel("close");
	});
	$("#pdfContext").click(function() {
		$("#leftSidePanel").panel("close");
	});
	loadMenu();
	var screen = $.mobile.getScreenHeight();
	var header = $(".ui-header").hasClass("ui-header-fixed") ? $(".ui-header")
			.outerHeight() - 1 : $(".ui-header").outerHeight();
	var footer = $(".ui-footer").hasClass("ui-footer-fixed") ? $(".ui-footer")
			.outerHeight() - 1 : $(".ui-footer").outerHeight();
	var contentCurrent = $(".ui-content").outerHeight()
			- $(".ui-content").height();
	var content = screen - header - footer - contentCurrent;
	$(".ui-content").height(content);
	var width = Math.round($("#leftSidePanel").width());
	var height = Math.round(width / 1.33);
	var leftDist = '0px';
	$("#textContainerMainDiv").width($(window).width());
	$("#textContainerMainDiv").height($(window).height()).trigger('create');
	var setup = function() {
		webgazer.params.videoElementId = "webgazerVideoFeed";
		var video = document.getElementById('webgazerVideoFeed');
		var video2 = document.getElementById('webgazerVideoPresentation');
		video.style.display = 'none';
		video.style.bottom = '0px';
		video.style.left = leftDist;
		video.style.position = 'absolute';
		video.width = $(window).width();
		video.height = $(window).height();
		video.style.margin = '0px';
		video.style.background = '#222222';
		video.style.zIndex = '-1';

		video2.src = video.src;
		video2.style.display = 'block';
		video2.style.bottom = '0px';
		video2.style.left = leftDist;
		video2.style.position = 'absolute';
		video2.width = width;
		video2.height = height;
		video2.style.margin = '0px';
		video2.style.zIndex = '-1';
		webgazer.params.imgWidth = width;
		webgazer.params.imgHeight = height;
		$("#webGazerContainer").width(width);
		$("#webGazerContainer").height(height);
		// Set up the main canvas. The main canvas is used to calibrate the
		// webgazer.
		var overlay = document.createElement('canvas');
		overlay.id = 'overlay';

		// Setup the size of canvas
		overlay.width = width;
		overlay.height = height;
		overlay.style.bottom = '0px';
		overlay.style.left = leftDist;
		overlay.style.margin = '0px';
		overlay.style.position = 'absolute';

		// Draw the face overlay on the camera video feedback
		var faceOverlay = document.createElement('face_overlay');
		faceOverlay.id = 'faceOverlay';
		faceOverlay.style.position = 'absolute';
		faceOverlay.style.top = '59px';
		faceOverlay.style.left = '107px';
		faceOverlay.style.border = 'solid';
		faceOverlay.style.zIndex = '22';

		overlay.style.zIndex = '33';
		document.getElementById("webGazerContainer").appendChild(video);
		document.getElementById("webGazerContainer").appendChild(overlay);
		document.getElementById("webGazerContainer").appendChild(faceOverlay);

		var canvas = document.getElementById("plotting_canvas");
		canvas.width = window.innerWidth;
		canvas.height = window.innerHeight;
		canvas.style.position = 'fixed';
		canvas.style.top = '0px';
		canvas.style.right = '0px';
		canvas.style.left = '0px';
		canvas.style.bottom = '0px';
		cl = webgazer.getTracker().clm;
		$("#webGazerContainer").trigger("create");
		// This function draw the face of the user frame.
		function drawLoop() {
			requestAnimFrame(drawLoop);
			overlay.getContext('2d').clearRect(0, 0, width, height);
			if (cl.getCurrentPosition()) {
				cl.draw(overlay);
			}
			$("#microphone").css("height", "0%");
			$("#microphone").parent().css("background-color",
					getColorForPercentage(1 - meter.volume));
			$("#volume").val(1 - meter.volume);
		}
		drawLoop();
		// evaluateAccuracy();
	};
	webgazer.setRegression('ridge').setTracker('clmtrackr').setGazeListener(
			function(data, clock) {
				if (data != null) {
					evaluateTheGazedPosition(data, clock);
				}
			}).begin().showPredictionPoints(true);
	function checkIfReady() {
		if (webgazer.isReady()) {
			setup();
		} else {
			setTimeout(checkIfReady, 100);
		}
	}
	setTimeout(checkIfReady, 100);

};

window.onbeforeunload = function() {
	webgazer.end();
	// window.localStorage.clear();
};

function Restart() {
	document.getElementById("Accuracy").innerHTML = "Not yet Calibrated";
	ClearCalibration();
	PopUpInstruction();
	$("#leftSidePanel").panel("close");
	$("#Accuracy").trigger("create");
}
