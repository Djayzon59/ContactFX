package pdf;

import bean.ContactBean;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import model.Personne;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;


public class PrintPDF {

    private ContactBean contactBean;
    private PdfWriter pdfWriter;
    private PdfDocument pdfDocument;
    private Document document;
    private File file;


    public PrintPDF(ContactBean contactBean) throws IOException {
        this.contactBean = contactBean;
        file = contactBean.getFile();
        try {
            String stringPdf = "C:\\Users\\Utilisateur1\\Desktop\\listeContact.pdf";
            pdfWriter = new PdfWriter(stringPdf);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        pdfDocument = new PdfDocument(pdfWriter);
        document = new Document(pdfDocument, PageSize.A4);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        createHeader();
        createTable();
        document.close();
    }

    public void createHeader() throws MalformedURLException {
        String imageFile = "C:\\Users\\Utilisateur1\\Desktop\\contact.png";
        ImageData data = ImageDataFactory.create(imageFile);
        Image image = new Image(data);
        image.setHeight(100);
        image.setWidth(100);
        image.setFixedPosition(45, 730);
        this.document.add(image);
        Paragraph paragraph = new Paragraph();
        paragraph.setFontSize(20);
        paragraph.add("Votre liste de contacts");
        paragraph.setUnderline();
        paragraph.setFixedPosition(200, 750, 200);
        this.document.add(paragraph);
    }

    public void createTable() {
        float[] columnWidths = {30, 30, 30, 30, 30, 30};
        Table table = new Table(columnWidths);
        table.addCell("NOM");
        table.addCell("Prénom");
        table.addCell("Rue");
        table.addCell("Code Postal");
        table.addCell("Ville");
        table.addCell("Date de naissance");
        for (Personne personne : contactBean.getAllPersonnes()) {
            table.addCell(personne.getLastName());
            table.addCell(personne.getFirstName());
            table.addCell(personne.getStreet());
            table.addCell(String.valueOf(personne.getPostalCode()));
            table.addCell(personne.getCity());
            table.addCell(String.valueOf(personne.getBirthday()));
        }
        table.setFixedPosition(45, 350, 500);

        this.document.add(table);
    }

    public void openPdf() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(new String[] // 2 parametres pris dans un tableau, l'appli et le pdf
                    {"\"C:\\Program Files\\Mozilla Firefox\\firefox.exe\"",
                            "C:\\Users\\Utilisateur1\\Desktop\\listeContact.pdf"});
        } catch (Exception err) {
            System.out.println("err = " + err);
        }

    }
}
