package multimedia;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * Created by Yenny Fung on 14/11/17.
 */
public class  MultimediaUtils {

    public static String transformToPath(String directory, String name) throws MimeTypeException {
        StringBuilder path = new StringBuilder();
        if(directory != null && !directory.isEmpty()) {
            path.append(directory);
            path.append("/");
        }
        path.append(name);
        return path.toString();
    }

    public static String transformToValidPath(String path) throws MimeTypeException {
        if(path != null && !path.isEmpty()) {
            return path.replaceAll(" ", "_").replaceAll("\\W[^/]", "").toLowerCase(); //TODO revisar "[^/]"
        }
        return new String();
    }

    public static String transformToUniquePath(String path) throws MimeTypeException {
        StringBuilder uniqueName = new StringBuilder();
        if(path != null && !path.isEmpty()) {
            uniqueName.append(path);
            uniqueName.append("_");
        }
        uniqueName.append(UUID.randomUUID());
        return uniqueName.toString();
    }

    public static BufferedImage resizeImage(BufferedImage image, int destWidth, int destHeight){
        try{
            int srcWidth = image.getWidth(); //asegurarse que image viene sin MIMEtype de encabezado de lo contrario no puede obtener el tamaño
            int srcHeight = image.getHeight();

            if(srcWidth != destWidth || srcHeight != destHeight) {
                int width, height;

                if (srcWidth <= srcHeight) {
                    width = srcWidth;
                    height = (srcWidth * destHeight) / destWidth;
                } else {
                    width = (srcHeight * destWidth) / destHeight;
                    height = srcHeight;
                }

                int sx1 = srcWidth / 2 - width / 2;
                int sy1 = srcHeight / 2 - height / 2;
                int sx2 = sx1 + width;
                int sy2 = sy1 + height;

                BufferedImage imageCropped = new BufferedImage(width, height, image.getType());
                Graphics2D graphicsCropped = imageCropped.createGraphics();
                graphicsCropped.drawImage(image, 0, 0, width, height, sx1, sy1, sx2, sy2, null);
                graphicsCropped.dispose();

                BufferedImage rescaleImg = new BufferedImage(destWidth, destHeight, image.getType()); //TODO no reescalar si es mas pequeño
                Graphics2D graphicsResized = rescaleImg.createGraphics();
                graphicsResized.drawImage(imageCropped, 0, 0, destWidth, destHeight, null);
                graphicsResized.dispose();
                return rescaleImg;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return image;
    }

    public static String guessExtensionFromMimeType(String mimeType) throws MimeTypeException {
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        MimeType type = allTypes.forName(mimeType);
        String extension = type.getExtension(); // .jpg
        if(!extension.isEmpty()) {
            extension = extension.replaceAll(" ", "_").replaceAll("\\W+", "");
        }
        return extension;
    }

    /*
    * other alternative less secure: http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/7u40-b43/java/net/URLConnection.java#URLConnection.guessContentTypeFromStream%28java.io.InputStream%29
    * other alternative less secure: https://stackoverflow.com/questions/4602060/how-can-i-get-mime-type-of-an-inputstream-of-a-file-that-is-being-uploaded
    */
    public static String guessMIMEtypeFromBytes(byte[] bytes) throws IOException {
        Tika tika = new Tika();
        return tika.detect(bytes);
    }

    public static String guessMIMEtype(File file) throws IOException {
        Tika tika = new Tika();
        return tika.detect(file);
    }

    public static byte[] decodeBase64ToBytes(String dataBase64) throws IOException {
        // data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAfAAAAAXNSR0IArs4c6QAAAARnQU...
        String payload = dataBase64.substring(dataBase64.indexOf(',') + 1);

        //byte[] bytes = Base64.getDecoder().decode(dataBase64); // no permite ciertos caracteres ilegales
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(payload);
        return bytes;
    }

    public static String encodeBytesToBase64(byte[] bytes) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        String dataBase64 = encoder.encode(bytes);
        return dataBase64;
    }

    public static BufferedImage bytesToImage(byte bytes[]) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bis);
        bis.close();
        return image;
    }

    public static File bytesToFile(byte bytes[]) throws IOException{
        File file = File.createTempFile("temp", null);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
        return file;
    }

    public static File imageToFile(BufferedImage image, String formatName) throws IOException{
        File file = File.createTempFile("temp", null);
        ImageIO.write(image, formatName, file);
        return file;
    }
}
