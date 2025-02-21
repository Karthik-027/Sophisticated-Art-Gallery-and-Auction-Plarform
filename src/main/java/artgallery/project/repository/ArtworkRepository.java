package artgallery.project.repository;

import artgallery.project.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    // Find artworks by title (case-insensitive)
    @Query("SELECT a FROM Artwork a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Artwork> findArtworksByTitle(String title);

    // Find artworks by artist ID
    @Query("SELECT a FROM Artwork a WHERE a.artistId = :artistId")
    List<Artwork> findArtworksByArtistId(Long artistId);

    // Find artworks within a price range
    @Query("SELECT a FROM Artwork a WHERE a.price BETWEEN :minPrice AND :maxPrice")
    List<Artwork> findArtworksByPriceRange(double minPrice, double maxPrice);
}
