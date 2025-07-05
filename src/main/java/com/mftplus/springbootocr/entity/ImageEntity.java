package com.mftplus.springbootocr.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "images")
public class ImageEntity {
    @Id
    private String id;
    private byte[] imageData;
    private String extractedText;

    public ImageEntity(String id, byte[] imageData, String extractedText) {
        this.id = id;
        this.imageData = imageData;
        this.extractedText = extractedText;
    }

    public ImageEntity(byte[] imageData, String extractedText) {
        this.imageData = imageData;
        this.extractedText = extractedText;
    }

    public ImageEntity() {
    }

    public String getId() {
        return id;
    }

    public ImageEntity setId(String id) {
        this.id = id;
        return this;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public ImageEntity setImageData(byte[] imageData) {
        this.imageData = imageData;
        return this;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public ImageEntity setExtractedText(String extractedText) {
        this.extractedText = extractedText;
        return this;
    }
}
