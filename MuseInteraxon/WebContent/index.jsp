<!DOCTYPE html>
<html class="gr__webgazer_cs_brown_edu">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CCI Demo</title>
<link rel="icon" type="image/png" href="images/th.jpg" >
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
<link rel="stylesheet" href="css/themes/default/theme-classic.css">
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
<script type="text/javascript"
	src="js/voiceRecorder/voice.training.stream.js"></script>
<script type="text/javascript" src="js/training/labeling.reading.js"></script>
<script type="text/javascript" src="js/training/stream.data.receive.js"></script>
</head>
<body>
	<div data-role="page" id="mainPage" data-theme="a">
		<div data-role="header" data-fullscreen="true"
			data-position-fixed="true" data-position="fixed">
			<a href="#leftSidePanel" data-role="button" id="leftSideMenuBTN"
				role="button"
				class="ui-btn ui-btn-left ui-icon-bars ui-btn-icon-notext ui-shadow ui-corner-all">&nbsp;</a>
			<h2>
				<img alt="" src="images/th.jpg" style="width: 3%;"> CCI <span
					id="timerSpan"></span>
			</h2>
			<div data-role="navbar" data-grid="d">
				<ul>
					<li><a href="#" onclick="Restart()">Calibrate</a></li>
					<li><a href="#" onclick="startTraining()">Connect</a></li>
					<li><a href="#" onclick="readingStart()">Start</a></li>
					<li><a href="#" onclick="readingPause()">Pause</a></li>
					<li><a href="#" onclick="finishRecording()">End</a></li>
					<!-- 					<li><a href="#" onclick="eyeTrackerPS()"></a></li> -->
				</ul>
			</div>
			<a href="#leftSidePanel" data-role="button" id="leftSideMenuBTN"
				role="button"
				class="ui-btn ui-btn-left ui-icon-bars ui-btn-icon-notext ui-shadow ui-corner-all">&nbsp;</a>
			<a href="#" data-role="button" role="button"
				class="ui-btn ui-btn-right ui-icon-left ui-shadow ui-corner-all ui-btn-icon-left ui-icon-eye"
				onclick="eyeTrackerPS()" data-icon="eye">Eye Tracker Pause/Start</a>
		</div>
		<div data-role="panel" data-position="left" data-display="overlay"
			id="leftSidePanel" class="ui-bar"></div>
		<div role="main" class="ui-content" id="pageContents" data-theme="c">
			<canvas id="plotting_canvas" width="100%" height="100%"
				style="position: fixed; left: 0; right: 0; top: 0; bottom: 0;"></canvas>
			<div class="ui-block-solo" id="textContainer"></div>
			<div id="evaluationDiv"></div>
		</div>
		<div data-role="footer" id="footerBar" data-position="fixed"
			class="ui-bar">
			<div class="ui-grid-c">
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
					<div class="ui-block-solo">
						<span id="noiseEnvPrediction"></span>
					</div>
					<div class="ui-block-solo">
						Distracted By&nbsp;<span id="speadOfReading">Cell Phone</span>
					</div>
					<div class="ui-block-solo">
						<span id="noOfLines">Speaking with Someone/Reading</span>
					</div>
				</div>
				<div class="ui-block-c">
					<div class="ui-block-solo">
						<span id="noiseEnvPrediction">Happy/Sleepy/Tired</span>
					</div>
					<div class="ui-block-solo">
						<span id="speadOfReading">Male/Female</span>
					</div>
					<div class="ui-block-solo">
						<span id="noOfLines"></span>
					</div>
				</div>
				<div class="ui-block-d">
					<div class="hprogress large" style="background-color: #FF0000;">
						<div class="hprogress-bar slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="MWPrediction"></div>
						<div class="text-center p-absolute">
							<i class="fa">RNN</i>
						</div>
					</div>
					<div class="hprogress large" style="background-color: #FF0000;">
						<div class="hprogress-bar slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="MWPrediction"></div>
						<div class="text-center p-absolute">
							<i class="fa">ANN</i>
						</div>
					</div>
					<div class="hprogress large" style="background-color: #FF0000;">
						<div class="hprogress-bar slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="MWPrediction"></div>
						<div class="text-center p-absolute">
							<i class="fa">SVM</i>
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
		<form id="dataClassificationLabels">
			<!-- 		MIND WANDERING -->
			<input type="hidden" name="MW" id="MW">
			<!-- 		MIND WANDERING -->
			<input type="hidden" name="G" id="gender">
			<!-- 		DISTRACTION REASON -->
			<input type="hidden" name="DR" id="dr">
			<!-- 		MOOD -->
			<input type="hidden" name="M" id="mood">
			<!-- 		GAZE X -->
			<input type="hidden" name="Xg" id="Xg">
			<!-- 		GAZE Y -->
			<input type="hidden" name="Xy" id="Xy">

		</form>
	</div>
</body>
</html>