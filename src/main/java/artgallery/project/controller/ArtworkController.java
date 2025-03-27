package artgallery.project.controller;

import artgallery.project.model.Artwork;
import artgallery.project.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artworks")
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    // Get all artworks
    @GetMapping
    public ResponseEntity<List<Artwork>> getAllArtworks() {
        List<Artwork> artworks = artworkService.getAllArtworks();
        return new ResponseEntity<>(artworks, HttpStatus.OK);
    }

    // Get artwork by ID
    @GetMapping("/{id}")
    public ResponseEntity<Artwork> getArtworkById(@PathVariable Long id) {
        Optional<Artwork> artwork = artworkService.getArtworkById(id);
        return artwork.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create new artwork
    @PostMapping
    public ResponseEntity<Artwork> createArtwork(@RequestBody Artwork artwork) {
        try {
            Artwork createdArtwork = artworkService.createOrUpdateArtwork(artwork);
            return new ResponseEntity<>(createdArtwork, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  
        }
    }

    // Update artwork by ID
    @PutMapping("/{id}")
    public ResponseEntity<Artwork> updateArtworkById(@PathVariable Long id, @RequestBody Artwork artworkDetails) {
        try {
            Artwork updatedArtwork = artworkService.updateArtworkById(id, artworkDetails);
            return new ResponseEntity<>(updatedArtwork, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete artwork by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtworkById(@PathVariable Long id) {
        if (artworkService.deleteArtworkById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Existing update and delete operations
    @PutMapping("/updateByTitle")
    public ResponseEntity<Artwork> updateArtworkByTitle(@RequestParam String title, @RequestBody Artwork artworkDetails) {
        try {
            Artwork updatedArtwork = artworkService.updateArtworkByTitle(title, artworkDetails);
            return new ResponseEntity<>(updatedArtwork, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateByDescription")
    public ResponseEntity<Artwork> updateArtworkByDescription(@RequestParam String description, @RequestBody Artwork artworkDetails) {
        try {
            Artwork updatedArtwork = artworkService.updateArtworkByDescription(description, artworkDetails);
            return new ResponseEntity<>(updatedArtwork, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteByTitle")
    public ResponseEntity<Void> deleteArtworkByTitle(@RequestParam String title) {
        artworkService.deleteArtworkByTitle(title);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteByDescription")
    public ResponseEntity<Void> deleteArtworkByDescription(@RequestParam String description) {
        artworkService.deleteArtworkByDescription(description);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get paginated artworks
    @GetMapping("/paginated")
    public ResponseEntity<List<Artwork>> getPaginatedArtworks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Artwork> paginatedArtworks = artworkService.getPaginatedArtworks(page, size).getContent();
        return new ResponseEntity<>(paginatedArtworks, HttpStatus.OK);
    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<List<Artwork>> searchArtworksByTitle(@RequestParam String title,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
    List<Artwork> artworks = artworkService.findArtworksByTitle(title, page, size);
    return new ResponseEntity<>(artworks, HttpStatus.OK);
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Artwork>> getArtworksByArtistId(@PathVariable Long artistId) {
        List<Artwork> artworks = artworkService.findArtworksByArtistId(artistId);
        return new ResponseEntity<>(artworks, HttpStatus.OK);
    }

    @GetMapping("/priceRange")
    public ResponseEntity<List<Artwork>> getArtworksByPriceRange(@RequestParam double minPrice,
                                                             @RequestParam double maxPrice,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
    List<Artwork> artworks = artworkService.findArtworksByPriceRange(minPrice, maxPrice, page, size);
    return new ResponseEntity<>(artworks, HttpStatus.OK);
}
}
