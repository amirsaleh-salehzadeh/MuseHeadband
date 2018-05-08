<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/flot.js"></script>
<script type="text/javascript" src="js/cciStreamGraphs.js"></script>
<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="css/jqm-demos.css">
<link rel="stylesheet" href="css/text.style.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript" src="js/webgazer.js"></script>
<script type="text/javascript">
webgazer.setGazeListener(function(data, elapsedTime) {
	if (data == null) {
		return;
	}
	var xprediction = data.x; 
	var yprediction = data.y;
	$("#gazerDiv").css("left", xprediction);
	$("#gazerDiv").css("top", yprediction);
}).begin();
</script>
</head>
<body>
	<div id="gazerDiv"
		style="background-color: red; width: 10px; height: 10px; position: absolute; z-index: 100;"></div>
	<div style="display: inline-block; width: 100%;" id="headerDiv">
		<table style="width: 100%; height: 32px;">
			<tr>
				<td><a href="#" class="red"
					style="display: block; float: left;" onclick="stopEEG()"><img
						alt="stop" src="images/stop.png" /></a></td>
				<td style="height: 24px;">
					<div class="w3-border" style="height: 24px;">
						<div class="w3-green w3-center" style="height: 24px;" id="concBar">Concentration
							0%</div>
					</div>
					<div class="w3-border" style="height: 24px;">
						<div class="w3-blue w3-center" style="height: 24px;" id="dreamBar">Dreaming
							0%</div>
					</div>
				</td>
				<td><div id="blinkContainer" style="visibility: hidden;">
						<img alt="blink" src="images/eye.png">
					</div></td>
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
				<td><a href="#" class="green"
					style="display: inline-block; float: right;" onclick="startEEG()"><img
						alt="start" src="images/play.png" /></a></td>
			</tr>
		</table>



	</div>
	<div style="display: inline;">
		<div style="height: 66px;">
			<div id='pod0' class='eegGraph'
				style="color: #8aceb5; height: 55px; width: 100%; display: inline-block; float: left;">
			</div>
			<!-- 				<div -->
			<!-- 					style="color: red; height: 222px; width: 66px; display: inline-block; float: left; font-size: 20px;"> -->
			<!-- 					Dreaming <input type="text" size="6" id="pod19min" placeholder="0" -->
			<!-- 						data-mini="true" value='0' onkeyup="refreshPods()"> <input -->
			<!-- 						type="text" size="6" id="pod19max" data-mini="true" -->
			<!-- 						placeholder="100" value='100' onkeyup="refreshPods()"> -->
			<!-- 				</div> -->
			<!-- 				<div id='pod19' class='eegGraph' -->
			<!-- 					style="color: red; height: 222px; display: inline-block; float: right;"> -->
			<!-- 				</div> -->
		</div>
	</div>
	<div
		style="position: absolute; bottom: 0; background-color: white; width: 100%; overflow: auto;"
		id="textContainer">
		<article>
			<div class="row">
				<div class="col-sm-12 col-md-6 col-lg-6 rd1">
					<p class="reading">By the year 2050, nearly 80% of the Earth's
						population will live in urban centres. Applying the most
						conservative estimates to current demographic trends, the human
						population will increase by about three billion people by then. An
						estimated 10 hectares of new land (about 20% larger than Brazil)
						will be needed to grow enough food to feed them, if traditional
						farming methods continue as they are practised today. At present,
						throughout the world, over 80% of the land that is suitable for
						raising crops is in use. Historically, some 15% of that has been
						laid waste by poor management practices. What can be done to
						ensure enough food for the world's population to live on?</p>
					<p class="reading">The concept of indoor farming is not new,
						since hothouse production of tomatoes and other produce has been
						in vogue for some time. What is new is the urgent need to scale up
						this technology to accommodate another three billion people. Many
						believe an entirely new approach to indoor farming is required,
						employing cutting-edge technologies. One such proposal is for the
						'Vertical Farm'. The concept is of multi-storey buildings in which
						food crops are grown in environmentally controlled conditions.
						Situated in the heart of urban centres, they would drastically
						reduce the amount of transportation required to bring food to
						consumers. Vertical farms would need to be efficient, cheap to
						construct and safe to operate. If successfully implemented,
						proponents claim, vertical farms offer the promise of urban
						renewal, sustainable production of a safe and varied food supply
						(through year-round production of all crops), and the eventual
						repair of ecosystems that have been sacrificed for horizontal
						farming.</p>
					<p class="reading">It took humans 10,000 years to learn how to
						grow most of the crops we now take for granted. Along the way, we
						despoiled most of the land we worked, often turning verdant,
						natural ecozones into semi-arid deserts. Within that same time
						frame, we evolved into an urban species, in which 60% of the human
						population now lives vertically in cities. This means that, for
						the majority, we humans have shelter from the elements, yet we
						subject our food-bearing plants to the rigours of the great
						outdoors and can do no more than hope for a good weather year.
						However, more often than not now, due to a rapidly changing
						climate, that is not what happens. Massive floods, long droughts,
						hurricanes and severe monsoons take their toll each year,
						destroying millions of tons of valuable crops.</p>
				</div>
				<div class="col-sm-12 col-md-6 col-lg-6 rd1">
					<p class="reading">The supporters of vertical farming claim
						many potential advantages for the system. For instance, crops
						would be produced all year round, as they would be kept in
						artificially controlled, optimum growing conditions. There would
						be no weather-related crop failures due to droughts, floods or
						pests. All the food could be grown organically, eliminating the
						need for herbicides, pesticides and fertilisers. The system would
						greatly reduce the incidence of many infectious diseases that are
						acquired at the agricultural interface. Although the system would
						consume energy, it would return energy to the grid via methane
						generation from composting non-edible parts of plants. It would
						also dramatically reduce fossil fuel use, by cutting out the need
						for tractors, ploughs and shipping.</p>
					<p class="reading">A major drawback of vertical farming,
						however, is that the plants would require artificial light.
						Without it, those plants nearest the windows would be exposed to
						more sunlight and grow more quickly, reducing the efficiency of
						the system. Single-storey greenhouses have the benefit of natural
						overhead light: even so, many still need artificial lighting. A
						multi-storey facility with no natural overhead light would require
						far more. Generating enough light could be prohibitively
						expensive, unless cheap, renewable energy is available, and this
						appears to be rather a future aspiration than a likelihood for the
						near future.</p>
					<p class="reading">One variation on vertical farming that has
						been developed is to grow plants in stacked trays that move on
						rails. Moving the trays allows the plants to get enough sunlight.
						This system is already in operation, and works well within a
						single-storey greenhouse with light reaching it from above: it is
						not certain, however, that it can be made to work without that
						overhead natural light.</p>
					<p class="reading">Vertical farming is an attempt to address
						the undoubted problems that we face in producing enough food for a
						growing population. At the moment, though, more needs to be done
						to reduce the detrimental impact it would have on the environment,
						particularly as regards the use of energy. While it is possible
						that much of our food will be grown in skyscrapers in future, most
						experts currently believe it is far more likely that we will
						simply use the space available on urban rooftops.</p>
				</div>
			</div>
		</article>
	</div>
</body>
</html>