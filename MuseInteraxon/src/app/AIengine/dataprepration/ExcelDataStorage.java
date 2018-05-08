package app.AIengine.dataprepration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelDataStorage {
	public static String filename = "C:\\TMPFiles\\ExcelResults.xls";
	public static boolean NEW_LOG = false;

	public ExcelDataStorage(boolean log) {
		NEW_LOG = log;
	}



	public void insertDataToTheFile(ArrayList<String> input, boolean append) {
		PrintWriter out = null;
		try {
			File f = new File("C:\\RecordingFiles");
			if(!f.exists())
				f.mkdir();
			f = new File("C:\\RecordingFiles\\recording.txt");
			if(!f.exists())
				f.createNewFile();
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					"C:\\RecordingFiles\\recording.txt", append)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int j = 0; j < input.size(); j++) {
			out.println(input.get(j).toString());
		}
		out.close();
	}

	public void appendEvaluation(ArrayList<double[]> input) {
		try {
			new FileOutputStream(filename, true);

			FileInputStream Fin = new FileInputStream(filename);
			POIFSFileSystem POIfs = new POIFSFileSystem(Fin);

			HSSFWorkbook workbook = new HSSFWorkbook(POIfs);
			HSSFSheet sheet = workbook.getSheet("Evaluating");
			int startPoint = sheet.getLastRowNum();
			HSSFRow row = sheet.createRow((int) startPoint);
			for (int i = 0; i < input.size(); i++) {
				row = sheet.createRow((int) (startPoint + i));
				for (int j = 0; j < input.get(i).length; j++) {
					row.createCell(j).setCellValue(input.get(i)[j]);
				}
			}
			FileOutputStream fileOut;
			try {
				fileOut = new FileOutputStream(filename);
				try {
					workbook.write(fileOut);
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createExcel(ArrayList<double[]> pureRawEMG,
			ArrayList<double[]> RawEMGABS, ArrayList<double[]> testNormal,
			ArrayList<double[]> testAverage, ArrayList<double[]> testRelative,
			ArrayList<double[]> MedianMax, ArrayList<double[]> powerRelative,
			ArrayList<double[]> Q1Q3, ArrayList<double[]> maxElectrodes,
			ArrayList<double[]> arrayMaximum,
			ArrayList<double[]> arrayAverageInterval,
			ArrayList<double[]> eventCatcher) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFRow row;
		HSSFSheet sheet;
		HSSFRow rowhead;
		if (pureRawEMG != null && pureRawEMG.size() > 0) {
			sheet = workbook.createSheet("Raw");
			rowhead = sheet.createRow((int) 0);
			//
			// // 1
			// for (int i = 0; i <= 50; i++) {
			// rowhead.createCell(i).setCellValue("" + i + 1 + "");
			// }
			for (int i = 0; i < pureRawEMG.size(); i++) {
				row = sheet.createRow((int) i);
				int tmp = pureRawEMG.get(i).length;
				if (tmp > 250)
					tmp = 22;
				for (int j = 0; j < tmp; j++) {
					row.createCell(j).setCellValue(pureRawEMG.get(i)[j]);
				}
			}
		}

		// 2
		if (RawEMGABS != null && RawEMGABS.size() > 0) {
			sheet = workbook.createSheet("Absolute");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 50; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < RawEMGABS.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < RawEMGABS.get(i).length; j++) {
					row.createCell(j).setCellValue(RawEMGABS.get(i)[j]);
				}
			}
		}
		// 3
		if (testNormal != null && testNormal.size() > 0) {
			sheet = workbook.createSheet("Normal");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 50; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < testNormal.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < testNormal.get(i).length; j++) {
					row.createCell(j).setCellValue(testNormal.get(i)[j]);
				}
			}
		}
		// 4
		if (testAverage != null && testAverage.size() > 0) {
			sheet = workbook.createSheet("Average");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 50; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < testAverage.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < testAverage.get(i).length; j++) {
					row.createCell(j).setCellValue(testAverage.get(i)[j]);
				}
			}
		}
		// 5
		if (testRelative != null && testRelative.size() > 0) {
			sheet = workbook.createSheet("Relative");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 50; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < testRelative.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < testRelative.get(i).length; j++) {
					row.createCell(j).setCellValue(testRelative.get(i)[j]);
				}
			}
		}

		// 6
		if (MedianMax != null && MedianMax.size() > 0) {
			sheet = workbook.createSheet("Median Max");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 50; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < MedianMax.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < MedianMax.get(i).length; j++) {
					row.createCell(j).setCellValue(MedianMax.get(i)[j]);
				}
			}
		}
		// 7
		if (powerRelative != null && powerRelative.size() > 0) {
			sheet = workbook.createSheet("AbsolutePower");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 50; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < powerRelative.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < powerRelative.get(i).length; j++) {
					row.createCell(j).setCellValue(powerRelative.get(i)[j]);
				}
			}
		}
		// 8
		if (Q1Q3 != null && Q1Q3.size() > 0) {
			sheet = workbook.createSheet("Q1Q3");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 50; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < Q1Q3.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < Q1Q3.get(i).length; j++) {
					row.createCell(j).setCellValue(Q1Q3.get(i)[j]);
				}
			}
		}
		// 9

		if (maxElectrodes != null && maxElectrodes.size() > 0) {
			sheet = workbook.createSheet("Active Electrodes");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 30; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < maxElectrodes.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < maxElectrodes.get(i).length; j++) {
					row.createCell(j).setCellValue(maxElectrodes.get(i)[j]);
				}
			}
		}
		// 10
		if (arrayMaximum != null && arrayMaximum.size() > 0) {
			sheet = workbook.createSheet("Maximum Value");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 30; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < arrayMaximum.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < arrayMaximum.get(i).length; j++) {
					row.createCell(j).setCellValue(arrayMaximum.get(i)[j]);
				}
			}
		}
		// 11
		if (arrayAverageInterval != null && arrayAverageInterval.size() > 0) {
			sheet = workbook.createSheet("Average Interval");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 30; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < arrayAverageInterval.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < arrayAverageInterval.get(i).length; j++) {
					row.createCell(j).setCellValue(
							arrayAverageInterval.get(i)[j]);
				}
			}
		}
		// 12
		if (eventCatcher != null && eventCatcher.size() > 0) {
			sheet = workbook.createSheet("Event Catcher");
			rowhead = sheet.createRow((int) 0);
			for (int i = 0; i <= 30; i++) {
				rowhead.createCell(i).setCellValue("" + i + 1 + "");
			}
			for (int i = 0; i < eventCatcher.size(); i++) {
				row = sheet.createRow((int) i);
				for (int j = 0; j < eventCatcher.get(i).length; j++) {
					row.createCell(j).setCellValue(eventCatcher.get(i)[j]);
				}
			}
		}
		// if (testNormal != null && testNormal.size() > 0) {
		// sheet = workbook.createSheet("Evaluating");
		// rowhead = sheet.createRow((int) 0);
		// for (int i = 0; i <= 20; i++) {
		// rowhead.createCell(i).setCellValue("" + i + 1 + "");
		// }
		// }
		FileOutputStream fileOut;
		try {
			File fileName = new File(filename);
			fileOut = new FileOutputStream(fileName);
			try {
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Done");
	}

}
