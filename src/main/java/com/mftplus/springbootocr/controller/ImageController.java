package com.mftplus.springbootocr.controller;


import com.mftplus.springbootocr.entity.ImageEntity;
import com.mftplus.springbootocr.entity.ImageView;
import com.mftplus.springbootocr.repository.ImageRepository;
import com.mftplus.springbootocr.service.OcrService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ImageController {
    private final ImageRepository imageRepository;

    private final OcrService ocrService;

    public ImageController(ImageRepository imageRepository, OcrService ocrService) {
        this.imageRepository = imageRepository;
        this.ocrService = ocrService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Upload Photo ");
        return "index";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {
        try {
            byte[] imageBytes = file.getBytes();
            String extractedText = ocrService.extractTextFromImage(imageBytes);
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setImageData(imageBytes);
            imageEntity.setExtractedText(extractedText);
            imageRepository.save(imageEntity);

            model.addAttribute("message", "Successfully Upload");
            model.addAttribute("extractedText", extractedText);

        } catch (Exception e) {
            model.addAttribute("message", "Could not upload the photo :" + e.getMessage());
        }
        return "index";
    }


    @GetMapping("/images")
    public String getAllImages(Model model) {
        List<ImageEntity> images = imageRepository.findAll();

        List<ImageView> imageViews = images.stream()
                .map(image -> new ImageView()
                        .setId(image.getId())
                        .setImageBase64("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(image.getImageData()))
                        .setExtractedText(image.getExtractedText())
                )
                .collect(Collectors.toList());

        model.addAttribute("images", imageViews);
        return "images";
    }

    @GetMapping("image/{id}")
    public String viewImage(@PathVariable String id, Model model) {
        ImageEntity image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid identifier : " + id));
        String imageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(image.getImageData());
        model.addAttribute("image", image);
        model.addAttribute("imageBase64", imageBase64);
        return "image-detail";
    }

    @GetMapping("/image/{id}/delete")
    public String deleteImage(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (imageRepository.existsById(id)) {
            imageRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Successfully deleted");
        } else {
            redirectAttributes.addFlashAttribute("message", "Photo not found");
        }
        return "redirect:/images";
    }


    @GetMapping("image/{id}/download")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String id) {
        ImageEntity image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid identifier  : " + id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image-" + id + ".jpg\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(image.getImageData());
    }
}
