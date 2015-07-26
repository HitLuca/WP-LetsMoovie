package json.payments;

import json.tickets.TicketData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/** Classe che permette di generare i pdf contenenti i biglietti
 * Created by etrunon on 23/07/15.
 */
public class PdfTicketCreator {

    private PDRectangle rect;
    private PDFont fontPlain;

    public ByteArrayOutputStream createPdf(List<TicketData> ticketData, ServletContext context) throws Exception {

        ImageIO.setUseCache(false);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);
        // PDPage.PAGE_SIZE_LETTER is also possible
        rect = page1.getMediaBox();
        // rect can be used to get the page width and height
        document.addPage(page1);

        // Create a new font object selecting one of the PDF base fonts
        fontPlain = PDType1Font.HELVETICA;

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream cos = new PDPageContentStream(document, page1);

        float margin = 50f, intraTicket = 100;
        float posX = rect.getLowerLeftX() + margin, posY = rect.getUpperRightY() - margin - 25;
        printHeader(posX, posY, document, cos, context);
        posY -= 35;

        cos.beginText();
        cos.setFont(fontPlain, 15);
        cos.moveTextPositionByAmount(posX, posY);
        cos.drawString("Codice Prenotazione:  " + ticketData.get(0).getCode());
        cos.endText();

        posY -= 50;

        for (int j = 0; j < ticketData.size(); j++) {
            if (j % 7 == 6) {
                cos.drawLine(posX - 30, posY + 30, posX + 470, posY + 30);

                cos.close();
                PDPage page2 = new PDPage(PDPage.PAGE_SIZE_A4);
                document.addPage(page2);
                cos = new PDPageContentStream(document, page2);
                posX = rect.getLowerLeftX() + margin;
                posY = rect.getUpperRightY() - margin - 25;
            }
            printTicket(posX, posY, document, cos, ticketData.get(j));
            posY -= intraTicket;
        }
        //Ultima linea orizzontale
        cos.drawLine(posX - 30, posY + 30, posX + 470, posY + 30);

        // close the content stream for page 2
        cos.close();

        // Save the results and ensure that the document is properly closed:
        document.save(output);
        document.close();

        return output;
    }

    private void addQrCode(float posX, float posY, String message, PDDocument document, PDPageContentStream cos, float scale) {
        try {
            byte[] bary = QrCodeCreator.doSOmething(message);

            InputStream in = new ByteArrayInputStream(bary);
            BufferedImage buffImage = ImageIO.read(in);

            PDXObjectImage ximage = new PDJpeg(document, buffImage);

            cos.drawXObject(ximage, posX, posY, ximage.getWidth() * scale, ximage.getHeight() * scale);

        } catch (FileNotFoundException fnfex) {
            System.out.println("No QRCode for you");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addImage(float posX, float posY, String imagePath, PDDocument document, PDPageContentStream cos, float scale, ServletContext context) {
        try {

            InputStream image = context.getResourceAsStream(imagePath);
            BufferedImage bi = ImageIO.read(image);
            PDXObjectImage ximage = new PDJpeg(document, bi);

            cos.drawXObject(ximage, posX, posY, ximage.getWidth() * scale, ximage.getHeight() * scale);

        } catch (FileNotFoundException fnfex) {
            System.out.println("No Image for you");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printHeader(float posX, float posY, PDDocument document, PDPageContentStream cos, ServletContext context) throws IOException {

        float relX = posX, relY = posY;
        float lineHeight = 12;

        String logoPath = "/img/logoTotale2.png";
        addImage(relX, relY, logoPath, document, cos, 0.1f, context);

        relX += 200;
        relY += 30;

        cos.beginText();
        cos.setFont(fontPlain, 15);
        cos.moveTextPositionByAmount(relX, relY);
        cos.drawString("Let's Moovie");
        cos.endText();

        relY -= lineHeight;

        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(relX, relY);
        cos.drawString("Telefono: +39 0461 382375   Email: info@lets-moovie.tk ");
        cos.endText();

        relY -= lineHeight;

        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(relX, relY);
        cos.drawString("Indirizzo: Via Prato Bovino 2, Castelnovo di Sotto RE");
        cos.endText();

    }

    private void printTicket(float posX, float posY, PDDocument document, PDPageContentStream cos, TicketData td) throws IOException {

        float relX = posX, relY = posY;
        float lineHeight = 18;

        //orizzontale
        cos.drawLine(relX - 30, relY + 30, relX + 470, relY + 30);
        //verticali
        cos.drawLine(relX - 30, relY + 25, relX - 30, relY - 65);
        cos.drawLine(relX + 470, relY + 25, relX + 470, relY - 65);

        cos.beginText();
        cos.setFont(fontPlain, 15);
        cos.moveTextPositionByAmount(relX, relY);
        cos.drawString(td.getFilm_title());
        cos.endText();

        relY -= lineHeight;
        relX += 20;

        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(relX, relY);
        cos.drawString("Proiezione: " + td.getShowDate() + "   Ora: " + td.getShowTime());
        cos.endText();

        relY -= lineHeight;

        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(relX, relY);
        cos.drawString("Fila: " + td.getS_row() + "   Colonna: " + td.getS_column());
        cos.endText();

        relY -= lineHeight;

        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(relX, relY);
        cos.drawString("Biglietto: " + td.getTicketType() + "   Prezzo: " + td.getPrice());
        cos.endText();

        //codice:  sala,prezzo,tipo biglietto,film,data e ora
        String newCode = td.getShowRoom() + " " + td.getTicketType() + " " + td.getPrice() + " " + td.getFilm_title() + " " + td.getShowDate() + " " + td.getShowTime();
        addQrCode(posX + 350, relY, newCode, document, cos, 0.6f);

    }
}

