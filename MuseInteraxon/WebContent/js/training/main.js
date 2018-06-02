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

window.onload = function() {
	// loadText();
	// start the webgazer tracker
	var width = 320;
	var height = 240;
	var topDist = '0px';
	var leftDist = '0px';

	// Set up the webgazer video feedback.
	var setup = function() {
		// Set up video variable to store the camera feedback
		var video = document.getElementById('webgazerVideoFeed');

		// Position the camera feedback to the top left corner.
		video.style.display = 'block';
		video.style.position = 'fixed';
		video.style.top = topDist;
		video.style.left = leftDist;

		// Set up the video feedback box size
		video.width = width;
		video.height = height;
		video.style.margin = '0px';
		video.style.background = '#222222';
		webgazer.params.imgWidth = width;
		webgazer.params.imgHeight = height;

		// Set up the main canvas. The main canvas is used to calibrate the
		// webgazer.
		var overlay = document.createElement('canvas');
		overlay.id = 'overlay';

		// Setup the size of canvas
		overlay.style.position = 'fixed';
		overlay.width = width;
		overlay.height = height;
		overlay.style.top = topDist;
		overlay.style.left = leftDist;
		overlay.style.margin = '0px';

		// Draw the face overlay on the camera video feedback
		var faceOverlay = document.createElement('face_overlay');
		faceOverlay.id = 'faceOverlay';
		faceOverlay.style.position = 'fixed';
		faceOverlay.style.top = '59px';
		faceOverlay.style.left = '107px';
		faceOverlay.style.border = 'solid';

		document.getElementById("webGazerContainer").appendChild(overlay);
		document.getElementById("webGazerContainer").appendChild(faceOverlay);

		var canvas = document.getElementById("plotting_canvas");
		canvas.width = window.innerWidth;
		canvas.height = window.innerHeight;
		canvas.style.position = 'fixed';
		canvas.style.top = '0px';
		canvas.style.right = '0px';
		canvas.style.left = '0px';
		var cl = webgazer.getTracker().clm;

		// This function draw the face of the user frame.
		function drawLoop() {
			requestAnimFrame(drawLoop);
			overlay.getContext('2d').clearRect(0, 0, width, height);
			if (cl.getCurrentPosition()) {
				cl.draw(overlay);
			}
		}
		drawLoop();
		evaluateAccuracy();
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

function loadText() {
	textnumber++;
	$("#textContainer").load(
			'text' + textnumber + '.html',
			function() {
				$("#questionnaireFormDIV").css("display", "none").trigger(
						'create');
				// ADDING THE WORDS TO SPANS
				var words = $("#readingText").html().split(" ");
				var newWords = "<p id='0' data-visited-counter='0'>";
				for (var p = 0; p < words.length; p++) {
					if (words[p].length <= 1)
						continue;
					if (words[p].indexOf("<br") == -1) {
						newWords += "<span class='spanWordContainer' id='" + p
								+ "' >" + words[p] + "&nbsp;</span>";
						numberOfWords++;
					} else if (p > 0) {
						numberOfParagraphs++;
						newWords += "</p><p id='" + numberOfParagraphs
								+ "' data-visited-counter='0'>";
					}
				}
				newWords += "</p>";
				$("#readingText").html(newWords).trigger("create");
				$("#noOfWords").html(numberOfWords);
				paintingWordsTimer = setInterval(paintTheWord, 30);
				$("#textContainer").width($(window).width() - 366);
				$("#textContainer").height($(window).height())
						.trigger('create');
				tenPercWidth = 0.1 * $("#readingText").width();
				// COUNTING THE LINE NUMBERS
				$("#readingText p").each(function(j, t) {
					if ($(t).html().length <= 0) {
						$(t).remove();
						return false;
					}
					paragraphCoordinates.push(t);
					sot(t);
				});
				$("#noOfLines").html(noOfRows);
			}).trigger('create');
}

function sot(t) {
	var rowDivs = "";
	$(t)
			.children()
			.each(
					function(k, l) {

						if (lastTop == -1 || lastTop != $(l).position().top) {
							lastTop = $(l).position().top;
							noOfRows++;
							if (rowDivs.length <= 0)
								rowDivs += "<div class='ui-block-solo rowDivs' id='"
										+ noOfRows + "'>";
							else
								rowDivs += "</div><div class='ui-block-solo rowDivs' id='"
										+ noOfRows + "'>";
						}
						rowDivs += "<span class='spanWordContainer' id='" + k
								+ "' >" + $(l).html() + "</span>";

					});
	rowDivs += "</div>";
	$(t).html(rowDivs).trigger("create");
}

/**
 * Restart the calibration process by clearing the local storage and reseting
 * the calibration point
 */
function Restart() {
	document.getElementById("Accuracy").innerHTML = "Not yet Calibrated";
	ClearCalibration();
	PopUpInstruction();
	$("#Accuracy").trigger("create");
}
