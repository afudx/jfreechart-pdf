package id.nullpointr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.io.IOException;


public class MainClass {
	public static void main(String[] args) {
		ChartGenerator chartGenerator = new ChartGenerator();
		String folderPath = "/data/tmp/jmx-data-test/result-file/";
		String fileName = "nullpointer-nodeTesting1.csv";
		String outputFile = "/data/tmp/result-chart.pdf";
		long fileCount = 0;
		
		try {
			fileCount = Files.list(Paths.get(folderPath)).count();
		} catch (java.io.IOException e1) {
			e1.printStackTrace();
		}
		
		DefaultCategoryDataset dataSets[] = new DefaultCategoryDataset[(int) fileCount];
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(folderPath+fileName));
			String sCurrentLine;
			int flag = 0;
			DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
			
			while ((sCurrentLine = bufferedReader.readLine()) != null) {
				if(flag == 0) {
					flag++;
					continue;
				}
				
				String splitLine[] = sCurrentLine.split(","); 
				
				double heapUsage = Integer.valueOf(splitLine[3]) / (1024*1024);
				dataSet.setValue(heapUsage, "Main", splitLine[0].split("\\ ")[1]);
				dataSet.setValue(heapUsage-49.2, "Messaging", splitLine[0].split("\\ ")[1]);
				dataSet.setValue(heapUsage-79.2, "Collmgmt", splitLine[0].split("\\ ")[1]);
				dataSet.setValue(heapUsage-100.2, "Stlm1", splitLine[0].split("\\ ")[1]);
				
			}
			
			dataSets[0] = dataSet;
			chartGenerator.generateChart(dataSets, "Heap Usage Chart", "Timestamp", "Heap", outputFile);

		} catch (IOException | java.io.IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bufferedReader.close();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
		
		/*DefaultCategoryDataset bluefin = new DefaultCategoryDataset();
		bluefin.setValue(3.7, "bluefin1-fsw", "18:00");
		
		dataSets[0] = bluefin;
		
		chartGenerator.generateChart(dataSets, "Heap Usage", "Timestamp", "Heap","/data/tmp/result-chart.pdf");*/

	}
	

}
