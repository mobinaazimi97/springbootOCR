package com.mftplus.springbootocr.service;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

@Service
public class OcrService {
    public String extractTextFromImage(byte[] imageData) {
        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/tessdata");
            tesseract.setLanguage("eng");
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
            return tesseract.doOCR(image);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return "could not read the text! ";
        }
    }
}
