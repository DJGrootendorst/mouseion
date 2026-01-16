package com.dirkjg.mouseion.controllers;

import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.Painting;
import com.dirkjg.mouseion.repositories.PaintingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PaintingController {

    private final PaintingRepository paintingRepository;

    // Constructor injectie
    public PaintingController(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    @GetMapping("/paintings")
    public ResponseEntity<List<Painting>> getAllPaintings(@RequestParam(value = "title", required = false) String title) {

        List<Painting> paintings;

        if (title == null) {
            // Vul de paintings lijst met alle paintings
            paintings = paintingRepository.findAll();
        } else {
            // Vul de paintingslijst met alle paintings die een bepaalde title hebben
            paintings = paintingRepository.findAllPaintingsByTitleEqualsIgnoreCase(title);
        }

        // Return een String met een 200 status
        return ResponseEntity.ok().body(paintings);
    }

    // Return 1 painting met een specifiek id
    @GetMapping("/paintings/{id}")
    public ResponseEntity<Painting> getPainting(@PathVariable("id") Long id) {

        Optional<Painting> painting = paintingRepository.findById(id);

        // Check op de optional empty is. Het tegenovergestelde alternatief is "painting.IsPresent()"
        if (painting.isEmpty()) {

            // Als er geen painting in de optional staat, roepen we hier de RecordNotFoundException constructor aan met message.
            throw new RecordNotFoundException("No painting found with id: " + id);

        } else {
            // Als er wel een painting in de optional staat, dan halen we die uit de optional met de get-methode.
            Painting painting1 = painting.get();

            // Return een String met een 200 status
            return ResponseEntity.ok().body(painting1);
        }
    }

    // Hier geef ik een painting mee in de parameter. Waarbij het JSON object exact overeen moet komen met het Painting object.
    @PostMapping("/paintings")
    public ResponseEntity<Painting> addPainting(@RequestBody Painting painting) {

        // Sla de nieuwe painting in de database op met de save-methode van de repository
        Painting savedPainting = paintingRepository.save(painting);

        // Return de gemaakte painting en een 201 status
        return ResponseEntity.status(201).body(savedPainting);
    }

    @DeleteMapping("/paintings/{id}")
    public ResponseEntity<Object> deletePainting(@PathVariable("id") Long id) {

        // Verwijder de painting met het opgegeven id uit de database
        paintingRepository.deleteById(id);

        // Return een 204 status
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/paintings/{id}")
    public ResponseEntity<Painting> updatePainting(@PathVariable Long id, @RequestBody Painting newPainting) {

        // Haal de aan te passen painting uit de database met het gegeven id
        Optional<Painting> painting = paintingRepository.findById(id);

        // Als eerste checken of de aan te passen painting wel in de database bestaat
        if (painting.isEmpty()) {
            throw new RecordNotFoundException("No painting found with id: " + id);
        } else {
            // Verander alle waardes van de painting uit de database naar de waardes van de painting uit de parameters.
            // Behalve de id. Als je de id veranderd, dan wordt er een nieuw object gemaakt in de database.
            Painting painting1 = painting.get();
            painting1.setTitle(newPainting.getTitle());
            painting1.setYear(newPainting.getYear());
            painting1.setImage(newPainting.getImage());
            // Sla de gewijzigde waarden op in de database onder dezelfde id.
            Painting returnPainting = paintingRepository.save(painting1);
            // Return de nieuwe versie van deze painting en een 200 code.
            return ResponseEntity.ok().body(returnPainting);
        }
    }

    // VERANTWOORDINGSDOCUMENT TECHNISCHE KEUZE 1
    // Het verschil tussen een patch en een put methode is dat een put een compleet object update,
    // waar een patch een gedeeltelijk object kan updaten. Dit is waardevol omdat
    // alleen gewijzigde velden worden aangepast zonder het volledige Painting-object te overschrijven.
    // Bijvoorbeeld alleen de titel van de Painting: "Mona Lisa (restauratie)" in plaats van "Mona Lisa"
    @PatchMapping("/painting/{id}")
    public ResponseEntity<Painting> updatePartialPainting(@PathVariable Long id, @RequestBody Painting newPainting) {
        Optional<Painting> painting = paintingRepository.findById(id);

        if (painting.isEmpty()) {
            throw new RecordNotFoundException("No painting found with id: " + id);

        } else {

            Painting painting1 = painting.get();
            if (newPainting.getTitle() != null) {
                painting1.setTitle(newPainting.getTitle());
            }

            // VERANTWOORDINGSDOCUMENT REFLECTIE 1:
            // In het model painting had ik eerst "private int year" staan. Maar een "int" kan niet null zijn, een "integer" kan dat wel.
            // "Year" was in "Painting" dus eerste gedefinieerd als een primitieve "int".
            // Een "Integer" is een object-wrapper van "int" en kan dus wel "null" zijn en is daarom geschikt voor optionele waarden zoals bij "patch".
            // "Null" betekent "geen waarde/niet aanwezig" en kan alleen bij objecten, niet bij primitieve types zoals "int".
            if (newPainting.getYear() != null) {
                painting1.setYear(newPainting.getYear());
            }
            if (newPainting.getImage() != null) {
                painting1.setImage(newPainting.getImage());
            }

            Painting returnPainting = paintingRepository.save(painting1);
            return ResponseEntity.ok().body(returnPainting);
        }
    }
}
