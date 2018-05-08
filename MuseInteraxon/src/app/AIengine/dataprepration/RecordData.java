package app.AIengine.dataprepration;

import java.util.ArrayList;

public class RecordData {
	private static int tmpTimer = 0;
	public static int outTimer = 0;

	public static void main(String[] arString) {
//		MYOThread mt = new MYOThread();
//		Thread t = new Thread(mt);
//		t.setName("myo");
//		t.setPriority(Thread.NORM_PRIORITY);
//		t.setDaemon(true);
//		t.start();
//		for (int i = -1; i < 6; i++)
//			recordMainTask(15, 0, i, false, false);
//		System.out.println("done");
	}

	public static void recordMainTask(String value, boolean append) {
		ArrayList<String> StringEMGCollector = new ArrayList<String>();
		StringEMGCollector.add(value);
		ExcelDataStorage ds = new ExcelDataStorage(append);
		ds.insertDataToTheFile(StringEMGCollector, append);
	}
}
