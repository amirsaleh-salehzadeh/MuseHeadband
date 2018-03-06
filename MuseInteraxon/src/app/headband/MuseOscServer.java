package app.headband;

import oscP5.*;

public class MuseOscServer {
	private boolean isAbs = false;
	static MuseOscServer museOscServer;
	public static MuseSignalEntity EEG;
	OscP5 museServer;

	public static void main(String[] args) {
		museOscServer = new MuseOscServer();
		if (museOscServer.museServer != null
				&& museOscServer.museServer.isBroadcast()) {
			museOscServer.museServer.stop();
		}
		museOscServer.museServer = new OscP5(museOscServer, "localhost", 5003);
		// museOscServer.museServer.stop();
		// muse-io --device Muse-1E5B --osc osc.udp://localhost:5003
		EEG = new MuseSignalEntity();

	}

	void oscEvent(OscMessage msg) {
		if (EEG == null)
			EEG = new MuseSignalEntity();
		if (msg.checkAddrPattern("/muse/eeg") == true) {
			EEG.setEEG1(msg.get(0).floatValue());
			EEG.setEEG2(msg.get(1).floatValue());
			EEG.setEEG3(msg.get(2).floatValue());
			EEG.setEEG4(msg.get(3).floatValue());
		}
		if (isAbs) {
			if (msg.checkAddrPattern("/muse/elements/low_freqs_absolute") == true) {
				EEG.setLowFreq(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/delta_absolute") == true) {
				EEG.setDelta(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/theta_absolute") == true) {
				EEG.setTeta(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/alpha_absolute") == true) {
				EEG.setAlpha(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/beta_absolute") == true) {
				EEG.setBeta(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/gamma_absolute") == true) {
				EEG.setGamma(getVal(msg));
			}
		} else {
			if (msg.checkAddrPattern("/muse/elements/low_freqs_relative") == true) {
				EEG.setLowFreq(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/delta_relative") == true) {
				EEG.setDelta(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/theta_relative") == true) {
				EEG.setTeta(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/alpha_relative") == true) {
				EEG.setAlpha(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/beta_relative") == true) {
				EEG.setBeta(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/gamma_relative") == true) {
				EEG.setGamma(getVal(msg));
			}
		}
		if (msg.checkAddrPattern("/muse/elements/delta_session_score") == true) {
			EEG.set_delta(getVal(msg));
		}
		if (msg.checkAddrPattern("/muse/elements/theta_session_score") == true) {
			EEG.set_teta(getVal(msg));
		}
		if (msg.checkAddrPattern("/muse/elements/alpha_session_score") == true) {
			EEG.set_alpha(getVal(msg));
		}
		if (msg.checkAddrPattern("/muse/elements/beta_session_score") == true) {
			EEG.set_beta(getVal(msg));
		}
		if (msg.checkAddrPattern("/muse/elements/gamma_session_score") == true) {
			EEG.set_gamma(getVal(msg));
		}
		if (msg.checkAddrPattern("/muse/elements/horseshoe") == true) {
			float[] tmp = { msg.get(0).floatValue(), msg.get(1).floatValue(),
					msg.get(2).floatValue(), msg.get(3).floatValue() };
			EEG.setHorseShoes(tmp);
		}
		if (msg.checkAddrPattern("/muse/batt") == true) {
			EEG.setBattery(msg.get(0).intValue());
			EEG.setTemprature(msg.get(3).intValue());
		}
		if (msg.checkAddrPattern("/muse/elements/touching_forehead") == true) {
			if (msg.get(0).intValue() == 1) {
				EEG.setForeheadConneted(true);
			} else
				EEG.setForeheadConneted(false);
		}
		if (msg.checkAddrPattern("/muse/drlref") == true) {
			EEG.setDRL(msg.get(0).floatValue());
			EEG.setREF(msg.get(1).floatValue());
		}
		if (msg.checkAddrPattern("/muse/acc") == true) {
			EEG.setACC_X(msg.get(0).floatValue());
			EEG.setACC_Y(msg.get(1).floatValue());
			EEG.setACC_Z(msg.get(2).floatValue());
		}
		if (msg.checkAddrPattern("/muse/elements/blink") == true) {
			if (msg.get(0).intValue() == 1) {
				EEG.setBlink(true);
			} else
				EEG.setBlink(false);
		}

		if (msg.checkAddrPattern("/muse/elements/experimental/concentration") == true) {
			EEG.setConcentration(msg.get(0).floatValue() * 100);
		}
		if (msg.checkAddrPattern("/muse/elements/experimental/mellow") == true) {
			EEG.setMeditation(msg.get(0).floatValue() * 100);
		}

	}

	private float getVal(OscMessage msg) {
		float val = 0;
		int counter = 0;
		if (msg.get(0) != null && msg.get(0).floatValue() != 0) {
			val += Math.abs(msg.get(0).floatValue());
			counter++;
		}
		if (msg.get(1) != null && msg.get(1).floatValue() != 0) {
			val += Math.abs(msg.get(1).floatValue());
			counter++;
		}
		if (msg.get(2) != null && msg.get(2).floatValue() != 0) {
			val += Math.abs(msg.get(2).floatValue());
			counter++;
		}
		if (msg.get(3) != null && msg.get(3).floatValue() != 0) {
			val += Math.abs(msg.get(3).floatValue());
			counter++;
		}
		val = val / counter;
		return val;
	}
}