package app.signalprocessing;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.FastMath;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.jblas.DoubleMatrix;

import com.google.common.primitives.Doubles;

public class SignalProcessingUnit {
	public static double[] realVal = null;
	public static double[] AVG = null;
	public static double[] AllOuts = null;
	public static double[] AVGWeights = null;
	private static double[] inputValues = null;
	private static double[] outputValues = null;
	private static int INPUTSIZE;
	public static ArrayList<ArrayList<Double>> allinputs = new ArrayList<ArrayList<Double>>();
	public static ArrayList<ArrayList<Double>> cacheAvg = new ArrayList<ArrayList<Double>>();
	public static ArrayList<ArrayList<Double>> distance = new ArrayList<ArrayList<Double>>();
	public static ArrayList<ArrayList<Double>> velocity = new ArrayList<ArrayList<Double>>();
	public static ArrayList<ArrayList<Double>> tempIncoming2Smooth = new ArrayList<ArrayList<Double>>();
	public static ArrayList<ArrayList<Double>> filterMAXsAtBegninning = new ArrayList<ArrayList<Double>>();
	public static ArrayList<double[]> OutP = new ArrayList<double[]>();
	public static ArrayList<ArrayList<Double>> preparationofCache = new ArrayList<ArrayList<Double>>();
	private static ArrayList<Integer> CONFIGS;

	private static int SLIDING_WINDOW_SIZE = 0;

	public SignalProcessingUnit(int slidingWindowSize,
			ArrayList<Integer> config, int inputSize) {
		SLIDING_WINDOW_SIZE = slidingWindowSize;
		allinputs = null;
		cacheAvg = null;
		tempIncoming2Smooth = null;
		realVal = null;
		CONFIGS = config;
		INPUTSIZE = inputSize;
		resetArrays();
		lastTime = System.currentTimeMillis();
	}

	public static double[] getOutputValues(double[] rawSignals) {
		if (outputValues == null)
			outputValues = new double[rawSignals.length];
		fillSlidingWindow(rawSignals);
		measureAverageValuesInterval();
		for (int i = 0; i < CONFIGS.size(); i++) {
			switch (CONFIGS.get(i)) {
			case SignalProcessingConfig.SD:
				measureStandardDeviationValues(outputValues);
				break;
			default:
				break;
			}
		}
		return outputValues;
	}

	private static void extendToOutput(double[] toBeExtendedValues) {
		System.arraycopy(toBeExtendedValues, outputValues.length, outputValues,
				60, toBeExtendedValues.length);
	}

	private static void fillSlidingWindow(double[] inputs) {
		for (int i = 0; i < inputs.length; i++) {
			allinputs.get(i).add(inputs[i]);
			if (allinputs.get(i).size() >= SLIDING_WINDOW_SIZE)
				allinputs.get(i).remove(0);
		}
	}

	private static void measureNormalValues(double[] inputs) {
		outputValues = null;
		outputValues = new double[inputs.length];
		AVG = null;
		realVal = new double[8];
		for (int j = 0; j < inputs.length; j++) {
			realVal[j] = Math.abs(inputs[j]);// *
			filterMAXsAtBegninning.get(j).add(realVal[j]);
			if (filterMAXsAtBegninning.get(j).size() > 2)
				filterMAXsAtBegninning.get(j).remove(0);
		}
		if (filterMAXsAtBegninning.get(filterMAXsAtBegninning.size() - 1)
				.size() >= 2) {
			for (int i = 0; i < filterMAXsAtBegninning.size(); i++) {
				double[] arr = Doubles.toArray(filterMAXsAtBegninning.get(i));
				java.util.Arrays.sort(arr);
				int n = (int) Math.round(arr.length * 75 / 100);
				int m = (int) Math.round(arr.length - 1);
				NormalizedField norm = new NormalizedField(
						NormalizationAction.Normalize, null, 127, 0, arr[m],
						arr[n]);
				tempIncoming2Smooth.get(i).add(
						(double) Math.round(norm
								.normalize(filterMAXsAtBegninning.get(i)
										.get(filterMAXsAtBegninning.get(i)
												.size() - 1))));
				if (tempIncoming2Smooth.get(i).size() > SLIDING_WINDOW_SIZE)
					tempIncoming2Smooth.get(i).remove(0);
			}
			// if (tempIncoming2Smooth.get(tempIncoming2Smooth.size() -
			// 1).size() > 2)
			// measureAverageValuesInterval();

		}
	}

	private static void measureStandardDeviationValues(double[] inputs) {
		for (int i = 0; i < cacheAvg.size(); i++) {
			double[] arr = Doubles.toArray(cacheAvg.get(i));
			DescriptiveStatistics ds = new DescriptiveStatistics(arr);
			outputValues[i] = ds.getStandardDeviation();
		}
	}

	public static double measureScalarValues(double[] inputs) {
		double sum = 0;
		for (int i = 0; i < inputs.length; i++) {
			sum += Math.pow(inputs[i], 2);
		}
		return Math.sqrt(sum);
	}

	public static long lastTime = 0;

	public static double[] measureDistanceFromAccel(double[] acceleration) {
		double[] res = new double[4];
		// velocity(i) = velocity(i-1) + acceleration (i)
		// position(i) = position (i-1) + velocity (i)
		double time = System.currentTimeMillis() - lastTime;
		for (int i = 0; i < acceleration.length; i++) {
			if (distance.get(i).size() == 0) {
				velocity.get(i).add(0.0);
				distance.get(i).add(0.0);
			} else {
				velocity.get(i).add(acceleration[i] * time);
				distance.get(i).add(acceleration[i] * Math.pow(time, 2) + (velocity.get(i).get(velocity.get(i).size()-1) * time));
			}
			res[i] = distance.get(i).get(distance.get(i).size() - 1);
			if (distance.get(i).size() > SLIDING_WINDOW_SIZE) {
				distance.get(i).remove(0);
				velocity.get(i).remove(0);
			}
			lastTime = System.currentTimeMillis();
		}
		return res;
	}

	public static void measureAverageValuesInterval() {
		double[] tmp = new double[8];
		double[] tmpcoef = new double[28];
		int counterCoeff = 0;
		for (int i = 0; i < allinputs.size(); i++) {
			tmp[i] = Math
					.sqrt((sumPow2(allinputs.get(i)).doubleValue() / allinputs
							.get(i).size()));
			cacheAvg.get(i).add(tmp[i]);
			if (cacheAvg.get(allinputs.size() - 1).size() > 1) {
				ArrayList<double[]> curveList = new ArrayList<double[]>();
				for (int k = 0; k < cacheAvg.get(i).size(); k++) {
					double[] cccc = new double[2];
					cccc[1] = cacheAvg.get(i).get(k);
					curveList.add(cccc);
				}
				allinputs.get(i).addAll(CurveRegrission(curveList, 2));
				if (allinputs.get(0).size() > 2) {
					double wl = 0;
					for (int k = 0; k < allinputs.get(i).size(); k++) {
						if (k > 0)
							wl += Math.abs(allinputs.get(i).get(k - 1)
									- allinputs.get(i).get(k));
					}
					outputValues[i] = allinputs.get(i).get(
							allinputs.get(i).size() - 1);
					outputValues[8 + i] = FastMath.pow(wl, (double) 1 / 2);
				}
				// System.arraycopy(tmpcoef, 0, curved, 60, tmpcoef.length);
				if (allinputs.get(i).size() > SLIDING_WINDOW_SIZE)
					allinputs.get(i).remove(0);
			}
			if (cacheAvg.get(i).size() > SLIDING_WINDOW_SIZE) {
				cacheAvg.get(i).remove(0);
			}
		}
		// if (allinputs.get(allinputs.size() - 1).size() >= 1000) {
		// AVG = new double[9];
		// for (int i = 0; i < allinputs.size(); i++) {
		// }
		// }
	}

	public static ArrayList<Double> CurveRegrission(ArrayList<double[]> set,
			int detrendPolynomial) {
		PolynomialCurveFitter p = PolynomialCurveFitter
				.create(detrendPolynomial);
		WeightedObservedPoints wop = new WeightedObservedPoints();
		ArrayList<Double> res = new ArrayList<Double>();
		for (int i = 0; i < set.size(); i++) {
			wop.add(set.get(i)[0], set.get(i)[1]);
		}
		double[] coeff = p.fit(wop.toList());
		double val = coeff[0] / 127;
		res.add(val);
		return res;
	}

	public static void resetArrays() {
		allinputs = new ArrayList<ArrayList<Double>>();
		distance = new ArrayList<ArrayList<Double>>();
		velocity = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < 4; i++) {
			distance.add(new ArrayList<Double>());
			velocity.add(new ArrayList<Double>());
		}
		cacheAvg = new ArrayList<ArrayList<Double>>();
		tempIncoming2Smooth = new ArrayList<ArrayList<Double>>();
		filterMAXsAtBegninning = new ArrayList<ArrayList<Double>>();
		OutP = new ArrayList<double[]>();
		preparationofCache = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < INPUTSIZE; i++) {
			allinputs.add(new ArrayList<Double>());
			cacheAvg.add(new ArrayList<Double>());
			tempIncoming2Smooth.add(new ArrayList<Double>());
			filterMAXsAtBegninning.add(new ArrayList<Double>());
			preparationofCache.add(new ArrayList<Double>());
		}
		AVGWeights = new double[SLIDING_WINDOW_SIZE];
		for (int i = 0; i < SLIDING_WINDOW_SIZE; i++) {
			AVGWeights[i] = i + 1;
		}
	}

	public static Double sumPow2(ArrayList<Double> X) {
		double sum = 0.0;
		for (int i = 0; i < X.size(); i++) {
			sum += (double) FastMath.pow(X.get(i), 2);
		}
		return sum;
	}

	public static Double sumMH(ArrayList<Double> X) {
		double sum = 0.0;
		for (int i = 0; i < X.size(); i++) {
			sum += (double) 1 / X.get(i);
		}
		return sum;
	}

	public static Double sumN(ArrayList<Double> X) {
		double sum = 0.0;
		for (int i = 0; i < X.size(); i++) {
			sum += X.get(i);
		}
		return sum;
	}

	public static ArrayList<double[]> addTaskNo(
			ArrayList<double[]> amplifyTheRes2, int taskNo) {
		ArrayList<double[]> res = new ArrayList<double[]>();
		for (int i = 0; i < amplifyTheRes2.size(); i++) {
			double[] tmp = new double[amplifyTheRes2.get(i).length + 1];
			tmp[0] = taskNo;
			for (int j = 0; j < amplifyTheRes2.get(i).length; j++) {
				tmp[j + 1] = amplifyTheRes2.get(i)[j];
			}
			res.add(tmp);
		}
		return res;
	}
}
