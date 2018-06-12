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

//function loadText() {
//	textnumber++;
//	$("#textContainer").load(
//			'text' + textnumber + '.html',
//			function() {
//				$("#questionnaireFormDIV").css("display", "none").trigger(
//						'create');
//				// ADDING THE WORDS TO SPANS
//				var words = $("#readingText").html().split(" ");
//				var newWords = "<p id='0' data-visited-counter='0'>";
//				var numberOfWordsInParagraph = 0;
//				for ( var p = 0; p < words.length; p++) {
//					if (words[p].length <= 1)
//						continue;
//					if (words[p].indexOf("<br") == -1) {
//						newWords += "<span class='spanWordContainer' id='" + p
//								+ "' >" + words[p] + "&nbsp;</span>";
//						numberOfWords++;
//					} else if (p > 0) {
//						numberOfParagraphs++;
//						newWords += "</p><p id='" + numberOfParagraphs
//								+ "' data-visited-counter='0'>";
//					}
//				}
//				newWords += "</p>";
//				$("#readingText").html(newWords).trigger("create");
//				$("#noOfWords").html(numberOfWords);
//				paintingWordsTimer = setInterval(paintTheWord, 30);
//				$("#textContainer").width($(window).width() - 366);
//				$("#textContainer").height($(window).height())
//						.trigger('create');
//				tenPercWidth = 0.1 * $("#readingText").width();
//				// COUNTING THE LINE NUMBERS
//				$("#readingText p").each(function(j, t) {
//					if ($(t).html().length <= 0) {
//						$(t).remove();
//						return false;
//					}
//					paragraphCoordinates.push(t);
//					sot(t);
//				});
//				$("#noOfLines").html(noOfRows);
//			}).trigger('create');
//}
//
//function sot(t) {
//	var rowDivs = "";
//	$(t)
//			.children()
//			.each(
//					function(k, l) {
//
//						if (lastTop == -1 || lastTop != $(l).position().top) {
//							lastTop = $(l).position().top;
//							noOfRows++;
//							if (rowDivs.length <= 0)
//								rowDivs += "<div class='ui-block-solo rowDivs' id='"
//										+ noOfRows + "'>";
//							else
//								rowDivs += "</div><div class='ui-block-solo rowDivs' id='"
//										+ noOfRows + "'>";
//						}
//						rowDivs += "<span class='spanWordContainer' id='" + k
//								+ "' >" + $(l).html() + "</span>";
//
//					});
//	rowDivs += "</div>";
//	$(t).html(rowDivs).trigger("create");
//}


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