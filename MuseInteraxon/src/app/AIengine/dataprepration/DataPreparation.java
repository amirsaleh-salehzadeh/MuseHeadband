package app.AIengine.dataprepration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.math3.stat.StatUtils;
import org.jblas.DoubleMatrix;

import app.AIengine.NeuralNetworks.FeedforwardNeuralNetwork;
import app.signalprocessing.SignalProcessingConfig;
import app.signalprocessing.SignalProcessingUnit;

import com.google.common.primitives.Doubles;

public class DataPreparation {
	public static DoubleMatrix[][] KinesDistance;
	static int AVGportion = 10;
	// static int noOfTasks = 10;

	public static ArrayList<double[]> PD = new ArrayList<double[]>();
	public static ArrayList<double[][]> PDALL = new ArrayList<double[][]>();
	public static ArrayList<double[][]> PDALLIDEAL = new ArrayList<double[][]>();
	public static SignalProcessingUnit ODAM;
	public static double noise;
	private static ArrayList<Double> TMPAllProcessedSignals = new ArrayList<>();
	private static ArrayList<Double> ideals = new ArrayList<Double>();

	// public static double IDEAL[][];

	public DataPreparation(int average_portion) {
		KinesDistance = null;
		AVGportion = average_portion;
		// noOfTasks = noOftask;
		PDALL = new ArrayList<double[][]>();
		PDALLIDEAL = new ArrayList<double[][]>();
		PD = new ArrayList<double[]>();
		TMPAllProcessedSignals = new ArrayList<>();
		ideals = new ArrayList<Double>();
	}

	public static void streamTraining(double[] input, double[] ideal) {
		ArrayList<Integer> spc = new ArrayList<Integer>();
		spc.add(SignalProcessingConfig.SD);
		if (ODAM == null)
			ODAM = new SignalProcessingUnit(AVGportion, spc, input.length);
		double[] tmpAVal = ODAM.getOutputValues(input);
		if (tmpAVal != null) {
			FeedforwardNeuralNetwork ffn = new FeedforwardNeuralNetwork(tmpAVal);
			ffn.RAWINPUT = input;
			if (ffn.INPUT == null) {
				ffn.INPUT = new double[ideal.length][input.length];
				ffn.IDEAL = new double[ideal.length][ideal.length];
			}
			for (int i = 0; i < input.length; i++) {
				TMPAllProcessedSignals.add(tmpAVal[i]);
				if (TMPAllProcessedSignals.size() >= AVGportion) {
					ffn.INPUT[i] = Doubles.toArray(TMPAllProcessedSignals);
					TMPAllProcessedSignals.remove(0);
				}
			}
			for (int i = 0; i < ideal.length; i++) {
				ideals.add(ideal[i]);
				if (ideals.size() >= AVGportion) {
					ffn.IDEAL[i] = Doubles.toArray(ideals);
					ideals.remove(0);
				}
			}
			String recording = tmpAVal[0] + "_" + ideal[0] + "\n";
			RecordData.recordMainTask(recording, true);
			ffn.dynamicNetwork(tmpAVal.length, 0, 0);
		}
//		System.out.println("SD>>");
//		System.out.printf("%.9f", tmpAVal[0]);
//		System.out.println("<<SD");
		
	}

	public static void fileReaderKines() {
		measureDistanceOfNormal();
		fileReader();
	}

	public static void fileReader() {
		String target_dir = "C:\\TMPFiles\\Kinesiology\\Main\\";
		File dir = new File(target_dir);
		File[] files = dir.listFiles();
		ArrayList<ArrayList<double[]>> TMPAllKines = new ArrayList<ArrayList<double[]>>();
		ArrayList<ArrayList<Integer>> ideals = new ArrayList<ArrayList<Integer>>();
		for (int k = 0; k < files.length; k++) {
			TMPAllKines.add(new ArrayList<double[]>());
			ideals.add(new ArrayList<Integer>());
		}
		ArrayList<double[]> realVals = new ArrayList<double[]>();
		for (int i = 0; i < files.length; i++) {
			BufferedReader inputStream = null;
			try {
				inputStream = new BufferedReader(new FileReader(files[i]));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String line;
			ArrayList<double[]> OnlineActivationRateMerged = new ArrayList<double[]>();
			ArrayList<ArrayList<double[]>> TMPAllTasks = null;
			// TMPAllTasks = new ArrayList<>();
			// for (int k = 0; k < files.length; k++) {
			// TMPAllTasks.add(new ArrayList<double[]>());
			// }
			// int counter = noOflines[minIndex];
			// SignalProcessingUnit ODAM = new SignalProcessingUnit(AVGportion,
			// new ArrayList<SignalProcessingConfig>(), );
			try {
				while ((line = inputStream.readLine()) != null) {
					if (line != null) {
						// System.out.println("Start "+
						// System.currentTimeMillis());
						if (ODAM.AllOuts != null) {
							double[] rawSignals = new double[2];
							double[] tmpAVal = ODAM.getOutputValues(rawSignals);
							for (int j = 0; j < TMPAllKines.size(); j++) {
								TMPAllKines.get(j).add(tmpAVal);
								if (i == j) {
									ideals.get(j).add(1);
								} else {
									ideals.get(j).add(0);
								}
							}
							realVals.add(ODAM.realVal);
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int j = 0; j < TMPAllKines.size(); j++) {
			double[][] training = new double[TMPAllKines.get(j).size()][TMPAllKines
					.get(j).get(0).length];
			for (int k = 0; k < TMPAllKines.get(j).size(); k++) {
				training[k] = TMPAllKines.get(j).get(k);
			}
			PDALL.add(training);
		}
		double[][] training = new double[ideals.get(0).size()][ideals.size()];// take
																				// *
																				// ideals.size()
																				// for
																				// one
																				// network
																				// for
																				// (int
																				// j
																				// =
																				// 0;
																				// j
																				// <
																				// ideals.size();
																				// j++)
																				// {
		// for (int k = 0; k < ideals.get(j).size(); k++) {
		// training[k][j] = ideals.get(j).get(k);
		// }

		// }

		// double[][] training = new double[ideals.get(0).size() *
		// ideals.size()][1];
		for (int j = 0; j < ideals.size(); j++) {
			// training = new double[ideals.get(j).size()][1];
			for (int k = 0; k < ideals.get(j).size(); k++) {
				training[k][j] = ideals.get(j).get(k);
			}
			PDALLIDEAL.add(training);
		}
	}

	private static void measureDistanceOfNormal() {
		String target_dir = "C:\\TMPFiles\\Kinesiology\\";
		File dir = new File(target_dir);

		File[] MainFolder = dir.listFiles();
		for (int z = 0; z < MainFolder.length; z++)
			if (!MainFolder[z].isFile()
					&& !MainFolder[z].getName().equalsIgnoreCase("Main")
					&& !MainFolder[z].getName().equalsIgnoreCase("Orientation")) {
				target_dir = "C:\\TMPFiles\\Kinesiology\\"
						+ MainFolder[z].getName() + "\\";

				dir = new File(target_dir);
				File[] files = dir.listFiles();

				if (KinesDistance == null)
					KinesDistance = new DoubleMatrix[MainFolder.length - 2][files.length];

				for (int i = 0; i < files.length; i++) {
					KinesDistance[z][i] = new DoubleMatrix(
							new double[files.length][8]);
					BufferedReader inputStream = null;
					try {
						inputStream = new BufferedReader(new FileReader(
								files[i]));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String line;
					// ODAM = new SignalProcessingUnit(AVGportion,
					// new SignalProcessingConfig());
					ArrayList<double[]> OnlineCurved = new ArrayList<double[]>();
					try {
						// if (i == 0) {
						while ((line = inputStream.readLine()) != null) {
							// ODAM.measureNormalValues(line);
							if (ODAM.AVG != null) {
								OnlineCurved.add(ODAM.AVG);
							}
						}
						ArrayList<ArrayList<Double>> tmp = new ArrayList<ArrayList<Double>>();
						for (int j = 0; j < 8; j++) {
							tmp.add(new ArrayList<Double>());
						}
						double[][] meanVals = new double[AVGportion][8];
						int counterMtx = 0;
						for (int h = 0; h < 8; h++) {
							for (int j = 0; j < OnlineCurved.size(); j++) {
								tmp.get(h).add(OnlineCurved.get(j)[h]);
								if (j != 0 && counterMtx < AVGportion)
									if (j % (OnlineCurved.size() / AVGportion) == 0) {
										meanVals[counterMtx][h] = StatUtils
												.mean(Doubles.toArray(tmp
														.get(h)));
										tmp = new ArrayList<ArrayList<Double>>();
										for (int f = 0; f < 8; f++) {
											tmp.add(new ArrayList<Double>());
										}
										// if (j == 0)
										counterMtx++;
									}
							}
							counterMtx = 0;
						}
						DoubleMatrix mean4file = new DoubleMatrix(meanVals);
						KinesDistance[z][i] = mean4file;
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}
}
