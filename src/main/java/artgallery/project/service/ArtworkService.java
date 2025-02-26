package artgallery.project.service;

import artgallery.project.model.Artwork;
import artgallery.project.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    // Get all artworks
    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    // Get artwork by ID
    public Optional<Artwork> getArtworkById(Long id) {
        return artworkRepository.findById(id);
    }

    // Create or update artwork
    public Artwork createOrUpdateArtwork(Artwork artwork) {
        if (artwork.getArtistId() == null) {
            throw new IllegalArgumentException("Artist ID cannot be null");
        }
        return artworkRepository.save(artwork);
    }

    // Update artwork by ID
    public Artwork updateArtworkById(Long id, Artwork updatedArtwork) {
        return artworkRepository.findById(id).map(existingArtwork -> {
            existingArtwork.setTitle(updatedArtwork.getTitle());
            existingArtwork.setDescription(updatedArtwork.getDescription());
            existingArtwork.setPrice(updatedArtwork.getPrice());
            existingArtwork.setArtistId(updatedArtwork.getArtistId());
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

    // Existing update and delete operations
    public void deleteArtworkByTitle(String title) {
        artworkRepository.deleteArtworkByTitle(title);
    }

    public void deleteArtworkByDescription(String description) {
        artworkRepository.deleteArtworkByDescription(description);
    }

    public Artwork updateArtworkByTitle(String title, Artwork updatedArtwork) {
        int rowsAffected = artworkRepository.updateArtworkByTitle(title, updatedArtwork.getDescription(), updatedArtwork.getPrice(), updatedArtwork.getArtistId());

        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Artwork with title '" + title + "' not found.");
        }

        return updatedArtwork;
    }

    public Artwork updateArtworkByDescription(String description, Artwork updatedArtwork) {
        int rowsAffected = artworkRepository.updateArtworkByDescription(description, updatedArtwork.getTitle(), updatedArtwork.getPrice(), updatedArtwork.getArtistId());

        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Artwork with description '" + description + "' not found.");
        }

        return updatedArtwork;
    }

    public Page<Artwork> getPaginatedArtworks(int page, int size) {
        return artworkRepository.findAll(PageRequest.of(page, size));
    }

    public List<Artwork> findArtworksByTitle(String title) {
        return artworkRepository.findArtworksByTitle(title);
    }

    public List<Artwork> findArtworksByArtistId(Long artistId) {
        return artworkRepository.findArtworksByArtistId(artistId);
    }

    public List<Artwork> findArtworksByPriceRange(double minPrice, double maxPrice) {
        return artworkRepository.findArtworksByPriceRange(minPrice, maxPrice);
    }
}
