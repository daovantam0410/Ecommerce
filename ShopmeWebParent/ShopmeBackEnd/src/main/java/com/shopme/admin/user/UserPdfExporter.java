package com.shopme.admin.user;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.common.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPdfExporter extends AbstractExporter{

    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        //Set Page size of document is A4
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        //Open document
        document.open();

        //Set properties of font title
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        //Set size text
        font.setSize(18);
        //Set color text
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("List of User", font);
        //format title(List of User) is text align center
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        //Add paragraph in to document
        document.add(paragraph);

        //Create new table includes 6 columns(as The number of field list users you need export to pdf)
        PdfPTable table = new PdfPTable(6);
        //Set width
        table.setWidthPercentage(100f);
        //Set spacing before of table
        table.setSpacingBefore(10);
        //Set width for all cell of the table
        table.setWidths(new float[] {1.2f, 4.0f, 3.0f, 3.0f, 3.0f, 1.7f});

        //Set header for table
        writeTableHeader(table);
        //Set data user for table
        writeTableData(table, listUsers);

        //Add table in to the document
        document.add(table);

        //Close document
        document.close();
    }

    private void writeTableData(PdfPTable table, List<User> listUsers) {
        //Loop and get data users
        for (User user: listUsers) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getEmail());
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getRoles().toString());
            table.addCell(String.valueOf(user.isEnabled()));
        }
    }


    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        //Set properties of header cell in the table
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        //Set properties of font title
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        //Set color text
        font.setColor(Color.WHITE);

        //Set font field UserID
        cell.setPhrase(new Phrase("ID", font));
        //Add cell it in to the table
        table.addCell(cell);

        //Set font field Email
        cell.setPhrase(new Phrase("Email", font));
        //Add cell it in to the table
        table.addCell(cell);

        //Set font field First Name
        cell.setPhrase(new Phrase("First Name", font));
        //Add cell it in to the table
        table.addCell(cell);

        //Set font field Last Name
        cell.setPhrase(new Phrase("Last Name", font));
        //Add cell it in to the table
        table.addCell(cell);

        //Set font field Roles
        cell.setPhrase(new Phrase("Roles", font));
        //Add cell it in to the table
        table.addCell(cell);

        //Set font field Enabled
        cell.setPhrase(new Phrase("Enabled", font));
        //Add cell it in to the table
        table.addCell(cell);
    }
}
