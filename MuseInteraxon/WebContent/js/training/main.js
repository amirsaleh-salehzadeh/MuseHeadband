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

function startTimer() {
	var today = new Date();
	var h = today.getHours();
	var m = today.getMinutes();
	var s = today.getSeconds();
	m = checkTime(m);
	s = checkTime(s);
	document.getElementById('timerSpan').innerHTML = h + ":" + m + ":" + s;
	var t = setTimeout(startTimer, 1000);
}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i
	}
	;// add zero in front of numbers < 10
	return i;
}

function loadMenu() {
	$("#leftSidePanel").load("menu.html", function() {
		$("#leftSidePanel").trigger("create");
		$("#leftSidePanel").panel("open");
	});

}
var pauseOverlay = false;
window.onload = function() {
	startTimer();
	loadText(2);
	loadMenu();
	$(".ui-content").height($.mobile.getScreenHeight());
	var leftDist = '0px';
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
		var width = Math.round($("#leftSidePanel").width());
		var height = Math.round(width / 1.33);
		video2.src = video.src;
		video2.style.display = 'block';
		video2.style.bottom = '0px';
		video2.style.left = leftDist;
		video2.style.position = 'absolute';
		video2.width = width;
		video2.height = height;
		video2.style.margin = '0px';
		video2.style.zIndex = '22';
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
			if (pauseOverlay)
				return;
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
		$("#leftSidePanel").panel({
			beforeopen : function(event, ui) {
				pauseOverlay = false;
				drawLoop();
			}
		});
		$("#leftSidePanel").on("panelclose", function(event, ui) {
			pauseOverlay = true;
		});
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

function eyeTrackerPS() {

}

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
