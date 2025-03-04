package tn.esprit.utils;
/*
import com.spire.barcode.BarCodeGenerator;
import com.spire.barcode.BarCodeType;
import com.spire.barcode.BarcodeSettings;
import com.spire.barcode.QRCodeECL;

 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class QrCodeGen {

    public static void GenerateQrCode() throws IOException {
        /*
        //Instantiate a BarcodeSettings object
        BarcodeSettings settings = new BarcodeSettings();
        //Set barcode type
        settings.setType(BarCodeType.QR_Code);
        //Set barcode data
        String data = "https://stackoverflow.com/";
        settings.setData(data);
        //Set barcode module width
        settings.setX(2);
        //Set error correction level
        settings.setQRCodeECL(QRCodeECL.M);

        //Set top text
        settings.setTopText("Contrats");
        //Set bottom text
        //settings.setBottomText("Event Name");

        //Set text visibility
        settings.setShowText(false);
        settings.setShowTopText(true);
        settings.setShowBottomText(true);

        //Set border visibility
        settings.hasBorder(false);

        //Instantiate a BarCodeGenerator object based on the specific settings
        BarCodeGenerator barCodeGenerator = new BarCodeGenerator(settings);
        //Generate QR code image
        BufferedImage bufferedImage = barCodeGenerator.generateImage();
        //save the image to a .png file
        ImageIO.write(bufferedImage,"png",new File("C:\\Users\\HP\\OneDrive - ESPRIT\\Bureau\\QR_Codes\\qrcode.png"));

         */
    }

}
