var nextWordPosiX = 0;
var distX = 0;
var distY = 0;
var paragraphCoordinates = [];

var xPredicted = [];
var yPredicted = [];
var sumx = 0, sumy = 0;
function evaluateTheGazedPosition(data, clock) {
	xPredicted.push(data.x);
	yPredicted.push(data.y);
	if (xPredicted.length > 5) {
		xPredicted = xPredicted.slice(1, 5);
		yPredicted = yPredicted.slice(1, 5);
		sumx = 0;
		sumy = 0;
		for ( var i = 0; i < xPredicted.length; i++) {
			sumx += xPredicted[i];
			sumy += yPredicted[i];
		}
		distX = sumx / 10;
		distY = sumy / 10;
	}
	
//	highlightTheGazedParagraph(distX, distY);
}

function calculateDistance(elem, mouseX, mouseY) {
	return Math.floor(Math.sqrt(Math.pow(mouseX - elem.offset().left, 2)
			+ Math.pow(mouseY - elem.offset().top, 2)));
	// return Math.floor(Math.sqrt(Math.pow(mouseX
	// - (elem.offset().left + (elem.width() / 2)), 2)
	// + Math.pow(mouseY - (elem.offset().top + (elem.height() / 2)), 2)));
}

var lastParagraph = 0;
var closestOne = 0;
var expectedParagraph = 0;
function highlightTheGazedParagraph(x, y) {
	var closestDistance = 0;
	for ( var i = 0; i < paragraphCoordinates.length; i++) {
		if (closestDistance <= 0
				|| closestDistance > calculateDistance(
						$(paragraphCoordinates[i]), x, y)) {
			closestDistance = calculateDistance($(paragraphCoordinates[i]), x,
					y);
			closestOne = i;
		}
	}
	if ($(paragraphCoordinates[closestOne]).data('visited-counter') >= 10
			&& !$(paragraphCoordinates[closestOne]).hasClass("gazedParagraph")
			&& closestOne == expectedParagraph) {
		$(paragraphCoordinates[closestOne]).addClass("gazedParagraph");
		$(paragraphCoordinates[closestOne]).data(
				'visited-counter',
				parseInt($(paragraphCoordinates[closestOne]).data(
						'visited-counter')) + 1);
		var d = new Date();
		var n = d.getMilliseconds() + (d.getSeconds() * 1000) + + (d.getMinutes() * 60000);
		$(paragraphCoordinates[closestOne]).attr('start-time', n);
		if (expectedParagraph > 0) {
			var wordPerMinute = Math
					.round((n - parseInt($(paragraphCoordinates[closestOne])
							.data('start-time')) / 1000)
							/ parseInt($(paragraphCoordinates[closestOne])
									.find('span').length));
			$("#speadOfReading").html(wordPerMinute);
			console.log(parseInt($(paragraphCoordinates[closestOne]).find(
					'span').length));
		}
		expectedParagraph++;
		// time per word
	}
	$("#readingText p").each(
			function(j, t) {
				var thisVisitedCnt = parseInt($(t).data("visited-counter"));
				if (10 >= thisVisitedCnt && thisVisitedCnt >= 0) {
					if ($(paragraphCoordinates[closestOne]).attr("id") == $(
							this).attr("id"))
						$(this).data('visited-counter', thisVisitedCnt + 1);
					else if (thisVisitedCnt > 0)
						$(this).data('visited-counter', thisVisitedCnt - 1);
				}
			});

}

function paintTheWord() {
	if ($(".spanWordContainer#" + coloredWordCounter).position() == null
			|| $(".spanWordContainer#" + coloredWordCounter).html().length <= 1) {
		coloredWordCounter++;
		return;
	}
	if (Math.abs($(".spanWordContainer#" + coloredWordCounter).position().left
			- distX) < tenPercWidth) {
		$(".spanWordContainer#" + coloredWordCounter).css("background-color",
				colorCode);
		$(paragraphCoordinates[closestOne]).data('predection-value');
		// prediction color code
		if (numberOfWords <= coloredWordCounter)
			clearInterval(paintingWordsTimer);
		coloredWordCounter++;
	} else
		return;
}
