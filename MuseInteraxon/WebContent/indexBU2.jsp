<!DOCTYPE html>
<html class="gr__webgazer_cs_brown_edu">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CCI Demo</title>
<link rel="icon" type="image/png" href="favicon.ico">
<link rel="stylesheet"
	href="css/themes/default/jquery.mobile.icons.min.css">
<link rel="stylesheet" href="css/themes/default/theme-classic.css">
<link rel="stylesheet"
	href="css/themes/default/jquery.mobile.structure-1.4.5.min.css">
<link rel="stylesheet"
	href="css/themes/default/jquery.mobile.icons-1.4.5.min.css">
<link rel="stylesheet"
	href="css/jquery-mobile/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="css/training.webgazer.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/training.progress.bars.css">
<script src="js/training/precision_store_points.js"></script>
<script async="async" type="text/javascript"
	src="js/webGazer/webgazer.js"></script>
<script src="js/jquery.js"></script>
<script src="js/jquery.mobile-1.4.5.min.js"></script>
<script src="js/training/main.js"></script>
<script src="js/webGazer/precision.js"></script>
<script src="js/training/calibration.js"></script>
<script src="js/training/precision_calculation.js"></script>
<script src="js/training/sweetalert.min.js"></script>
<script src="js/training/resize_canvas.js"></script>
<script type="text/javascript"
	src="js/voiceRecorder/voice.training.stream.js"></script>
<script type="text/javascript" src="js/training/stream.data.receive.js"></script>
</head>
<body>
	<div data-role="page" id="mainPage" >
		<div data-role="panel" data-position="right" data-display="push"
			id="leftSidePanel" style="width: 320px;" data-position-fixed="true">
			<ul data-role="listview">
				<li id="webGazerContainerLI">
					<div class="ui-block-solo" id="webGazerContainer"></div>
				</li>
				<li onclick="Restart()"><a href="#"><span href="#"
						id="Accuracy">Not yet Calibrated</span> &nbsp;Recalibrate</a></li>
				<li class="ui-bar"><div class="hprogress"
						style="background-color: #7E0046;">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="delta"></div>
						<div class="text-center p-absolute">
							<i class="fa">&delta;</i>
						</div>
					</div>
					<div class="hprogress" style="background-color: #0083FF;">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="theta"></div>
						<div class="text-center p-absolute">
							<i class="fa">&theta;</i>
						</div>
					</div>
					<div class="hprogress" style="background-color: #13CD00;">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="alpha"></div>
						<div class="text-center p-absolute">
							<i class="fa">&alpha;</i>
						</div>
					</div>
					<div class="hprogress" style="background-color: #FFC300">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="beta"></div>
						<div class="text-center p-absolute">
							<i class="fa">&beta;</i>
						</div>
					</div>
					<div class="hprogress" style="background-color: #FF0000;">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="gamma"></div>
						<div class="text-center p-absolute">
							<i class="fa">&gamma;</i>
						</div>
					</div></li>
				<li><div class="hprogress large" style="background-color: blue">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="D"></div>
						<div class="text-center p-absolute">
							<i class="fa">D</i>
						</div>
					</div>
					<div class="hprogress large" style="background-color: green">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="F"></div>
						<div class="text-center p-absolute">
							<i class="fa">F</i>
						</div>
					</div>
					<div class="hprogress large" style="background-color: #FF0000;">
						<div class="hprogress-bar slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="MWPrediction"></div>
						<div class="text-center p-absolute">
							<i class="fa">MW</i>
						</div>
					</div></li>
				<li><div class="hprogress" style="background-color: #FFC300">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="X"></div>
						<div class="text-center p-absolute">
							<i class="fa">X</i>
						</div>
					</div>
					<div class="hprogress" style="background-color: #17FF00;">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="Y"></div>
						<div class="text-center p-absolute">
							<i class="fa">Y</i>
						</div>
					</div>
					<div class="hprogress" style="background-color: #FF0000;">
						<div class="hprogress-bar animated slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="Z"></div>
						<div class="text-center p-absolute">
							<i class="fa">Z</i>
						</div>
					</div>
					<div class="hprogress large" style="background-color: #FF0000;">
						<div class="hprogress-bar slideInDown" role="progressbar"
							aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
							id="microphone"></div>
						<div class="text-center p-absolute">
							<i class="fa">Mic</i>
						</div>
					</div>
					<table>
						<tr>
							<td><img alt="battery" src="images/batt.png"></td>
							<td><span style="color: green; font-size: 16pt;"
								id="battVal">--%</span></td>
							<td id="hs1" class="horseShoes" style="color: white;">TP9</td>
							<td id="hs2" class="horseShoes" style="color: white;">Fp1</td>
							<td id="hs3" class="horseShoes" style="color: white;"></td>
							<td id="hs4" class="horseShoes" style="color: white;">Fp2</td>
							<td id="hs5" class="horseShoes" style="color: white;">TP10</td>
							<td><div id="blinkContainer" style="visibility: hidden;">
									<img alt="blink" src="images/eye.png">
								</div></td>
						</tr>
					</table></li>
				<li><div class="ui-grid-a">
						<div class="ui-block-a">
							<a href="#" data-role="button"
								class="ui-btn ui-corner-all ui-mini ui-shadow-icon ui-corner-all"
								onclick="startTraining()" style="width: 100%;">Start
								Headband</a>
						</div>
						<div class="ui-block-b">
							<a href="#" data-role="button"
								class="ui-btn ui-corner-all ui-mini ui-shadow-icon ui-corner-all"
								onclick="stopSoundTraining()" style="width: 100%;">Stop</a>
						</div>
					</div></li>
			</ul>
			<div id="helpModal" data-role="popup" style="display: none;">
				<div class="modal-dialog">
					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-body">
							<img src="images/th.jpg" width="64" height="64"
								alt="webgazer demo instructions"> Eye Tracking Calibration
						</div>
						<div class="modal-footer">
							<button id="closeBtn" type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary"
								data-dismiss="modal" onclick="Restart()">Calibrate</button>
						</div>
					</div>

				</div>
			</div>
		</div>
		<div data-role="header">
			<a href="#leftSidePanel" data-role="button" id="leftSideMenuBTN"
				role="button"
				class="ui-btn ui-btn-right ui-icon-bars ui-icon-notext ui-shadow ui-corner-all">&nbsp;</a>
		</div>
		<div role="main" class="ui-content">
			<canvas id="plotting_canvas" width="1366" height="651"></canvas>
			<div class="ui-block-solo" id="textContainer"></div>
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
	</div>
</body>
</html>