package app.common;

public class MuseSignalEntity {
	public float alpha = 0;
	public float beta = 0;
	public float teta = 0;
	public float gamma = 0;
	public float delta = 0;
	public float _alpha = 0;
	public float _beta = 0;
	public float _teta = 0;
	public float _gamma = 0;
	public float _delta = 0;
	public float lowFreq = 0;
	public boolean foreheadConneted;
	public boolean blink;
	public float[] horseShoes;
	public float battery;
	public float temprature;
	public float DRL;
	public float REF;
	public float ACC_X;
	public float ACC_Y;
	public float ACC_Z;
	public float EEG1 = 0;
	public float EEG2 = 0;
	public float EEG3 = 0;
	public float EEG4 = 0;
	public float Concentration = 0;
	public float Meditation = 0;
	public String IMG = "";
	public double predictions = 0;
	
	/**
	 * @return the predictions
	 */
	public double getPredictions() {
		return predictions;
	}

	/**
	 * @param predictions the predictions to set
	 */
	public void setPredictions(double predictions) {
		this.predictions = predictions;
	}

	/**
	 * @return the iMG
	 */
	public String getIMG() {
		return IMG;
	}

	/**
	 * @param iMG the iMG to set
	 */
	public void setIMG(String iMG) {
		IMG = iMG;
	}

	/**
	 * @return the concentration
	 */
	public float getConcentration() {
		return Concentration;
	}

	/**
	 * @param concentration the concentration to set
	 */
	public void setConcentration(float concentration) {
		Concentration = concentration;
	}

	/**
	 * @return the meditation
	 */
	public float getMeditation() {
		return Meditation;
	}

	/**
	 * @param meditation the meditation to set
	 */
	public void setMeditation(float meditation) {
		Meditation = meditation;
	}

	public boolean isBlink() {
		return blink;
	}

	public void setBlink(boolean blink) {
		this.blink = blink;
	}

	public MuseSignalEntity(float alpha, float beta, float teta, float gamma,
			float delta, float _alpha, float _beta, float _teta, float _gamma,
			float _delta, float lowFreq, boolean foreheadConneted,
			float[] horseShoes, float battery, float temprature, float dRL,
			float rEF, float aCC_X, float aCC_Y, float aCC_Z, float eEG1,
			float eEG2, float eEG3, float eEG4, float concentration, float meditation) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.teta = teta;
		this.gamma = gamma;
		this.delta = delta;
		this._alpha = _alpha;
		this._beta = _beta;
		this._teta = _teta;
		this._gamma = _gamma;
		this._delta = _delta;
		this.lowFreq = lowFreq;
		this.foreheadConneted = foreheadConneted;
		this.horseShoes = horseShoes;
		this.battery = battery;
		this.temprature = temprature;
		this.DRL = dRL;
		this.REF = rEF;
		this.ACC_X = aCC_X;
		this.ACC_Y = aCC_Y;
		this.ACC_Z = aCC_Z;
		this.EEG1 = eEG1;
		this.EEG2 = eEG2;
		this.EEG3 = eEG3;
		this.EEG4 = eEG4;
		this.Concentration = concentration;
		this.Meditation = meditation;
	}

	public boolean isForeheadConneted() {
		return foreheadConneted;
	}

	public void setForeheadConneted(boolean foreheadConneted) {
		this.foreheadConneted = foreheadConneted;
	}

	public float[] getHorseShoes() {
		return horseShoes;
	}

	public void setHorseShoes(float[] horseShoes) {
		this.horseShoes = horseShoes;
	}

	public float getBattery() {
		return battery;
	}

	public void setBattery(float battery) {
		this.battery = battery;
	}

	public float getTemprature() {
		return temprature;
	}

	public void setTemprature(float temprature) {
		this.temprature = temprature;
	}

	public float getDRL() {
		return DRL;
	}

	public void setDRL(float dRL) {
		DRL = dRL;
	}

	public float getREF() {
		return REF;
	}

	public void setREF(float rEF) {
		REF = rEF;
	}

	public float getACC_X() {
		return ACC_X;
	}

	public void setACC_X(float aCC_X) {
		ACC_X = aCC_X;
	}

	public float getACC_Y() {
		return ACC_Y;
	}

	public void setACC_Y(float aCC_Y) {
		ACC_Y = aCC_Y;
	}

	public float getACC_Z() {
		return ACC_Z;
	}

	public void setACC_Z(float aCC_Z) {
		ACC_Z = aCC_Z;
	}

	public MuseSignalEntity(float alpha, float beta, float teta, float gamma,
			float delta, float _alpha, float _beta, float _teta, float _gamma,
			float _delta, float lowFreq, float eEG1, float eEG2, float eEG3,
			float eEG4) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.teta = teta;
		this.gamma = gamma;
		this.delta = delta;
		this._alpha = _alpha;
		this._beta = _beta;
		this._teta = _teta;
		this._gamma = _gamma;
		this._delta = _delta;
		this.lowFreq = lowFreq;
		EEG1 = eEG1;
		EEG2 = eEG2;
		EEG3 = eEG3;
		EEG4 = eEG4;
	}

	public float get_alpha() {
		return _alpha;
	}

	public void set_alpha(float _alpha) {
		this._alpha = _alpha;
	}

	public float get_beta() {
		return _beta;
	}

	public void set_beta(float _beta) {
		this._beta = _beta;
	}

	public float get_teta() {
		return _teta;
	}

	public void set_teta(float _teta) {
		this._teta = _teta;
	}

	public float get_gamma() {
		return _gamma;
	}

	public void set_gamma(float _gamma) {
		this._gamma = _gamma;
	}

	public float get_delta() {
		return _delta;
	}

	public void set_delta(float _delta) {
		this._delta = _delta;
	}

	public float getLowFreq() {
		return lowFreq;
	}

	public void setLowFreq(float lowFreq) {
		this.lowFreq = lowFreq;
	}

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

	public MuseSignalEntity() {
	}

	public MuseSignalEntity(float alpha, float beta, float teta, float gamma,
			float delta, float lowFreq, float eEG1, float eEG2, float eEG3,
			float eEG4) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.teta = teta;
		this.gamma = gamma;
		this.delta = delta;
		this.lowFreq = lowFreq;
		EEG1 = eEG1;
		EEG2 = eEG2;
		EEG3 = eEG3;
		EEG4 = eEG4;
	}

}
