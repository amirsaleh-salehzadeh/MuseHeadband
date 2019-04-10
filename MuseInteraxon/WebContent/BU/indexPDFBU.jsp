<!DOCTYPE html>
<html class="gr__webgazer_cs_brown_edu">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CCI Demo</title>
<link rel="icon" type="image/png" href="images/th.jpg">
<link rel="stylesheet"
	href="css/themes/default/jquery.mobile.icons.min.css">
<link rel="stylesheet"
	href="css/themes/default/jquery.mobile.structure-1.4.5.min.css">
<link rel="stylesheet"
	href="css/themes/default/jquery.mobile.icons-1.4.5.min.css">
<link rel="stylesheet" href="css/jquery-mobile/jquery.mobile-1.4.5.css">
<link rel="stylesheet" href="css/training.webgazer.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/training.progress.bars.css">
<script src="js/training/precision_store_points.js"></script>
<script async="async" type="text/javascript"
	src="js/webGazer/webgazer.js"></script>
<script src="js/jquery.js"></script>
<script src="js/jquery.mobile-1.4.5.min.js"></script>
<script src="js/training/highlight.words.js"></script>
<script src="js/training/text.loading.js"></script>
<script src="js/training/main.js"></script>
<script src="js/webGazer/precision.js"></script>
<script src="js/training/calibration.js"></script>
<script src="js/training/precision_calculation.js"></script>
<script src="js/training/sweetalert.min.js"></script>
<script src="js/training/resize_canvas.js"></script>
<script src="js/training/popup.mind.wandering.js"></script>
<script src="js/training/pdfobject.min.js"></script>
<script type="text/javascript"
	src="js/voiceRecorder/voice.training.stream.js"></script>
<script type="text/javascript" src="js/training/labeling.reading.js"></script>
<script type="text/javascript" src="js/training/stream.data.receive.js"></script>
</head>
<body>
	<div data-role="page" id="mainPage" data-theme="b">
		<div data-role="header" data-fullscreen="true">
			<a href="#leftSidePanel" data-role="button" id="leftSideMenuBTN"
				role="button"
				class="ui-btn ui-btn-left ui-icon-bars ui-btn-icon-notext ui-shadow ui-corner-all">&nbsp;</a>
			<h2>
				<img alt="" src="images/th.jpg" style="width: 3%;"> CCI
			</h2>
		</div>
		<div data-role="panel" data-position="left" data-display="push"
			id="leftSidePanel"></div>
		<div role="main" class="ui-content" id="pageContents" data-theme="c">
			<canvas id="plotting_canvas" width="100%" height="100%"
				style="position: fixed; left: 0; right: 0; top: 0; bottom: 0;"></canvas>
			<div class="ui-block-solo" id="textContainer"></div>
			<div id="evaluationDiv" class="ui-block-solo">
				<a href="#" onclick="openMWpopup()" data-role="button"
					id="leftSideMenuBTN" role="button"
					class="ui-btn ui-btn-right ui-icon-bars ui-btn-icon-notext ui-shadow ui-corner-all">&nbsp;</a>
			</div>
			<div class="calibrationDiv">
				<input type="button" class="Calibration" id="Pt1"
					style="display: none;" data-role="none"> <input
					type="button" class="Calibration" id="Pt2" style="display: none;"
					data-role="none"> <input type="button" class="Calibration"
					id="Pt3" style="display: none;" data-role="none"> <input
					type="button" class="Calibration" id="Pt4" style="display: none;"
					data-role="none"> <input type="button" class="Calibration"
					id="Pt5" style="display: none;" data-role="none"> <input
					type="button" class="Calibration" id="Pt6" style="display: none;"
					data-role="none"> <input type="button" class="Calibration"
					id="Pt7" style="display: none;" data-role="none"> <input
					type="button" class="Calibration" id="Pt8" style="display: none;"
					data-role="none"> <input type="button" class="Calibration"
					id="Pt9" style="display: none;" data-role="none">
			</div>
		</div>
		<div data-role="footer" id="footerBar" data-position="fixed">
			<div class="ui-grid-a">
				<div class="ui-block-a">
					<div class="ui-block-solo">
						<span id="noOfWords"></span>&nbsp;Words
					</div>
					<div class="ui-block-solo">
						<span id="speadOfReading"></span>&nbsp;words/min
					</div>
					<div class="ui-block-solo">
						<span id="noOfLines"></span>&nbsp;Lines
					</div>
				</div>
				<div class="ui-block-b">
					<div class="hprogress large" style="background-color: #FF0000;">
						<div class="hprogress-bar slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="MWPrediction"></div>
						<div class="text-center p-absolute">
							<i class="fa">MW</i>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="helpModal" data-role="popup">
			<div class="ui-block-solo">
				<img src="images/th.jpg" width="64" height="64"
					alt="webgazer demo instructions"> Eye Tracking Calibration
			</div>
			<div class="modal-footer">
				<button id="closeBtn" type="button" class="btn btn-default"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal"
					onclick="Restart()">Calibrate</button>
			</div>
		</div>
		<div id="distractionPopup" data-role="popup">
			<a href="#" data-rel="back"
				class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-icon-delete ui-btn-icon-notext ui-btn-right">Close</a>
			<div class="ui-block-solo">
				<img src="images/th.jpg" width="64" height="64"
					alt="webgazer demo instructions">
			</div>
			<fieldset data-role="controlgroup" data-type="horizontal"
				data-mini="true">
				<legend>Has Your Mind Wandered?</legend>
				        <input type="radio" name="radio-choice-h-2"
					id="option_mw_sd" value="on" checked="checked">         <label
					for="option_mw_sd">Intentionally</label>         <input
					type="radio" name="radio-choice-h-2" id="option_mw_pd" value="off">
				        <label for="option_mw_pd">Someone Distracted Me</label> <input
					type="radio" name="radio-choice-h-2" id="option_mw_cp"
					value="other">         <label for="option_mw_cp">Cell
					Phone</label> <input type="radio" name="radio-choice-h-2" id="option_mw_dd"
					value="other">         <label for="option_mw_dd">Day
					Dreaming</label> <input type="radio" name="radio-choice-h-2"
					id="option_mw_no" value="other" data-rel="back">         <label
					for="option_mw_no">No</label>
			</fieldset>
		</div>
	</div>
</body>
</html>