package app.datastream.eeg;

import com.google.gson.Gson;

import app.AIengine.dataprepration.RecordData;
import app.common.MuseSignalEntity;
import oscP5.*;

public class MuseOscServer {
	private boolean isAbs = false;
	public static MuseSignalEntity EEG;
	public static OscP5 museServer;
	private static Gson mapper;
	public static boolean record;
	private static long lastTime = 0;
	public static String jsonVal;

	void oscEvent(OscMessage msg) {
		if (lastTime == 0)
			lastTime = System.currentTimeMillis();

		if (lastTime + 5 > System.currentTimeMillis()) {
			return;
		}
		lastTime = System.currentTimeMillis();
		if (EEG == null) {
			EEG = new MuseSignalEntity();
			mapper = new Gson();

		}
		if (msg.checkAddrPattern("/muse/fft")) {
			for (int i = 0; i < 129; i++) {
				EEG.appendFFTValue(i, msg.get(i).floatValue());
				// System.out.print("EEG on channel " + i + ": " + msg.get(i).floatValue() +
				// "\n");
			}
		}
		if (msg.checkAddrPattern("/muse/eeg") == true) {
			EEG.setEEG1(msg.get(0).floatValue());
			EEG.setEEG2(msg.get(1).floatValue());
			EEG.setEEG3(msg.get(2).floatValue());
			EEG.setEEG4(msg.get(3).floatValue());
		}
		if (isAbs) {
			if (msg.checkAddrPattern("/muse/elements/low_freqs_absolute") == true) {
				EEG.setLowFreqABS(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/delta_absolute") == true) {
				EEG.setDeltaABS(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/theta_absolute") == true) {
				EEG.setTetaABS(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/alpha_absolute") == true) {
				EEG.setAlphaABS(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/beta_absolute") == true) {
				EEG.setBetaABS(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/gamma_absolute") == true) {
				EEG.setGammaABS(getVal(msg));
			}
		} else {
			if (msg.checkAddrPattern("/muse/elements/low_freqs_relative") == true) {
				EEG.setLowFreqR(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/delta_relative") == true) {
				EEG.setDeltaR(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/theta_relative") == true) {
				EEG.setTetaR(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/alpha_relative") == true) {
				EEG.setAlphaR(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/beta_relative") == true) {
				EEG.setBetaR(getVal(msg));
			}
			if (msg.checkAddrPattern("/muse/elements/gamma_relative") == true) {
				EEG.setGammaR(getVal(msg));
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
			float[] tmp = { msg.get(0).floatValue(), msg.get(1).floatValue(), msg.get(2).floatValue(),
					msg.get(3).floatValue() };
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
			EEG.setACC_X(msg.get(0).floatValue() / 2000);
			EEG.setACC_Y(msg.get(1).floatValue() / 2000);
			EEG.setACC_Z(msg.get(2).floatValue() / 2000);
		}
		if (msg.checkAddrPattern("/muse/gyro") == true) {
			System.out.println(msg.get(0).floatValue());
			System.out.println(msg.get(1).floatValue());
			System.out.println(msg.get(2).floatValue());
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
		if (record) {
			EEG.setIMG("");
			jsonVal = mapper.toJson(EEG);
			System.out.println(jsonVal);
			RecordData.recordMainTask(jsonVal, true);
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