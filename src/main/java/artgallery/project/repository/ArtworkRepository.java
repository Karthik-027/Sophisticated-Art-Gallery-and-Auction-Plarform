package artgallery.project.repository;

import artgallery.project.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    // Update artwork by title (PUT)
    @Transactional
    @Modifying
    @Query("UPDATE Artwork a SET a.description = :description, a.price = :price, a.artistId = :artistId WHERE LOWER(a.title) = LOWER(:title)")
    int updateArtworkByTitle(String title, String description, double price, Long artistId);

    // Update artwork by description (PUT)
    @Transactional
    @Modifying
    @Query("UPDATE Artwork a SET a.title = :title, a.price = :price, a.artistId = :artistId WHERE LOWER(a.description) = LOWER(:description)")
    int updateArtworkByDescription(String description, String title, double price, Long artistId);

    // Delete artwork by title (DELETE)
    @Transactional
    @Modifying
    @Query("DELETE FROM Artwork a WHERE LOWER(a.title) = LOWER(:title)")
    void deleteArtworkByTitle(String title);

    // Delete artwork by description (DELETE)
    @Transactional
    @Modifying
    @Query("DELETE FROM Artwork a WHERE LOWER(a.description) = LOWER(:description)")
    void deleteArtworkByDescription(String description);
}
