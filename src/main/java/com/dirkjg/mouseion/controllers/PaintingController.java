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

// Verantwoordingsdocument TECHNISCHE KEUZE 1: CONSISTENTE CONTROLLER STRUCTUUR
// Voor de nieuwe controllers, namelijk PainterController, EducationContentController,
// HistoricalPeriodController, CharacteristicAspectController, heb ik ervoor gekozen
// de stijl en structuur overeen te laten komen met de reeds bestaande en geteste
// PaintingController. Dit betekent dat dezelfde opzet wordt gebruikt voor dependency injection,
// CRUD-endpoints, query parameters, ResponseEntity-responses en patch-methoden.
// Deze consistente aanpak zorgt voor overzichtelijke, herbruikbare en onderhoudbare code,
// en minimaliseert de kans op fouten omdat het patroon al eerder in de applicatie succesvol
// is getest.

@RestController
public class PaintingController {

    private final PaintingService paintingService;
    private final FileService fileService;

    public PaintingController(PaintingService paintingService, FileService fileService) {
        this.paintingService = paintingService;
        this.fileService = fileService;
    }

    // Get all paintings
    @GetMapping("/paintings")
    public ResponseEntity<List<PaintingDto>> getAllPaintings(
            @RequestParam(value = "title", required = false) Optional<String> title) {

        List<PaintingDto> dtos;

        if (title.isEmpty()){
            dtos = paintingService.getAllPaintings();
        } else {
            dtos = paintingService.getAllPaintingsByTitle(title.get());
        }

        return ResponseEntity.ok().body(dtos);

    }

    // Get een painting by id
    @GetMapping("/paintings/{id}")
    public ResponseEntity<PaintingDto> getPainting(@PathVariable("id")Long id) {

        PaintingDto painting = paintingService.getPaintingById(id);

        return ResponseEntity.ok().body(painting);
    }

    // Post een nieuwe painting
    @PostMapping("/paintings")
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
    public ResponseEntity<PaintingDto> updatePainting(@PathVariable Long id, @Valid @RequestBody PaintingInputDto newPainting) {

        PaintingDto dto = paintingService.updatePainting(id, newPainting);

        return ResponseEntity.ok().body(dto);

    }

    // VERANTWOORDINGSDOCUMENT TECHNISCHE KEUZE 1
    // Het verschil tussen een patch en een put methode is dat een put een compleet object update,
    // waar een patch een gedeeltelijk object kan updaten. Dit is waardevol omdat
    // alleen gewijzigde velden worden aangepast zonder het volledige Painting-object te overschrijven.
    // Bijvoorbeeld alleen de titel van de Painting: "Mona Lisa (restauratie)" in plaats van "Mona Lisa"
    @PatchMapping("/paintings/{id}")
    public ResponseEntity<PaintingDto> updatePartialPainting(
            @PathVariable Long id,
            @RequestBody PaintingInputDto newPainting) {

        PaintingDto dto = paintingService.updatePartialPainting(id, newPainting);

        return ResponseEntity.ok().body(dto);
   }
        // VERANTWOORDINGSDOCUMENT REFLECTIE 1:
        // In het model painting had ik eerst "private int year" staan. Maar een "int" kan niet null zijn, een "integer" kan dat wel.
        // "Year" was in "Painting" dus eerste gedefinieerd als een primitieve "int".
        // Een "Integer" is een object-wrapper van "int" en kan dus wel "null" zijn en is daarom geschikt voor optionele waarden zoals bij "patch".
        // "Null" betekent "geen waarde/niet aanwezig" en kan alleen bij objecten, niet bij primitieve types zoals "int".

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
