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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // Handle missing or invalid artist
        }
    }

    // Update artwork
    @PutMapping("/{id}")
    public ResponseEntity<Artwork> updateArtwork(@PathVariable Long id, @RequestBody Artwork artworkDetails) {
        if (!artworkService.getArtworkById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        artworkDetails.setId(id);  // Ensure the artwork ID is correctly set
        try {
            Artwork updatedArtwork = artworkService.createOrUpdateArtwork(artworkDetails);
            return new ResponseEntity<>(updatedArtwork, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // Handle missing or invalid artist
        }
    }

    // Delete artwork
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtwork(@PathVariable Long id) {
        if (!artworkService.getArtworkById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        artworkService.deleteArtworkById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get paginated artworks
    @GetMapping("/paginated")
    public ResponseEntity<List<Artwork>> getPaginatedArtworks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Artwork> paginatedArtworks = artworkService.getPaginatedArtworks(page, size).getContent();
        return new ResponseEntity<>(paginatedArtworks, HttpStatus.OK);
    }

    // Get artworks by title
    @GetMapping("/searchByTitle")
    public ResponseEntity<List<Artwork>> searchArtworksByTitle(@RequestParam String title) {
        List<Artwork> artworks = artworkService.findArtworksByTitle(title);
        return new ResponseEntity<>(artworks, HttpStatus.OK);
    }

    // Get artworks by artist ID
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Artwork>> getArtworksByArtistId(@PathVariable Long artistId) {
        List<Artwork> artworks = artworkService.findArtworksByArtistId(artistId);
        return new ResponseEntity<>(artworks, HttpStatus.OK);
    }

    // Get artworks by price range
    @GetMapping("/priceRange")
    public ResponseEntity<List<Artwork>> getArtworksByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ) {
        List<Artwork> artworks = artworkService.findArtworksByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(artworks, HttpStatus.OK);
    }
}
