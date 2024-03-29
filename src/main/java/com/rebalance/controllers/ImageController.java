package com.rebalance.controllers;

import com.rebalance.entities.Image;
import com.rebalance.exceptions.InvalidRequestException;
import com.rebalance.servises.ExpenseService;
import com.rebalance.servises.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class ImageController {

    private final ImageService imageService;
    private final ExpenseService expenseService;

    @Autowired
    public ImageController(ImageService imageService, ExpenseService expenseService) {
        this.imageService = imageService;
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses/{globalId}/image")
    public ResponseEntity<Map<String, String>> getImageByGlobalId(@PathVariable("globalId") long globalId) {
        String base64Image = imageService.getImageByGlobalId(globalId);
        Map<String, String> response = new HashMap<>();
        response.put("image", base64Image);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/expenses/{globalId}/icon")
    public ResponseEntity<Map<String, String>> getImageIconByGlobalId(@PathVariable("globalId") long globalId) {
        String base64Image = imageService.getImageIconByGlobalId(globalId);
        Map<String, String> response = new HashMap<>();
        response.put("image", base64Image);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/expenses/{globalId}/image")
    public ResponseEntity<HttpStatus> addImageToExpense(@PathVariable("globalId") long globalId, @RequestBody Map<String, String> requestBody) {
        if (!requestBody.containsKey("image")) {
            throw new InvalidRequestException("Request body should have \"image\" field");
        }
        expenseService.throwExceptionIfExpensesWithGlobalIdNotFound(globalId);
        String base64Image = requestBody.get("image");
        imageService.saveImage(base64Image, globalId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/expenses/{globalId}/image")
    public ResponseEntity<HttpStatus> deleteImageByGlobalId(@PathVariable("globalId") long globalId) {
        imageService.deleteImageByGlobalId(globalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/expenses/{globalId}/image")
    public ResponseEntity<Image> updateImageByGlobalId(@PathVariable("globalId") long globalId, @RequestBody Map<String, String> requestBody) {
        imageService.throwExceptionIfNotExistsById(globalId);
        if (!requestBody.containsKey("image")) {
            throw new InvalidRequestException("Request body should have \"image\" field");
        }
        String base64Image = requestBody.get("image");
        imageService.updateImage(globalId, base64Image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
