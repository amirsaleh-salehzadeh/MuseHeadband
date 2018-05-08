<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <script type="text/javascript" -->
<!-- 	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script> -->
<!-- <script type="text/javascript" -->
<!-- 	src="https://cdnjs.cloudflare.com/ajax/libs/flot/0.8.3/jquery.flot.min.js"></script> -->
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/flot.js"></script>
<script type="text/javascript" src="js/cciGraphs.js"></script>
<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="css/theme-classic.css">
<link rel="stylesheet" href="css/progress.bars.css">
<link rel="stylesheet" href="css/text.style.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript" src="js/voiceRecorder/volume-meter.js"></script>
<script type="text/javascript" src="js/voiceRecorder/main.js"></script>
</head>
<body>
	<div data-role="page" style="background-color: white;">
		<!-- 			<div id="gazerDiv" -->
		<!-- 				style="background-color: red; width: 10px; height: 10px; position: absolute; z-index: 100;"></div> -->
		<div class="ui-block-solo ui-bar" data-role="header"
			style="height: 111px;" id="headerDiv">
			<table style="width: 100%; height: 111px;">
				<tr>
					<td>
						<div id="videocontainer">
							<video autoplay="true" id="videoElement">
							</video>
						</div>
					</td>
					<td>
						<div class="hprogress" style="background-color: #7E0046;">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
								id="delta"></div>
							<div class="text-center p-absolute">
								<i class="fa">&delta;</i>
							</div>
						</div>
						<div class="hprogress" style="background-color: #0083FF;">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
								id="theta"></div>
							<div class="text-center p-absolute">
								<i class="fa">&theta;</i>
							</div>
						</div>
						<div class="hprogress" style="background-color: #FFC300">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
								id="alpha"></div>
							<div class="text-center p-absolute">
								<i class="fa">&alpha;</i>
							</div>
						</div>
						<div class="hprogress" style="background-color: #17FF00;">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
								id="beta"></div>
							<div class="text-center p-absolute">
								<i class="fa">&beta;</i>
							</div>
						</div>
						<div class="hprogress" style="background-color: #FF0000;">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
								id="gamma"></div>
							<div class="text-center p-absolute">
								<i class="fa">&gamma;</i>
							</div>
						</div>
					</td>
					<td>
						<div class="hprogress large">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="D"></div>
							<div class="text-center p-absolute">
								<i class="fa">D</i>
							</div>
						</div>
						<div class="hprogress large">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="F"></div>
							<div class="text-center p-absolute">
								<i class="fa">F</i>
							</div>
						</div>
					</td>
					<td>
						<div class="hprogress" style="background-color: #FFC300">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="X"></div>
							<div class="text-center p-absolute">
								<i class="fa">X</i>
							</div>
						</div>
						<div class="hprogress" style="background-color: #17FF00;">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="Y"></div>
							<div class="text-center p-absolute">
								<i class="fa">Y</i>
							</div>
						</div>
						<div class="hprogress" style="background-color: #FF0000;">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" id="Z"></div>
							<div class="text-center p-absolute">
								<i class="fa">Z</i>
							</div>
						</div>
					</td>
					<td>
						<div class="hprogress large" style="background-color: #FF0000;">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
								id="microphone"></div>
							<div class="text-center p-absolute">
								<i class="fa">Mic</i>
							</div>
						</div>
					</td>
					<td>
						<div class="hprogress large" style="background-color: #FF0000;">
							<div class="hprogress-bar slideInDown" role="progressbar"
								aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"
								id="MWPrediction"></div>
							<div class="text-center p-absolute">
								<i class="fa">MW</i>
							</div>
						</div>
					</td>
					<td>
						<div id="blinkContainer" style="visibility: hidden;">
							<img alt="blink" src="images/eye.png">
						</div>
					</td>
					<td>
						<div style="display: inline-block; float: right;">
							<table>
								<tr>
									<td><img alt="battery" src="images/batt.png"></td>
									<td><span style="color: green; font-size: 16pt;"
										id="battVal">--%</span></td>
								</tr>
								<tr>
									<td colspan="2">
										<table>
											<tr>
												<td id="hs1" class="horseShoes" style="color: white;">TP9</td>
												<td id="hs2" class="horseShoes" style="color: white;">Fp1</td>
												<td id="hs3" class="horseShoes" style="color: white;"></td>
												<td id="hs4" class="horseShoes" style="color: white;">Fp2</td>
												<td id="hs5" class="horseShoes" style="color: white;">TP10</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</td>
					<td>
						<div class="ui-block-solo">
							<div class="ui-block-solo">
								<a href="#" data-role="button"
									class="ui-btn ui-corner-all ui-mini ui-shadow-icon ui-corner-all"
									onclick="startEEG()" style="width: 100%;">Record</a>
							</div>
							<div class="ui-block-solo">
								<a href="#" data-role="button"
									class="ui-btn ui-corner-all ui-mini ui-shadow-icon ui-corner-all"
									onclick="stopEEG()" style="width: 100%;">Stop</a>
							</div>
						</div>
					</td>
				</tr>
			</table>


		</div>
		<div role="main" class="ui-content" style="height: 100%;">
			<div class="ui-block-solo"
				style="height: 100%; background-color: white;">hi</div>
		</div>
	</div>
	<div data-role="popup" id="popupSitIn" data-dismissible="false">
		<form onsubmit="return sitInTheGame()" action="#">
			<div data-role="header" data-theme="a">
				<h1>MW Detected</h1>
			</div>
			<div role="main" class="ui-content" data-overlay-theme="a"
				data-theme="a">
				<div class="ui-grid-a ui-block-solo">
					<div class="ui-block-a">
						<a href="#"
							class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-a"
							data-rel="back" data-mini="true">Wrong</a>
					</div>
					<div class="ui-block-b">
						<a href="#"
							class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-a"
							data-rel="back" data-mini="true">Accurate</a>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="js/videoRecorder/video.js"></script>
<!-- <script type="text/javascript" src="js/videoRecorder/clmtrackr.js"></script> -->
<!-- <script type="text/javascript" src="js/videoRecorder/webgazer.js"></script> -->
<script type="text/javascript">
	// 	webgazer.setTracker("clmtrackr"); //set a tracker module
	// 	webgazer.setRegression("ridge"); //set a regression module
	// 	webgazer.setGazeListener(function(data, elapsedTime) {
	// 		if (data == null) {
	// 			return;
	// 		}
	// 		var xprediction = data.x;
	// 		var yprediction = data.y;
	// 		$("#gazerDiv").css("left", xprediction);
	// 		$("#gazerDiv").css("top", yprediction);
	// 	}).begin();
	// 	var videoInput = document.getElementById('videoElement');
	// 	var ctracker = new clm.tracker();
	// 	ctracker.init();
	// 	ctracker.start(videoInput);
</script>
</html>