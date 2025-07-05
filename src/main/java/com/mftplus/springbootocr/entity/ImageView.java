package com.mftplus.springbootocr.entity;

public class ImageView {
    private String id;
    private String imageBase64;
    private String extractedText;

    public ImageView(String id, String imageBase64, String extractedText) {
        this.id = id;
        this.imageBase64 = imageBase64;
        this.extractedText = extractedText;
    }

    public ImageView(String imageBase64, String extractedText) {
        this.imageBase64 = imageBase64;
        this.extractedText = extractedText;
    }

    public ImageView() {
    }

    public String getId() {
        return id;
    }

    public ImageView setId(String id) {
        this.id = id;
        return this;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public ImageView setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
        return this;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public ImageView setExtractedText(String extractedText) {
        this.extractedText = extractedText;
        return this;
    }
}

