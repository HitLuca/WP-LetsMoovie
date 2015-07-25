package json.payments;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 * Created by etrunon on 23/07/15.
 */
public class QrCodeCreator {

    public static byte[] doSOmething(String toBeCodified) {
        return QRCode.from(toBeCodified).to(ImageType.JPG).stream().toByteArray();
    }
}
