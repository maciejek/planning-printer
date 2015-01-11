package pl.wroc.pwr.agile.annotation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.UserStory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * 
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<UserStory> listStories = (List<UserStory>) model.get("listStories");

		doc.add(new Paragraph("User stories:"));

		for (UserStory story : listStories) {
			PdfPTable userStoryTable = createUserStoryTable(story);
			doc.add(userStoryTable);
//			doc.add(new LineSeparator());

			Set<Task> userStoryTasks = story.getTasks();

			if (userStoryTasks != null) {

				for (Task task : userStoryTasks) {
					PdfPTable taskTable = createTaskTable(task);
					doc.add(taskTable);
//					doc.add(new LineSeparator());
				}
			}
		}
		
	}

	private PdfPTable createTaskTable(Task task) throws DocumentException {

		PdfPTable table = new PdfPTable(10);
		table.setWidthPercentage(50.0f);
		table.setWidths(new float[] { 5.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f });
		table.setSpacingBefore(10);
		
		Font smallFont = FontFactory.getFont("Arial", 8);
		   
		PdfPCell cellTask;
		cellTask = new PdfPCell();
		cellTask.setColspan(10);
		cellTask.setPhrase(new Phrase(task.getNumber() + ".  "+ task.getSummary()));
		cellTask.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellTask.setPaddingBottom(5);
		table.addCell(cellTask);
		table.completeRow();

		cellTask = new PdfPCell();
		cellTask.setColspan(2);
		cellTask.setPhrase(new Phrase("Odpowiedzialny:", smallFont));
		cellTask.setPaddingBottom(5);
		table.addCell(cellTask);
		cellTask.setColspan(8);
		cellTask.setPhrase(new Phrase("Darek"));
		table.addCell(cellTask);
		table.completeRow();

		cellTask = new PdfPCell();
		cellTask.setColspan(2);
		cellTask.setPhrase(new Phrase("Estymacja:", smallFont));
		cellTask.setPaddingBottom(5);
		table.addCell(cellTask);
		cellTask.setColspan(8);
		cellTask.setPhrase(new Phrase("5h"));
		table.addCell(cellTask);
		table.completeRow();

		cellTask = new PdfPCell();
		cellTask.setColspan(2);
		cellTask.setPhrase(new Phrase("Spedzono:", smallFont));
		cellTask.setPaddingBottom(5);
		table.addCell(cellTask);
		cellTask.setColspan(8);
		cellTask.setPhrase(new Phrase("3h 30 min"));
		cellTask.setPaddingBottom(5);
		table.addCell(cellTask);
		table.completeRow();

		cellTask = new PdfPCell();
		cellTask.setColspan(1);
		cellTask.setPhrase(new Phrase("Dzien:", smallFont));
		cellTask.setPaddingBottom(5);
		table.addCell(cellTask);
		table.completeRow();

		cellTask = new PdfPCell();
		cellTask.setColspan(1);
		cellTask.setPhrase(new Phrase("Czas:", smallFont));
		cellTask.setPaddingBottom(5);
		table.addCell(cellTask);
		table.completeRow();

		return table;
	}

	private PdfPTable createUserStoryTable(UserStory story) throws DocumentException {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 5.0f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setPhrase(new Phrase(story.getNumber() + ".  " + story.getSummary()+ " -- " + story.getPoints()));
		cell.setPadding(5);
		cell.setPaddingBottom(5);
		table.addCell(cell);
		
		return table;
	}

}
