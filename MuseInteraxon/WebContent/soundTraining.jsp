<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="css/theme-classic.css">
<link rel="stylesheet" href="css/progress.bars.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script type="text/javascript" src="js/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript" src="js/flot.js"></script>
<script type="text/javascript"
	src="js/voiceRecorder/voice.training.stream.js"></script>
<script type="text/javascript" src="js/stream.data.receive.js"></script>
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
						<div class="hprogress" style="background-color: #7E0046;">
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
						</div>
					</td>
					<td>
						<div class="hprogress large" style="background-color: blue">
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
					</td>
					<td>
						<div class="hprogress" style="background-color: #FFC300">
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
					<td width="133px;"><input value="" id="volume" type="hidden">
						<input type="hidden" size="4" id="pod0min" data-mini="true"
						placeholder="500" value='500' onkeyup="refreshPods()"> <input
						type="hidden" size="4" placeholder="1100" id="pod0max"
						data-mini="true" value='1100' onkeyup="refreshPods()"> <input
						type="hidden" size="6" id="pod1min" placeholder="700"
						data-mini="true" value='700' onkeyup="refreshPods()"> <input
						type="hidden" size="6" id="pod1max" data-mini="true"
						placeholder="900" value='900' onkeyup="refreshPods()"> <input
						type="hidden" size="4" id="pod2min" data-mini="true"
						placeholder="700" value='700' onkeyup="refreshPods()"> <input
						type="hidden" size="4" placeholder="900" id="pod2max"
						data-mini="true" value='900' onkeyup="refreshPods()"> <input
						type="hidden" size="6" id="pod3min" placeholder="700"
						data-mini="true" value='700' onkeyup="refreshPods()"> <input
						type="hidden" size="6" id="pod3max" data-mini="true"
						placeholder="900" value='1100' onkeyup="refreshPods()">
						<div id='pod0' class='eegGraph ui-block-solo'
							style="color: #8aceb5; height: 55px;"></div>
						<div id='pod1' class='eegGraph ui-block-solo'
							style="color: red; height: 55px;"></div></td>
					<td width="133px;">
						<div id='pod2' class='eegGraph ui-block-solo'
							style="color: #8aceb5; height: 55px;"></div>
						<div id='pod3' class='eegGraph ui-block-solo'
							style="color: red; height: 55px;"></div>
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
									onclick="startTraining()" style="width: 100%;">Record</a>
							</div>
							<div class="ui-block-solo">
								<a href="#" data-role="button"
									class="ui-btn ui-corner-all ui-mini ui-shadow-icon ui-corner-all"
									onclick="stopSoundTraining()" style="width: 100%;">Stop</a>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>