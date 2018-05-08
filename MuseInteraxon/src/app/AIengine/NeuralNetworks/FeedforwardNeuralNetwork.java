package app.AIengine.NeuralNetworks;

import java.io.File;
import java.util.ArrayList;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.jblas.DoubleMatrix;

import app.AIengine.dataprepration.DataPreparation;
import app.AIengine.dataprepration.ExcelDataStorage;

public class FeedforwardNeuralNetwork implements Runnable {
	public static int[] ResEval;
	public static BasicNetwork network;
	private static boolean busyToCompute;
	public static boolean trainingFinished;
	public static double predictionRes;

	public FeedforwardNeuralNetwork(double[] input) {
		super();
		busyToCompute = false;
		trainingFinished = false;
	}

	public static double INPUT[][];
	public static double RAWINPUT[];
	public static double IDEAL[][];
	public static String outputRes;
	public static ArrayList<double[]> TMPAll;

	// @Override
	public void run() {
//		killer = true;
		// ArrayList<double[][]> dataAll = new ArrayList<double[][]>();
		// ArrayList<double[][]> dataAllIdeals = new ArrayList<double[][]>();
//		DataPreparation DP = new DataPreparation(AVGportion);
//		DP.fileReaderKines();
		// dataAll = DP.PDALL;
		// dataAllIdeals = DP.PDALLIDEAL;
		int min = 0;
//		for (int i = 0; i < dataAll.size(); i++) {
//			if (dataAll.get(i).length < min || min == 0)
//				min = dataAll.get(i).length;
//		}
//		INPUT = new double[min * dataAll.size()][8];
//		for (int i = 0; i < dataAll.size(); i++) {
//			for (int j = 0; j < min; j++) {
//				INPUT[(i * min) + j] = dataAll.get(i)[j];
//			}
//		}
		ExcelDataStorage DS = new ExcelDataStorage(false);
		TMPAll = new ArrayList<double[]>();
		ArrayList<double[]> TMPAll0 = new ArrayList<double[]>();
		ArrayList<double[]> TMPAll3 = new ArrayList<double[]>();
		ArrayList<double[]> TMPAll1 = new ArrayList<double[]>();
		ArrayList<double[]> TMPAll2 = new ArrayList<double[]>();
		ArrayList<double[]> normal = new ArrayList<double[]>();
//		for (int k = 0; k < dataAllIdeals.size(); k++) {
//			INPUT = new double[min][8];
//			for (int j = 0; j < min; j++) {
//				INPUT[j] = dataAll.get(k)[j];
//				// if (k == 0)
//				// TMPAll.add(INPUT[j]);
//			}
//			IDEAL = dataAllIdeals.get(k);
//			dynamicNetwork(dataAll.get(0)[0].length, 3, k);//
//		}
		if (TMPAll.size() <= 60000)
			for (int i = 0; i < TMPAll.size(); i++) {
				TMPAll0.add(TMPAll.get(i));
			}
		else {
			for (int i = 0; i < 60000; i++) {
				TMPAll0.add(TMPAll.get(i));
			}
			if (TMPAll0.size() >= 60000)
				for (int i = 60000; i < 120000; i++) {
					TMPAll1.add(TMPAll.get(i));
				}
			// if (TMPAll1.size() >= 60000)
			// for (int i = 120000; i < 180000; i++) {
			// TMPAll2.add(TMPAll.get(i));
			// }
			// if (TMPAll2.size() >= 60000)
			// for (int i = 180000; i < TMPAll.size(); i++) {
			// TMPAll3.add(TMPAll.get(i));
			// }
		}
		DS.createExcel(TMPAll0, normal, TMPAll1, TMPAll2, TMPAll3, null, null,
				null, null, null, null, null);
		Thread.currentThread().interrupt();
	}

	public static void dynamicNetwork(int inputSize, int middLayer,
			int networkNo) {
		if (busyToCompute)
			return;
		busyToCompute = true;
		if (network == null) {
			network = new BasicNetwork();
			network.addLayer(new BasicLayer(inputSize));// input
//			network.addLayer(new BasicLayer(new ActivationSigmoid(), true,
//					middLayer));// middle
			network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));// output
			network.getStructure().finalizeStructure();
			network.reset();
		}
		MLDataSet trainingSet = new BasicMLDataSet(INPUT, IDEAL);
		// if (networkNo <= 6)
		// network = (BasicNetwork) EncogDirectoryPersistence
		// .loadObject(new File("C:\\TMPFiles\\Networks\\network" + networkNo));
		ResilientPropagation train = new ResilientPropagation(network,
				trainingSet);
		int epoch = 1;
		// long starttime = System.currentTimeMillis();
		// if (networkNo > 6) {
		do {
			train.iteration();
//			outputRes = "Epoch #" + epoch + " Error:" + train.getError();
//			System.out.println(outputRes);
			epoch++;
		} while (train.getError() > 0.01 && epoch < 2000 
				&& !Thread.currentThread().isInterrupted());
		middLayer++;
		if (train.getError() > 0.01)
			dynamicNetwork(inputSize, middLayer, networkNo);
		MLData data = new BasicMLData(RAWINPUT.length);
		data.setData(RAWINPUT);
		network.compute(data);
		final MLData output = network.compute(data);
		for (int j = 0; j < output.size(); j++) {
			double outp = output.getData(j);
			predictionRes = outp * 100;
		}

		train.finishTraining();
		busyToCompute = false;
		System.out.println("Neural Network Results: " + predictionRes);
		// if (networkNo > 6)
		if (trainingFinished)
			EncogDirectoryPersistence.saveObject(new File(
					"C:\\TMPFiles\\Networks\\network" + networkNo), network);
		Encog.getInstance().shutdown();
	}
}
//