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
	drawLoop();
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

function drawLoop(time) {
	updateBar("microphone", meter.volume);
	$("#volume").val(meter.volume);
	rafID = window.requestAnimationFrame(drawLoop);
}

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

function updateBar(id, value) {
	$("#" + id).css("height", 100 - (value * 100) + "%");
}

