<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <script type="text/javascript" -->
<!-- 	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script> -->
<!-- <script type="text/javascript" -->
<!-- 	src="https://cdnjs.cloudflare.com/ajax/libs/flot/0.8.3/jquery.flot.min.js"></script> -->
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/flot.js"></script>
<script type="text/javascript" src="js/eegGraphs.js"></script>
<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="css/jqm-demos.css">
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<script src="js/index.js"></script>
<script src="js/jquery.mobile-1.4.5.min.js"></script>
<style>
</style>
</head>
<body>
	<div style="display: inline;">
		<a href="#" class="button red"
			style="display: inline-block; float: left;" onclick="stopEEG()"><img
			alt="stop" src="images/stop.png" /></a> <img alt="NMMU"
			style="display: inline-block; float: left;" src="images/logo.png">
		<table style="display: inline-block; height: 90px;">
			<tr>
				<td><span
					style="color: white; font-size: x-large; font-weight: bolder; font-style: italic; vertical-align: inherit;">Computing
						Sciences Department </span></td>
			</tr>
			<tr>
				<td><div id="blinkContainer" style="display: none;">
						<img alt="blink" src="images/eye.png">
					</div></td>
			</tr>
		</table>
		<a href="#" class="button green"
			style="display: inline-block; float: right;" onclick="startEEG()"><img
			alt="start" src="images/play.png" /></a>
		<div style="display: inline-block; float: right;">
			<table>
				<tr>
					<td><img alt="battery" src="images/batt.png"></td>
					<td><span style="color: green; font-size: 16pt;" id="battVal">--%</span></td>
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
	</div>
	<div id="delta" class="description" onclick="showDiv('delta')"
		onmouseout="hideDiv('delta')">
		<ul>
			<li>&delta; Power Spectrum. Frequency Range Between [1-4 Hz]
				<ul>
					<li>Deep Dreamless Sleep</li>
					<li>Loss of Body Awareness</li>
					<li>Immune System</li>
					<li>Natural Restorative</li>
				</ul>
			</li>
		</ul>
	</div>
	<div id="theta" class="description" onclick="showDiv('theta')"
		onmouseout="hideDiv('theta')">
		<ul>
			<li>&theta; Power Spectrum. Frequency Range between [4-8 Hz]
				<ul>
					<li>Deep Meditation</li>
					<li>Daydreaming</li>
					<li>Sleep</li>
					<li>Feeling Deep</li>
					<li>Raw Emotions</li>
					<li>Creative Inspiration</li>
				</ul>
			</li>
		</ul>
	</div>
	<div id="alpha" class="description" onclick="showDiv('alpha')"
		onmouseout="hideDiv('alpha')">
		<ul>
			<li>&alpha; Power Spectrum. Frequency Range Between [7.5-13 Hz]
				<ul>
					<li>Calm</li>
					<li>Deep Relaxation</li>
					<li>Disengagement</li>
				</ul>
			</li>
		</ul>
	</div>
	<div id="beta" class="description" onclick="showDiv('beta')"
		onmouseout="hideDiv('beta')">
		<ul>
			<li>&beta; Power Spectrum. Frequency Range Between [13-30 Hz]
				<ul>
					<li>Active</li>
					<li>Busy Thinking</li>
					<li>Active Processing</li>
					<li>Focused Concentration</li>
					<li>Awakening</li>
					<li>Memorising</li>
				</ul>
			</li>
		</ul>
	</div>
	<div id="gamma" class="description" onclick="showDiv('gamma')"
		onmouseout="hideDiv('gamma')">
		<ul>
			<li>&gamma; Power Spectrum. Frequency Range Between [30-44 Hz]
				<ul>
					<li>Higher Mental Activity</li>
					<li>Problem Solving</li>
					<li>Attention</li>
				</ul>
			</li>
		</ul>
	</div>
	<div class='eegGraphs'>
		<div data-role="collapsible" data-mini="true"
			class="selectorCollapsible">
			<h4>
				Raw EEG Signal <span style="font-style: italic;">(&micro;V)</span>
			</h4>
			<div style="height: 222px;">
				<div
					style="color: #8aceb5; height: 222px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					EEG1 <input type="text" size="4" id="pod0min" data-mini="true"
						placeholder="500" value='500' onkeyup="refreshPods()"> <input
						type="text" size="4" placeholder="1100" id="pod0max"
						data-mini="true" value='1100' onkeyup="refreshPods()">
				</div>
				<div id='pod0' class='eegGraph'
					style="color: #8aceb5; height: 222px; display: inline-block; float: left;">
				</div>
				<div
					style="color: red; height: 222px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					EEG2 <input type="text" size="6" id="pod1min" placeholder="700"
						data-mini="true" value='700' onkeyup="refreshPods()"> <input
						type="text" size="6" id="pod1max" data-mini="true"
						placeholder="900" value='900' onkeyup="refreshPods()">
				</div>
				<div id='pod1' class='eegGraph'
					style="color: red; height: 222px; display: inline-block; float: right;">
				</div>
			</div>
			<div style="height: 222px;">
				<div
					style="color: white; max-height: 222px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					EEG3 <input type="text" size="4" id="pod2min" placeholder="750"
						data-mini="true" value='750' onkeyup="refreshPods()"> <input
						type="text" size="4" id="pod2max" data-mini="true"
						placeholder="950" value='950' onkeyup="refreshPods()">
				</div>
				<div id='pod2' class='eegGraph'
					style="color: white; height: 222px; display: inline-block; float: left;">
					<input type="hidden" id="pod2min"> <input type="hidden"
						id="pod2max">
				</div>
				<div
					style="color: #FE9901; max-height: 222px; width: 66px; ma display: inline-block; float: left; font-size: 20px;">
					EEG4 <input type="text" size="4" id="pod3min" data-mini="true"
						value='0' onkeyup="refreshPods()"> <input type="text"
						size="4" id="pod3max" data-mini="true" value='2000'
						onkeyup="refreshPods()">
				</div>
				<div id='pod3' class='eegGraph'
					style="color: #FE9901; height: 222px; display: inline-block; float: right; font-size: 20px;">
				</div>
			</div>
		</div>
		<div data-role="collapsible" data-mini="true"
			class="selectorCollapsible">
			<h4>
				Concetration and Dreaming Measurement <span style="font-style: italic;">(%)</span>
			</h4>
			<div style="height: 222px;">
				<div
					style="color: #8aceb5; height: 222px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					Concentration <input type="text" size="4" id="pod18min" data-mini="true"
						placeholder="0" value='0' onkeyup="refreshPods()"> <input
						type="text" size="4" placeholder="100" id="pod18max"
						data-mini="true" value='100' onkeyup="refreshPods()">
				</div>
				<div id='pod18' class='eegGraph'
					style="color: #8aceb5; height: 222px; display: inline-block; float: left;">
				</div>
				<div
					style="color: red; height: 222px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					Dreaming <input type="text" size="6" id="pod19min" placeholder="0"
						data-mini="true" value='0' onkeyup="refreshPods()"> <input
						type="text" size="6" id="pod19max" data-mini="true"
						placeholder="100" value='100' onkeyup="refreshPods()">
				</div>
				<div id='pod19' class='eegGraph'
					style="color: red; height: 222px; display: inline-block; float: right;">
				</div>
			</div>
		</div>
		<div data-role="collapsible" data-mini="true"
			class="selectorCollapsible">
			<h4>Absolute Value of Filtered Signals</h4>
			<div style="height: 77px;" onclick="showDiv('delta')"
				onmouseout="hideDiv('delta')">
				<div
					style="display: inline-block; font-size: 22px; color: #FE08A4; float: left; height: 77px;">&delta;</div>
				<div
					style="color: #FE08A4; height: 73px; width: 66px; display: inline-block; float: left;">
					<input type="text" size="4" value="0" id="pod9min" data-mini="true"
						onkeyup="refreshPods()"> <input type="text" size="4"
						id="pod9max" data-mini="true" value="1" onkeyup="refreshPods()">
				</div>
				<div id='pod9' class='eegGraph'
					style="color: #FE08A4; display: inline-block; height: 66px; float: left;">
				</div>
				<div
					style="color: #6CED09; height: 77px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					<input type="text" size="4" value="0" id="pod14min"
						data-mini="true" onkeyup="refreshPods()"> <input
						type="text" value="1" size="4" id="pod14max" data-mini="true"
						onkeyup="refreshPods()">
				</div>
				<div id='pod14' class='eegGraph'
					style="color: #6CED09; display: inline-block; float: right; height: 66px;">
				</div>
			</div>
			<div style="height: 77px;" onclick="showDiv('theta')"
				onmouseout="hideDiv('theta')">
				<div
					style="display: inline-block; font-size: 22px; color: #FE6208;; float: left; height: 77px;">&theta;</div>
				<div
					style="color: #FE6208; height: 77px; width: 66px; display: inline-block; float: left;">
					<input type="text" size="4" value="0" id="pod7min" data-mini="true"
						onkeyup="refreshPods()"> <input type="text" value="1"
						size="4" id="pod7max" data-mini="true" onkeyup="refreshPods()">
				</div>
				<div id='pod7' class='eegGraph'
					style="color: #FE6208; display: inline-block; height: 66px; float: left;">
				</div>
				<div
					style="color: #8aceb5; height: 77px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					<input type="text" size="4" id="pod12min" data-mini="true"
						value="0" onkeyup="refreshPods()"> <input type="text"
						size="4" value="1" id="pod12max" data-mini="true"
						onkeyup="refreshPods()">
				</div>
				<div id='pod12' class='eegGraph'
					style="color: #8aceb5; display: inline-block; float: right; height: 66px;">
				</div>
			</div>
			<div style="height: 77px;" onclick="showDiv('alpha')"
				onmouseout="hideDiv('alpha')">
				<div
					style="display: inline-block; font-size: 22px; color: #CACAC9; float: left; height: 77px;">&alpha;</div>
				<div
					style="color: #CACAC9; height: 77px; width: 66px; display: inline-block; float: left;">
					<input type="text" size="4" id="pod5min" value="0" data-mini="true"
						onkeyup="refreshPods()"> <input type="text" value="1"
						size="4" id="pod5max" data-mini="true" onkeyup="refreshPods()">
				</div>
				<div id='pod5' class='eegGraph'
					style="color: #CACAC9; display: inline-block; height: 66px; float: left;">
				</div>
				<div
					style="color: #DEED09; height: 77px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					<input type="text" size="4" id="pod10min" value="0"
						data-mini="true" onkeyup="refreshPods()"> <input value="1"
						type="text" size="4" id="pod10max" data-mini="true"
						onkeyup="refreshPods()">
				</div>
				<div id='pod10' class='eegGraph'
					style="color: #DEED09; display: inline-block; height: 66px; float: right;">
				</div>
			</div>
			<div style="height: 77px;" onclick="showDiv('beta')"
				onmouseout="hideDiv('beta')">
				<div
					style="display: inline-block; font-size: 22px; color: #0893FE; float: left; height: 77px;">&beta;</div>
				<div
					style="color: #0893FE; height: 77px; width: 66px; display: inline-block; float: left;">
					<input type="text" size="4" id="pod6min" value="0" data-mini="true"
						onkeyup="refreshPods()"> <input type="text" value="1"
						size="4" id="pod6max" data-mini="true" onkeyup="refreshPods()">
				</div>
				<div id='pod6' class='eegGraph'
					style="color: #0893FE; display: inline-block; height: 66px; float: left;">
				</div>
				<div
					style="color: #DD08FE; height: 77px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					<input type="text" size="4" id="pod11min" value="0"
						data-mini="true" onkeyup="refreshPods()"> <input
						type="text" value="1" size="4" id="pod11max" data-mini="true"
						onkeyup="refreshPods()">
				</div>
				<div id='pod11' class='eegGraph'
					style="color: #DD08FE; display: inline-block; height: 66px; float: right;">
				</div>
			</div>
			<div style="height: 77px;" onclick="showDiv('gamma')"
				onmouseout="hideDiv('gamma')">
				<div
					style="display: inline-block; font-size: 22px; color: #8aceb5; float: left; height: 77px;">&gamma;</div>
				<div
					style="color: #8aceb5; height: 77px; width: 66px; display: inline-block; float: left;">
					<input type="text" size="4" id="pod8min" value="0" data-mini="true"
						onkeyup="refreshPods()"> <input type="text" value="1"
						size="4" id="pod8max" data-mini="true" onkeyup="refreshPods()">
				</div>
				<div id='pod8' class='eegGraph'
					style="color: #8aceb5; display: inline-block; height: 66px; float: left;">
				</div>
				<div
					style="color: #09EDB8; height: 77px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					<input type="text" size="4" id="pod13min" data-mini="true"
						value="0" onkeyup="refreshPods()"> <input value="1"
						type="text" size="4" id="pod13max" data-mini="true"
						onkeyup="refreshPods()">
				</div>
				<div id='pod13' class='eegGraph'
					style="color: #09EDB8; display: inline-block; height: 66px; float: right;">
				</div>
			</div>
		</div>
		<div data-role="collapsible" data-mini="true"
			class="selectorCollapsible">
			<h4>
				Head Movements <span style="font-style: italic;">(milli-g)</span>
			</h4>
			<div style="height: 133px;">
				<div
					style="display: inline-block; font-size: 22px; color: red; float: left; height: 133px;">X</div>
				<div
					style="height: 133px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					<input type="text" size="4" id="pod15min" data-mini="true"
						value="-1000" onkeyup="refreshPods()"> <input type="text"
						size="4" value="2000" id="pod15max" data-mini="true"
						onkeyup="refreshPods()">
				</div>
				<div id='pod15' class='eegGraph'
					style="color: red; width: 90%; height: 133px; display: inline-block;"></div>
			</div>
			<div style="height: 133px;">
				<div
					style="display: inline-block; font-size: 22px; color: green; float: left; height: 133px;">Y</div>
				<div
					style="height: 133px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					<input type="text" size="4" id="pod16min" value="-1000"
						data-mini="true" onkeyup="refreshPods()"> <input
						type="text" value="2000" size="4" id="pod16max" data-mini="true"
						onkeyup="refreshPods()">
				</div>
				<div id='pod16' class='eegGraph'
					style="color: green; width: 90%; height: 133px; display: inline-block;"></div>
			</div>
			<div style="height: 133px;">
				<div
					style="display: inline-block; font-size: 22px; color: #8aceb5; float: left; height: 133px;">Z</div>
				<div
					style="height: 77px; width: 66px; display: inline-block; float: left; font-size: 20px;">
					<input type="text" size="4" id="pod17min" value="-1000"
						data-mini="true" onkeyup="refreshPods()"> <input
						type="text" value="2000" size="4" id="pod17max" data-mini="true"
						onkeyup="refreshPods()">
				</div>
				<div id='pod17' class='eegGraph'
					style="color: #8aceb5; width: 90%; height: 133px; display: inline-block;"></div>
			</div>
		</div>
		<br />
		<div id='pod4' class='eegGraph'
			style="color: #CACAC9; display: none; width: 100%;">Low
			Frequency (2.5-6.1 Hz)</div>
	</div>

</body>
</html>