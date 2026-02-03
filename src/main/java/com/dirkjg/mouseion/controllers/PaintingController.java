package com.dirkjg.mouseion.controllers;

import com.dirkjg.mouseion.Dtos.PaintingDto;
import com.dirkjg.mouseion.Dtos.PaintingInputDto;
import com.dirkjg.mouseion.services.PaintingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PaintingController {

    private final PaintingService paintingService;

    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
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
    @PatchMapping("/painting/{id}")
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
}
