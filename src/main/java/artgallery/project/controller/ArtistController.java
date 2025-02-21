package artgallery.project.controller;

import artgallery.project.model.Artist;
import artgallery.project.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
// import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // Get all artists
    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    // Get artist by ID
    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        Optional<Artist> artist = artistService.getArtistById(id);
        return artist.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new artist
    @PostMapping
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.createOrUpdateArtist(artist);
    }

    // Update artist by ID
    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody Artist artistDetails) {
        Optional<Artist> existingArtist = artistService.getArtistById(id);
        if (existingArtist.isPresent()) {
            artistDetails.setId(id);
            Artist updatedArtist = artistService.createOrUpdateArtist(artistDetails);
            return ResponseEntity.ok(updatedArtist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete artist by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        Optional<Artist> existingArtist = artistService.getArtistById(id);
        if (existingArtist.isPresent()) {
            artistService.deleteArtistById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get paginated and sorted list of artists
    @GetMapping("/paginated")
    public ResponseEntity<List<Artist>> getPaginatedArtists(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "name") String sortBy,
    @RequestParam(defaultValue = "asc") String direction
    ) {
    List<Artist> artists = artistService.getPaginatedArtists(page, size, sortBy, direction).getContent();
    return new ResponseEntity<>(artists, HttpStatus.OK);
    }   


    // Find artists by name
    @GetMapping("/search")
    public List<Artist> searchArtistsByName(@RequestParam String name) {
        return artistService.findArtistsByName(name);
    }

    // Find artists by a keyword in their bio
    @GetMapping("/searchByBio")
    public List<Artist> searchArtistsByBio(@RequestParam String keyword) {
        return artistService.searchArtistsByBioKeyword(keyword);
    }

    // Delete artists by name
    @DeleteMapping("/deleteByName")
    public ResponseEntity<Void> deleteArtistByName(@RequestParam String name) {
        artistService.deleteArtistsByName(name);
        return ResponseEntity.noContent().build();
    }
}
