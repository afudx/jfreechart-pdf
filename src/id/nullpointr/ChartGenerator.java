package id.nullpointr;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.sun.javafx.charts.Legend;

public class ChartGenerator {
	private static final int WIDTH = 1600;
	private static final int HEIGHT = 1000;
	
	
	public void generateChart(DefaultCategoryDataset dataSets[], String title, String xTitle, String yTitle, String filepath) {
		
		PdfWriter writer = null;
		Document document = null;

		try {
			writer = new PdfWriter(new FileOutputStream(filepath));
			PdfDocument pdfDoc = new PdfDocument(writer);
			document = new Document(pdfDoc, new PageSize(WIDTH, HEIGHT));
			document.setWidth(WIDTH);
			document.setHeight(HEIGHT);
			
			for(int i = 0; i < dataSets.length; i++) {
				JFreeChart chart = ChartFactory.createLineChart(title, xTitle, yTitle, dataSets[i], PlotOrientation.VERTICAL, true, true, false);
				document.add(convertChartToImage(chart, WIDTH, HEIGHT));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
		
	}


	private Image convertChartToImage(JFreeChart chart, int width, int height) throws IOException {
		Image result = null;
		BufferedImage original = chart.createBufferedImage(width, height);
		try (ByteArrayOutputStream outputStreaam = new ByteArrayOutputStream()) {

			ImageIO.write(original, "png", outputStreaam);
			outputStreaam.flush();

			ImageData image = ImageDataFactory.create(outputStreaam.toByteArray());

			result = new Image(image);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}
