package app.AIengine.dataprepration;

import java.util.ArrayList;
import java.util.Arrays;

import org.jblas.DoubleMatrix;

import app.signalprocessing.SignalProcessingConfig;
import app.signalprocessing.SignalProcessingUnit;

public class DataPreparationAccell {
	public static DoubleMatrix[][] KinesDistance;
	static int AVGportion = 10;
	// static int noOfTasks = 10;

	public static ArrayList<double[]> allProcessedSignals;
	public static SignalProcessingUnit ODAM;

	// public static double IDEAL[][];

	public static void recordAccelOnSpreedSheet(double[] input) {
		if (allProcessedSignals == null)
			allProcessedSignals = new ArrayList<double[]>();
		ArrayList<Integer> spc = new ArrayList<Integer>();
		spc.add(SignalProcessingConfig.SD);
		double[] accels = new double[4];
		double[] res = new double[8];
		for (int i = 0; i < input.length; i++) {
			accels[i] = input[i];
			res[i] = input[i];
		}
		if (ODAM == null)
			ODAM = new SignalProcessingUnit(10, spc, input.length);
		accels[3] = ODAM.measureScalarValues(input);
		res[3] = accels[3];
		double[] measuredDistances = ODAM.measureDistanceFromAccel(accels);
		for (int j = 0; j < measuredDistances.length; j++) {
			res[j + 4] = measuredDistances[j];
		}
		if (allProcessedSignals.size() == 0)
			allProcessedSignals.add(res);
		else if (!Arrays.equals(
				allProcessedSignals.get(allProcessedSignals.size() - 1),
				res))
			allProcessedSignals.add(res);
	}

	public static void exportExcelforAccels() {
		if (allProcessedSignals == null)
			return;
		ExcelDataStorage ed = new ExcelDataStorage(false);
		ArrayList<double[]> TMPAll0 = new ArrayList<double[]>();
		ArrayList<double[]> TMPAll3 = new ArrayList<double[]>();
		ArrayList<double[]> TMPAll1 = new ArrayList<double[]>();
		ArrayList<double[]> TMPAll2 = new ArrayList<double[]>();
		if (allProcessedSignals.size() <= 60000)
			for (int i = 0; i < allProcessedSignals.size(); i++) {
				TMPAll0.add(allProcessedSignals.get(i));
			}
		else {
			for (int i = 0; i < 60000; i++) {
				TMPAll0.add(allProcessedSignals.get(i));
			}
			if (TMPAll0.size() >= 60000)
				for (int i = 60000; i < 120000; i++) {
					TMPAll1.add(allProcessedSignals.get(i));
				}
			if (TMPAll1.size() >= 60000)
				for (int i = 120000; i < 180000; i++) {
					TMPAll2.add(allProcessedSignals.get(i));
				}
			if (TMPAll2.size() >= 60000)
				for (int i = 180000; i < allProcessedSignals.size(); i++) {
					TMPAll3.add(allProcessedSignals.get(i));
				}
		}
		ed.createExcel(TMPAll0, null, TMPAll1, TMPAll2, TMPAll3, null, null,
				null, null, null, null, null);
	}

}
