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
<script type="text/javascript" src="js/training/stream.data.receive.js"></script>
</head>
<body>
	<div data-role="page" id="mainPage" data-theme="e">
		<div role="main" class="ui-content" id="pageContents" data-theme="c">
			<div class="ui-grid-a">
				<div class="ui-block-a" style="width: 320px;">
					<div class="ui-block-solo" id="webGazerContainer"></div>
					<div class="ui-block-solo" id="webGazerContainerLI"></div>
					<div class="ui-block-solo">
						<ul data-role="listview">
							<li onclick="Restart()"><a href="#"><span href="#"
									id="Accuracy">Not yet Calibrated</span> &nbsp;Recalibrate</a></li>
							<li class="ui-bar"><div class="hprogress"
									style="background-color: #7E0046;">
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="delta"></div>
									<div class="text-center p-absolute">
										<i class="fa">&delta;</i>
									</div>
								</div>
								<div class="hprogress" style="background-color: #0083FF;">
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="theta"></div>
									<div class="text-center p-absolute">
										<i class="fa">&theta;</i>
									</div>
								</div>
								<div class="hprogress" style="background-color: #13CD00;">
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="alpha"></div>
									<div class="text-center p-absolute">
										<i class="fa">&alpha;</i>
									</div>
								</div>
								<div class="hprogress" style="background-color: #FFC300">
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="beta"></div>
									<div class="text-center p-absolute">
										<i class="fa">&beta;</i>
									</div>
								</div>
								<div class="hprogress" style="background-color: #FF0000;">
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="gamma"></div>
									<div class="text-center p-absolute">
										<i class="fa">&gamma;</i>
									</div>
								</div></li>
							<li><div class="hprogress large"
									style="background-color: blue">
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="D"></div>
									<div class="text-center p-absolute">
										<i class="fa">D</i>
									</div>
								</div>
								<div class="hprogress large" style="background-color: green">
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="F"></div>
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
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="X"></div>
									<div class="text-center p-absolute">
										<i class="fa">X</i>
									</div>
								</div>
								<div class="hprogress" style="background-color: #17FF00;">
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="Y"></div>
									<div class="text-center p-absolute">
										<i class="fa">Y</i>
									</div>
								</div>
								<div class="hprogress" style="background-color: #FF0000;">
									<div class="hprogress-bar animated slideInDown"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" id="Z"></div>
									<div class="text-center p-absolute">
										<i class="fa">Z</i>
									</div>
								</div>
								<div class="hprogress large" style="background-color: #FFF;">
									<div class="hprogress-bar slideInDown" role="progressbar"
										aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
										id="microphone"></div>
									<div class="text-center p-absolute">
										<i class="fa">Mic</i>
									</div>
								</div></li>
							<li>
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
								</table>
							</li>
<!-- 							<li> -->
<!-- 								<div id="emotion_container"> -->
<!-- 									<div id="emotion_icons"> -->
<!-- 										<img class="emotion_icon" id="icon1" -->
<!-- 											src="images/media/icon_angry.png" style="visibility: hidden;"> -->
<!-- 										<img class="emotion_icon" id="icon2" -->
<!-- 											src="images/media/icon_sad.png" style="visibility: hidden;"> -->
<!-- 										<img class="emotion_icon" id="icon3" -->
<!-- 											src="images/media/icon_surprised.png" -->
<!-- 											style="visibility: hidden;"> <img class="emotion_icon" -->
<!-- 											id="icon4" src="images/media/icon_happy.png" -->
<!-- 											style="visibility: visible;"> -->
<!-- 									</div> -->
<!-- 									<div id="emotion_chart"> -->
<!-- 										<svg width="320" height="100"> -->
<!-- 											<rect x="20" y="66.35493501450489" -->
<!-- 												height="3.6450649854951127" width="30" fill="#2d578b"></rect> -->
<!-- 											<rect x="105" y="69.41838057022441" -->
<!-- 												height="0.5816194297755792" width="30" fill="#2d578b"></rect> -->
<!-- 											<rect x="190" y="60.40301444287701" height="9.59698555712299" -->
<!-- 												width="30" fill="#2d578b"></rect> -->
<!-- 											<rect x="275" y="20.78637720318563" -->
<!-- 												height="49.21362279681437" width="30" fill="#2d578b"></rect> -->
<!-- 											<text x="20" y="66.35493501450489" dx="-15" dy="1.2em" -->
<!-- 												text-anchor="middle" fill="white" class="labels">0.1</text> -->
<!-- 											<text x="135" y="69.41838057022441" dx="-15" dy="1.2em" -->
<!-- 												text-anchor="middle" fill="white" class="labels">0.0</text> -->
<!-- 											<text x="220" y="60.40301444287701" dx="-15" dy="1.2em" -->
<!-- 												text-anchor="middle" fill="white" class="labels">0.1</text> -->
<!-- 											<text x="305" y="20.78637720318563" dx="-15" dy="1.2em" -->
<!-- 												text-anchor="middle" fill="white" class="labels">0.7</text> -->
<!-- 											<text x="20" y="70" dx="-15" text-anchor="middle" -->
<!-- 												style="font-size: 12" transform="translate(0, 18)" -->
<!-- 												class="yAxis">angry</text> -->
<!-- 											<text x="135" y="70" dx="-15" text-anchor="middle" -->
<!-- 												style="font-size: 12" transform="translate(0, 18)" -->
<!-- 												class="yAxis">sad</text> -->
<!-- 											<text x="220" y="70" dx="-15" text-anchor="middle" -->
<!-- 												style="font-size: 12" transform="translate(0, 18)" -->
<!-- 												class="yAxis">surprised</text> -->
<!-- 											<text x="305" y="70" dx="-15" text-anchor="middle" -->
<!-- 												style="font-size: 12" transform="translate(0, 18)" -->
<!-- 												class="yAxis">happy</text></svg> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</li> -->
							<li>
								<div class="ui-grid-b">
									<div class="ui-block-a">
										<span id="noOfWords"></span>&nbsp;Words
									</div>
									<div class="ui-block-b">
										Word/Min: <span id="speadOfReading"></span>
									</div>
									<div class="ui-block-c">
										<span id="noOfLines"></span>&nbsp;Lines
									</div>
								</div>
							</li>
							<li><div class="ui-grid-a">
									<div class="ui-block-a">
										<a href="#" data-role="button"
											class="ui-btn ui-corner-all ui-mini ui-shadow-icon ui-corner-all"
											onclick="startTraining()">Start Headband</a>
									</div>
									<div class="ui-block-b">
										<a href="#" data-role="button"
											class="ui-btn ui-corner-all ui-mini ui-shadow-icon ui-corner-all"
											onclick="stopSoundTraining()">Stop</a>
									</div>
								</div></li>
						</ul>
					</div>

					<div id="helpModal" data-role="popup" style="display: none;">
						<div class="modal-dialog">
							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-body">
									<img src="images/th.jpg" width="64" height="64"
										alt="webgazer demo instructions"> Eye Tracking
									Calibration
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
				<div class="ui-block-b">
					<div class="ui-block-solo" id="textContainer"></div>
					<div class="calibrationDiv">
						<input type="button" class="Calibration" id="Pt1"
							style="display: none;" data-role="none"> <input
							type="button" class="Calibration" id="Pt2" style="display: none;"
							data-role="none"> <input type="button"
							class="Calibration" id="Pt3" style="display: none;"
							data-role="none"> <input type="button"
							class="Calibration" id="Pt4" style="display: none;"
							data-role="none"> <input type="button"
							class="Calibration" id="Pt5" style="display: none;"
							data-role="none"> <input type="button"
							class="Calibration" id="Pt6" style="display: none;"
							data-role="none"> <input type="button"
							class="Calibration" id="Pt7" style="display: none;"
							data-role="none"> <input type="button"
							class="Calibration" id="Pt8" style="display: none;"
							data-role="none"> <input type="button"
							class="Calibration" id="Pt9" style="display: none;"
							data-role="none">
					</div>
					<canvas id="plotting_canvas" width="100%" height="100%"
						style="position: fixed; left: 0; right: 0; top: 0; bottom: 0;"></canvas>
				</div>
			</div>
		</div>
	</div>
</body>
</html>