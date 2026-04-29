package com.dirkjg.mouseion.controllers;

import com.dirkjg.mouseion.Dtos.PaintingDto;
import com.dirkjg.mouseion.Dtos.PaintingInputDto;
import com.dirkjg.mouseion.models.Painting;
import com.dirkjg.mouseion.services.FileService;
import com.dirkjg.mouseion.services.PaintingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class PaintingController {

    private final PaintingService paintingService;
    private final FileService fileService;

    public PaintingController(PaintingService paintingService, FileService fileService) {
        this.paintingService = paintingService;
        this.fileService = fileService;
    }

    // Get all paintings + filter-function
    @GetMapping("/paintings")
    public ResponseEntity<List<PaintingDto>> getAllPaintings(
            @RequestParam(value = "title", required = false) Optional<String> title,
            @RequestParam(value = "historicalPeriodId", required = false) Optional<Long> historicalPeriodId,
            @RequestParam(value = "characteristicAspectId", required = false) Optional<Long> characteristicAspectId
    ) {

        List<PaintingDto> dtos = paintingService.getFilteredPaintings(
                title,
                historicalPeriodId,
                characteristicAspectId
        );

        return ResponseEntity.ok().body(dtos);

    }

    // Get een painting by id
    @GetMapping("/paintings/{id}")
    public ResponseEntity<PaintingDto> getPainting(@PathVariable("id")Long id) {

        PaintingDto painting = paintingService.getPaintingById(id);

        return ResponseEntity.ok().body(painting);
    }

    // Post een nieuwe painting
    @PostMapping("/painting")
    public ResponseEntity<PaintingDto> addPainting(@Valid @RequestBody PaintingInputDto paintingInputDto) {

        PaintingDto dto = paintingService.addPainting(paintingInputDto);

        return ResponseEntity.created(null).body(dto);

    }

    // Verwijder een painting by id
    @DeleteMapping("/paintings/{id}")
    public ResponseEntity<Object> deletePainting(@PathVariable Long id) {

        paintingService.deletePainting(id);

        return ResponseEntity.noContent().build();

    }

    // Volledige update van een painting
    @PutMapping("/paintings/{id}")
    public ResponseEntity<PaintingDto> updatePainting(
            @PathVariable Long id,
            @Valid @RequestBody PaintingInputDto newPainting
    ) {
        PaintingDto dto = paintingService.updatePainting(id, newPainting);
        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/paintings/{id}")
    public ResponseEntity<PaintingDto> updatePartialPainting(
            @PathVariable Long id,
            @RequestBody PaintingInputDto newPainting) {

        PaintingDto dto = paintingService.updatePartialPainting(id, newPainting);

        return ResponseEntity.ok().body(dto);
   }

    @PutMapping("/paintings/{id}/educationContent/{educationContentId}")
    public void assignEducationContentToPainting(
            @PathVariable("id") Long id,
            @PathVariable Long educationContentId) {
        paintingService.assignEducationContentToPainting(id, educationContentId);
    }

    @PutMapping("/paintings/{id}/painter/{painterId}")
    public void assignPainterToPainting(
            @PathVariable("id") Long id,
            @PathVariable Long painterId) {
        paintingService.assignPainterToPainting(id, painterId);
    }

    @PutMapping("/paintings/{id}/characteristicAspect/{aspectId}")
    public void assignCharacteristicAspectToPainting(
            @PathVariable("id") Long id,
            @PathVariable Long aspectId) {
        paintingService.assignCharacteristicAspectToPainting(id, aspectId);
    }

    @PostMapping("/paintings/{id}/image")
    public ResponseEntity<Painting> addImageToPainting(@PathVariable("id") Long paintingNumber,
                                                       @RequestBody MultipartFile file)
    throws IOException {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/paintings/")
                .path(Objects.requireNonNull(paintingNumber.toString()))
                .path("/image")
                .toUriString();
        String fileName = fileService.storeFile(file);
        Painting painting = paintingService.assignImageToPainting(fileName, paintingNumber);

        return ResponseEntity.created(URI.create(url)).body(painting);
    }

    @GetMapping("/paintings/{id}/image")
    public ResponseEntity<Resource> getPaintingImage(@PathVariable("id") Long paintingId, HttpServletRequest request) {
        Resource resource = paintingService.getImageFromPainting(paintingId);

        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }

}
