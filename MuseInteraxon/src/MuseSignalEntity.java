public class MuseSignalEntity {
	public float alpha;
	public float beta;
	public float teta;
	public float gamma;
	public float delta;
	public float EEG1;
	public float EEG2;
	public float EEG3;
	public float EEG4;

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public float getBeta() {
		return beta;
	}

	public void setBeta(float beta) {
		this.beta = beta;
	}

	public float getTeta() {
		return teta;
	}

	public void setTeta(float teta) {
		this.teta = teta;
	}

	public float getGamma() {
		return gamma;
	}

	public void setGamma(float gamma) {
		this.gamma = gamma;
	}

	public float getDelta() {
		return delta;
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

	public float getEEG1() {
		return EEG1;
	}

	public void setEEG1(float eEG1) {
		EEG1 = eEG1;
	}

	public float getEEG2() {
		return EEG2;
	}

	public void setEEG2(float eEG2) {
		EEG2 = eEG2;
	}

	public float getEEG3() {
		return EEG3;
	}

	public void setEEG3(float eEG3) {
		EEG3 = eEG3;
	}

	public float getEEG4() {
		return EEG4;
	}

	public void setEEG4(float eEG4) {
		EEG4 = eEG4;
	}

	public MuseSignalEntity(float alpha, float beta, float teta, float gamma,
			float delta, float eEG1, float eEG2, float eEG3, float eEG4) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.teta = teta;
		this.gamma = gamma;
		this.delta = delta;
		EEG1 = eEG1;
		EEG2 = eEG2;
		EEG3 = eEG3;
		EEG4 = eEG4;
	}

}
