package json.payments;

import net.glxn.qrgen.image.ImageType;
import net.glxn.qrgen.QRCode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by etrunon on 23/07/15.
 */
public class QrCodeCreator {

    public ByteArrayOutputStream doSOmething(String toBeCodified) {
        return QRCode.from(toBeCodified).to(ImageType.JPG).stream();
    }
}
