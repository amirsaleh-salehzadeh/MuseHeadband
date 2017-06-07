<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/flot/0.8.3/jquery.flot.min.js"></script>
<script type="text/javascript" src="js/eegGraphs.js"></script>
<style>
body {
	background-color: #10161d;
}

#canvas {
	border: 1px solid red;
}

.eegGraph {
	display: inline-block;
	height: 100px;
	width: 100%;
}
</style>
<script>
	// 	$(function() {

	// 		var canvas = document.getElementById("canvas");
	// 		var ctx = canvas.getContext("2d");
	// 		var data = [];
	// 		for ( var i = 0; i < 5000; i++) {
	// 			data.push(Math.sin(i / 10) * 70 + 100);
	// 		}
	// 		var x = 0;
	// 		var panAtX = 250;
	// 		var continueAnimation = true;
	// 		animate();
	// 		$.ajax({
	// 			url : address + "/REST/GetWS/PingMe?ipAdd=" + $("#ipAddress").val(),
	// 			dataType : "json",
	// 			cache : false,
	// 			async : false,
	// 			beforeSend : function() {
	// 				$('#readerStat').html("");
	// 				$("#RaceForm").addClass("transparent").trigger('create');
	// 				document.getElementById("loader").style.display = "block";
	// 			},
	// 			success : function(data) {
	// 				setTimeout(function() {
	// 					if (data)
	// 						$('#readerStat').html('<label style="color: green;">Connected</label>');
	// 					else
	// 						$('#readerStat').html('<label style="color: red;">NOT Connected</label>');
	// 					$('#RaceForm').removeClass('transparent');
	// 					document.getElementById("loader").style.display = "none";
	// 				}, 2000);
	// 			},
	// 			error : function(xhr, settings, exception) {
	// 				// alert(xhr);
	// 			}
	// 		});
	// 		function animate() {
	// 			if (x > data.length - 1) {
	// 				return;
	// 			}
	// 			if (continueAnimation) {
	// 				requestAnimationFrame(animate);
	// 			}
	// 			if (x++ < panAtX) {
	// 				ctx.fillRect(x, data[x], 1, 1);
	// 			} else {
	// 				ctx.clearRect(0, 0, canvas.width, canvas.height);
	// 				// plot data[] from x-PanAtX to x 
	// 				for ( var xx = 0; xx < panAtX; xx++) {
	// 					var y = data[x - panAtX + xx];
	// 					ctx.fillRect(xx, y, 1, 1)
	// 				}
	// 			}
	// 		}
	// 	});
</script>
</head>
<body>
	<!-- 	<canvas id="canvas" width=300 height=300></canvas> -->
	<div class='eegGraphs'>
		<div id='pod0' class='eegGraph'></div>
		<div id='pod1' class='eegGraph'></div>
		<div id='pod2' class='eegGraph'></div>
		<div id='pod3' class='eegGraph'></div>
	</div>

</body>
</html>