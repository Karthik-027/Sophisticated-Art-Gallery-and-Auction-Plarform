package artgallery.project.service;

import artgallery.project.model.Artwork;
import artgallery.project.model.Artist;
import artgallery.project.repository.ArtworkRepository;
import jakarta.transaction.Transactional;
import artgallery.project.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private ArtistRepository artistRepository; // Inject ArtistRepository

    // Get all artworks
    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    // Get artwork by ID
    public Optional<Artwork> getArtworkById(Long id) {
        return artworkRepository.findById(id);
    }

    // Create or update artwork with artist mapping
    @Transactional
    public Artwork createOrUpdateArtwork(Artwork artwork) {
        if (artwork.getArtist() == null || artwork.getArtist().getId() == null) {
            throw new IllegalArgumentException("Artist ID cannot be null");
        }

        Artist artist = artistRepository.findById(artwork.getArtist().getId())
                .orElseThrow(() -> new IllegalArgumentException("Artist not found with ID: " + artwork.getArtist().getId()));

        artwork.setArtist(artist); // Set the mapped artist
        return artworkRepository.save(artwork);
    }

    // Update artwork by ID
    public Artwork updateArtworkById(Long id, Artwork updatedArtwork) {
        return artworkRepository.findById(id).map(existingArtwork -> {
            existingArtwork.setTitle(updatedArtwork.getTitle());
            existingArtwork.setDescription(updatedArtwork.getDescription());
            existingArtwork.setPrice(updatedArtwork.getPrice());

            if (updatedArtwork.getArtist() != null && updatedArtwork.getArtist().getId() != null) {
                Artist artist = artistRepository.findById(updatedArtwork.getArtist().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Artist not found with ID: " + updatedArtwork.getArtist().getId()));
                existingArtwork.setArtist(artist);
            }

            return artworkRepository.save(existingArtwork);
        }).orElseThrow(() -> new IllegalArgumentException("Artwork with ID " + id + " not found."));
    }

    // Delete artwork by ID
    public boolean deleteArtworkById(Long id) {
        if (artworkRepository.existsById(id)) {
            artworkRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Delete artwork by title
    public void deleteArtworkByTitle(String title) {
        artworkRepository.deleteArtworkByTitle(title);
    }

    // Delete artwork by description
    public void deleteArtworkByDescription(String description) {
        artworkRepository.deleteArtworkByDescription(description);
    }

    // Update artwork by title
    public Artwork updateArtworkByTitle(String title, Artwork updatedArtwork) {
        Artist artist = artistRepository.findById(updatedArtwork.getArtist().getId())
                .orElseThrow(() -> new IllegalArgumentException("Artist not found with ID: " + updatedArtwork.getArtist().getId()));

        int rowsAffected = artworkRepository.updateArtworkByTitle(title, updatedArtwork.getDescription(), updatedArtwork.getPrice(), artist);

        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Artwork with title '" + title + "' not found.");
        }

        return updatedArtwork;
    }

    // Update artwork by description
    public Artwork updateArtworkByDescription(String description, Artwork updatedArtwork) {
        Artist artist = artistRepository.findById(updatedArtwork.getArtist().getId())
                .orElseThrow(() -> new IllegalArgumentException("Artist not found with ID: " + updatedArtwork.getArtist().getId()));

        int rowsAffected = artworkRepository.updateArtworkByDescription(description, updatedArtwork.getTitle(), updatedArtwork.getPrice(), artist);

        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Artwork with description '" + description + "' not found.");
        }

        return updatedArtwork;
    }

    // Paginated artworks
    public Page<Artwork> getPaginatedArtworks(int page, int size) {
        return artworkRepository.findAll(PageRequest.of(page, size));
    }

    // Find artworks by title
    public List<Artwork> findArtworksByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return artworkRepository.findArtworksByTitle(title, pageable);
    }
    
    // Find artworks by artist
    public List<Artwork> findArtworksByArtist(Long artistId, int page, int size) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found with ID: " + artistId));
        Pageable pageable = PageRequest.of(page, size);
        return artworkRepository.findByArtist(artist, pageable);
    }
    
    public List<Artwork> findArtworksByArtistId(Long artistId) {
        return artworkRepository.findByArtistId(artistId);
    }
    
    // Find artworks by price range
    public List<Artwork> findArtworksByPriceRange(double minPrice, double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return artworkRepository.findArtworksByPriceRange(minPrice, maxPrice, pageable);
    }
}