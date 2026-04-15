package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.Painting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaintingRepository extends JpaRepository<Painting, Long> {
    // Met 'EqualsIgnoreCase' kunnen gebruikers zoekopdrachten typen zonder op hoofdletters te letten,
    // dus de zoekopdracht naar het schilderij 'Mona Lisa' of 'mona lisa' maakt geen verschil.
    List<Painting> findAllPaintingsByTitleEqualsIgnoreCase(String title);
}
