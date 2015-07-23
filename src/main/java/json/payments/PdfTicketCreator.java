package json.payments;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import com.sun.javafx.iio.ImageStorage;
import json.tickets.TicketData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

/**
 * Created by etrunon on 23/07/15.
 */
public class PdfTicketCreator {

    public ByteArrayOutputStream createPdf(List<TicketData> ticketData) throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);
        // PDPage.PAGE_SIZE_LETTER is also possible
        PDRectangle rect = page1.getMediaBox();
        // rect can be used to get the page width and height
        document.addPage(page1);

        // Create a new font object selecting one of the PDF base fonts
        PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream cos = new PDPageContentStream(document, page1);

        int line = 0;

        // Define a text content stream using the selected font, move the cursor and draw some text
        cos.beginText();
        cos.setFont(fontPlain, 12);
        cos.moveTextPositionByAmount(50, rect.getHeight() - 50 * (++line));
        cos.drawString("Biglietto SuperBovino del cinema Let'sMoovie!");
        cos.endText();

        // add a qrCode
        addQrCode(1, 1, "Il mio messaggio è molto lungo e alf4Num3R1C0", document, cos);

/*
        // Make sure that the content stream is closed:
        cos.close();
        PDPage page2 = new PDPage(PDPage.PAGE_SIZE_A4);
        document.addPage(page2);
        cos = new PDPageContentStream(document, page2);
*/

/*
        // draw a red box in the lower left hand corner
        cos.setNonStrokingColor(Color.RED);
        cos.fillRect(10, 10, 100, 100);
*/

/*
        // add two lines of different widths
        cos.setLineWidth(1);
        cos.addLine(200, 250, 400, 250);
        cos.closeAndStroke();
        cos.setLineWidth(5);
        cos.addLine(200, 300, 400, 300);
        cos.closeAndStroke();
*/


        // close the content stream for page 2
        cos.close();

        // Save the results and ensure that the document is properly closed:
        document.save(output);
        document.close();

        return output;
    }

    private void addQrCode(float posX, float posY, String message, PDDocument document, PDPageContentStream cos) {
        try {
            byte[] bary = QrCodeCreator.doSOmething(message);

            InputStream in = new ByteArrayInputStream(bary);
            BufferedImage buffImage = ImageIO.read(in);

            PDXObjectImage ximage = new PDJpeg(document, buffImage);

            float scale = 1f; // alter this value to set the image size
            cos.drawXObject(ximage, 100, 400, ximage.getWidth() * scale, ximage.getHeight() * scale);

        } catch (FileNotFoundException fnfex) {
            System.out.println("No QRCode for you");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addImage(float posX, float posY, String imagePath, PDDocument document, PDPageContentStream cos) {
        try {
            byte[] bary = null;

            InputStream in = new ByteArrayInputStream(bary);
            BufferedImage buffImage = ImageIO.read(in);

            PDXObjectImage ximage = new PDJpeg(document, buffImage);

            float scale = 1f; // alter this value to set the image size
            cos.drawXObject(ximage, 100, 400, ximage.getWidth() * scale, ximage.getHeight() * scale);

        } catch (FileNotFoundException fnfex) {
            System.out.println("No QRCode for you");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

