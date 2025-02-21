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

    // Delete artwork by ID
    public void deleteArtworkById(Long id) {
        artworkRepository.deleteById(id);
    }

    // Get paginated artworks
    public Page<Artwork> getPaginatedArtworks(int page, int size) {
        return artworkRepository.findAll(PageRequest.of(page, size));
    }

    // JPQL: Find artworks by title
    public List<Artwork> findArtworksByTitle(String title) {
        return artworkRepository.findArtworksByTitle(title);
    }

    // JPQL: Find artworks by artist ID
    public List<Artwork> findArtworksByArtistId(Long artistId) {
        return artworkRepository.findArtworksByArtistId(artistId);
    }

    // JPQL: Find artworks within a price range
    public List<Artwork> findArtworksByPriceRange(double minPrice, double maxPrice) {
        return artworkRepository.findArtworksByPriceRange(minPrice, maxPrice);
    }
    
}
