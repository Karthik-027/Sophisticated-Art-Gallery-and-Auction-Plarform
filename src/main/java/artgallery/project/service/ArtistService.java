package artgallery.project.service;

import artgallery.project.model.Artist;
import artgallery.project.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    // Retrieve all artists
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    // Retrieve artist by ID
    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    // Create or update an artist
    public Artist createOrUpdateArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    // Delete artist by ID
    public void deleteArtistById(Long id) {
        artistRepository.deleteById(id);
    }

    // Get paginated and sorted list of artists
    public Page<Artist> getPaginatedArtists(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return artistRepository.findAll(PageRequest.of(page, size, sort));
    }

    // Search for artists by name (case-insensitive)
    public List<Artist> findArtistsByName(String name) {
        return artistRepository.findByNameIgnoreCase(name);
    }

    // Search for artists with a specific keyword in their bio
    public List<Artist> searchArtistsByBioKeyword(String keyword) {
        return artistRepository.findByBioContaining(keyword);
    }

    // Delete artists by name
    public void deleteArtistsByName(String name) {
        artistRepository.deleteByName(name);
    }
}
