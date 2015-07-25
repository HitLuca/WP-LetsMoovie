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
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 23/07/15.
 */
public class PdfTicketCreator {

    PDRectangle rect;
    int line = 0;
    String pathResource;
    private PDFont fontPlain;

    public ByteArrayOutputStream createPdf(List<TicketData> ticketData, String pathResource) throws Exception {

        this.pathResource = pathResource;

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
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream cos = new PDPageContentStream(document, page1);

        TicketData td = new TicketData("The final Project", "2015-07-24", "19:48", "8", "9", "LaPaladinaDellaGiustizia99", "s5f3d7j9H5gdD3uhT5");
        TicketData td1 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "10", "Etrunon", "s5f3d7j9H5gdD3uhT5");
        TicketData td2 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "11", "HitLuca", "s5f3d7j9H5gdD3uhT5");
        TicketData td3 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "12", "M4RC0SX", "s5f3d7j9H5gdD3uhT5");
        TicketData td4 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "13", "Mion00", "s5f3d7j9H5gdD3uhT5");
        TicketData td5 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "14", "Fuffaknight", "s5f3d7j9H5gdD3uhT5");
        TicketData td6 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "9", "LaPaladinaDellaGiustizia99", "s5f3d7j9H5gdD3uhT5");
        TicketData td7 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "10", "Etrunon", "s5f3d7j9H5gdD3uhT5");
        TicketData td8 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "11", "HitLuca", "s5f3d7j9H5gdD3uhT5");
        TicketData td9 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "12", "M4RC0SX", "s5f3d7j9H5gdD3uhT5");
        TicketData td10 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "13", "Mion00", "s5f3d7j9H5gdD3uhT5");
        TicketData td11 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "14", "Fuffaknight", "s5f3d7j9H5gdD3uhT5");
        TicketData td12 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "9", "LaPaladinaDellaGiustizia99", "s5f3d7j9H5gdD3uhT5");
        TicketData td13 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "10", "Etrunon", "s5f3d7j9H5gdD3uhT5");
        TicketData td14 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "11", "HitLuca", "s5f3d7j9H5gdD3uhT5");
        TicketData td15 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "12", "M4RC0SX", "s5f3d7j9H5gdD3uhT5");
        TicketData td16 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "13", "Mion00", "s5f3d7j9H5gdD3uhT5");
        TicketData td17 = new TicketData("The final Project", "2015-07-24", "19:48", "8", "14", "Fuffaknight", "s5f3d7j9H5gdD3uhT5");

        List<TicketData> tdl = new ArrayList<>();
        tdl.add(td);
        tdl.add(td1);
        tdl.add(td2);
        tdl.add(td3);
        tdl.add(td4);
        tdl.add(td5);
        tdl.add(td6);
        tdl.add(td7);
        tdl.add(td8);
        tdl.add(td9);
        tdl.add(td10);
        tdl.add(td11);
        tdl.add(td12);
        tdl.add(td13);
        tdl.add(td14);
        tdl.add(td15);
        tdl.add(td16);
        tdl.add(td17);

        float margin = 50f, intraTicket = 100;
        float posX = rect.getLowerLeftX() + margin, posY = rect.getUpperRightY() - margin - 25;
        printHeader(posX, posY, document, cos);
        posY -= 35;

        cos.beginText();
        cos.setFont(fontPlain, 15);
        cos.moveTextPositionByAmount(posX, posY);
        cos.drawString("Codice Prenotazione:  " + td.getCode());
        cos.endText();

        posY -= 50;

        for (int j = 0; j < tdl.size(); j++) {
            if (j % 7 == 6) {
                // Make sure that the content stream is closed:
                cos.close();
                PDPage page2 = new PDPage(PDPage.PAGE_SIZE_A4);
                document.addPage(page2);
                cos = new PDPageContentStream(document, page2);
                posX = rect.getLowerLeftX() + margin;
                posY = rect.getUpperRightY() - margin - 25;
            }
            printTicket(posX, posY, document, cos, tdl.get(j));
            posY -= intraTicket;
        }

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

    private void addImage(float posX, float posY, String imagePath, PDDocument document, PDPageContentStream cos, float scale) {
        try {

            File image = new File(imagePath);

            BufferedImage bi = ImageIO.read(image);
            PDXObjectImage ximage = new PDJpeg(document, bi);

            cos.drawXObject(ximage, posX, posY, ximage.getWidth() * scale, ximage.getHeight() * scale);

        } catch (FileNotFoundException fnfex) {
            System.out.println("No QRCode for you");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printHeader(float posX, float posY, PDDocument document, PDPageContentStream cos) throws IOException {

        float relX = posX, relY = posY;
        float lineHeight = 12;

        String logoPath = pathResource.concat("/img/logoTotale2.png");
        addImage(relX, relY, logoPath, document, cos, 0.1f);

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
        cos.drawString("Email: info@lets-moovie.tk");
        cos.endText();

    }

    private void printTicket(float posX, float posY, PDDocument document, PDPageContentStream cos, TicketData td) throws IOException {

        float relX = posX, relY = posY;
        float lineHeight = 18;

        cos.drawLine(relX - 30, relY + 30, relX + 470, relY + 30);
        cos.drawLine(relX - 30, relY + 25, relX - 30, relY - 65);
        cos.drawLine(relX + 470, relY + 25, relX + 470, relY - 65);

        System.out.println(rect.getWidth());

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
        cos.drawString("Proiezione: " + td.getDate() + "   Ora: " + td.getTime());
        cos.endText();

        relY -= lineHeight;

        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(relX, relY);
        cos.drawString("Fila: " + td.getS_row() + "   Colonna: " + td.getS_column());
        cos.endText();

        String newCode = td.getUsername() + " Riga: " + td.getS_row() + " Colonna: " + td.getS_column() + " Code: " + td.getCode();
        addQrCode(posX + 350, relY - 30, newCode, document, cos, 0.8f);

    }
}

