package pl.wroc.pwr.agile.annotation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.UserStory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
 
/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdfView {
 
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<UserStory> listStories = (List<UserStory>) model.get("listStories");
         
        doc.add(new Paragraph("User stories:"));
        
        for (UserStory story : listStories) {
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100.0f);
		    table.setWidths(new float[] {5.0f});
		    table.setSpacingBefore(10);
		    
		    Font font = FontFactory.getFont(FontFactory.TIMES);
	        font.setColor(BaseColor.WHITE);
	        
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(5);


	        table.addCell(story.getNumber() + ".  " + story.getSummary() + " -- " +story.getPoints());
	        doc.add(table);
	        doc.add(new LineSeparator());
	        
	        Set<Task> tasks = story.getTasks();
	        
	        if (tasks !=null) {
				
	        for (Task task : tasks) {
	        	PdfPTable table1 = new PdfPTable(1);
	          table1.setWidthPercentage(100.0f);
	          table1.setWidths(new float[] {3.0f});
	          table1.setSpacingBefore(10);
	           
	          // define font for table header row
	          Font fontTask = FontFactory.getFont(FontFactory.HELVETICA);
	          fontTask.setColor(BaseColor.WHITE);
	           
	          // define table header cell
	          PdfPCell cellTask = new PdfPCell();
	          cellTask.setBackgroundColor(BaseColor.BLUE);
	          cellTask.setPadding(5);
	           
	          // write table header
	          cellTask.setPhrase(new Phrase("Book Title", font));
	          table1.addCell(cell);
	           

		        table1.addCell(task.getNumber() + ".  " + task.getSummary());
		       
		        
		        PdfPTable table2 = new PdfPTable(2);
		        table2.setWidthPercentage(100.0f);
		          table2.setWidths(new float[] {2.0f, 2.0f});
		          table2.setSpacingBefore(10);
		          
		          PdfPCell cellTask2 = new PdfPCell();
		          cellTask2.setPadding(5);
		           
		          // write table header
		          cellTask2.setPhrase(new Phrase("odpowiedzialny", font));
		          table2.addCell(cellTask2);
		          table2.addCell(new Phrase("ktoś kto go robi"));
		          
		          table1.addCell(table2);
		        
		        
		        doc.add(table1);
		        doc.add(new LineSeparator());
			}
	        }
	        
		}
        
        
         
//        PdfPTable table = new PdfPTable(5);
//        table.setWidthPercentage(100.0f);
//        table.setWidths(new float[] {3.0f, 2.0f, 2.0f, 2.0f, 1.0f});
//        table.setSpacingBefore(10);
//         
//        // define font for table header row
//        Font font = FontFactory.getFont(FontFactory.HELVETICA);
//        font.setColor(BaseColor.WHITE);
//         
//        // define table header cell
//        PdfPCell cell = new PdfPCell();
//        cell.setBackgroundColor(BaseColor.BLUE);
//        cell.setPadding(5);
//         
//        // write table header
//        cell.setPhrase(new Phrase("Book Title", font));
//        table.addCell(cell);
//         
//        table.addCell(cell);
//         
//        // write table row data
//        for (Task aBook : listBooks) {
//            table.addCell(aBook.getSummary());
//            
//        }
//         
//        doc.add(table);
//         
    }
 
}
