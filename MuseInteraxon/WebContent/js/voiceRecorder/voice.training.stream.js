var soundIdealValue = 0;

var streamSound;
var canvas2, context2, v, w, h;
window.AudioContext = window.AudioContext || window.webkitAudioContext;
audioContext = new AudioContext();
try {
	navigator.getUserMedia = navigator.getUserMedia
			|| navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
	navigator.getUserMedia({
		"audio" : {
			"mandatory" : {
				"googEchoCancellation" : "false",
				"googAutoGainControl" : "false",
				"googNoiseSuppression" : "false",
				"googHighpassFilter" : "false"
			},
			"optional" : []
		},
	}, gotStream, didntGetStream);
} catch (e) {
	alert('getUserMedia threw exception :' + e);
}

function didntGetStream() {
	alert('Stream generation failed.');
}

var mediaStreamSource = null;

function gotStream(stream) {
	mediaStreamSource = audioContext.createMediaStreamSource(stream);
	meter = createAudioMeter(audioContext);
	mediaStreamSource.connect(meter);
//	drawLoop();
}

function createAudioMeter(audioContext, clipLevel, averaging, clipLag) {
	var processor = audioContext.createScriptProcessor(512);
	processor.onaudioprocess = volumeAudioProcess;
	processor.clipping = false;
	processor.lastClip = 0;
	processor.volume = 0;
	processor.clipLevel = clipLevel || 0.98;
	processor.averaging = averaging || 0.95;
	processor.clipLag = clipLag || 750;
	processor.connect(audioContext.destination);
	processor.checkClipping = function() {
		if (!this.clipping)
			return false;
		if ((this.lastClip + this.clipLag) < window.performance.now())
			this.clipping = false;
		return this.clipping;
	};

	processor.shutdown = function() {
		this.disconnect();
		this.onaudioprocess = null;
	};

	return processor;
}

function volumeAudioProcess(event) {
	var buf = event.inputBuffer.getChannelData(0);
	var bufLength = buf.length;
	var sum = 0;
	var x;
	for ( var i = 0; i < bufLength; i++) {
		x = buf[i];
		if (Math.abs(x) >= this.clipLevel) {
			this.clipping = true;
			this.lastClip = window.performance.now();
		}
		sum += x * x;
	}
	var rms = Math.sqrt(sum / bufLength);
	this.volume = Math.max(rms, this.volume * this.averaging);
}

//function drawLoop(time) {
////	updateBar("microphone", meter.volume);
//	$("#microphone").css("height", "0%");
//	$("#microphone").parent().css("background-color", getColorForPercentage(1 - meter.volume));
//	$("#volume").val(1- meter.volume);
//	rafID = window.requestAnimationFrame(drawLoop);
//}

function playNoise() {
	var audioss = new Audio('sound/cardFan1.wav');
	audioss.play();
	soundIdealValue = 1;
	$("#volume").val(parseFloat($("#volume").val()) + 0.2);
	setTimeout(resetSoundIdealValue, 1000);
}

function resetSoundIdealValue() {
	soundIdealValue = 0;
}

var percentColors = [ {
	pct : 0.0,
	color : {
		r : 0xff,
		g : 0x00,
		b : 0
	}
}, {
	pct : 0.5,
	color : {
		r : 0xff,
		g : 0xff,
		b : 0
	}
}, {
	pct : 1.0,
	color : {
		r : 0x00,
		g : 0xff,
		b : 0
	}
} ];

var getColorForPercentage = function(pct) {
	for ( var i = 1; i < percentColors.length - 1; i++) {
		if (pct < percentColors[i].pct) {
			break;
		}
	}
	var lower = percentColors[i - 1];
	var upper = percentColors[i];
	var range = upper.pct - lower.pct;
	var rangePct = (pct - lower.pct) / range;
	var pctLower = 1 - rangePct;
	var pctUpper = rangePct;
	var color = {
		r : Math.floor(lower.color.r * pctLower + upper.color.r * pctUpper),
		g : Math.floor(lower.color.g * pctLower + upper.color.g * pctUpper),
		b : Math.floor(lower.color.b * pctLower + upper.color.b * pctUpper)
	};
	return 'rgb(' + [ color.r, color.g, color.b ].join(',') + ')';
	// or output as hex if preferred
}
function updateBar(id, value) {
	$("#" + id).css("height", 100 - (value * 100) + "%");
}
