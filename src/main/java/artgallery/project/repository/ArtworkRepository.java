package artgallery.project.repository;

import artgallery.project.model.Artwork;
import artgallery.project.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    
    List<Artwork> findByArtistId(Long artistId);


    // Find artworks by title (case-insensitive)
    @Query("SELECT a FROM Artwork a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Artwork> findArtworksByTitle(String title);

    // Find artworks by artist
    List<Artwork> findByArtist(Artist artist);

    // Find artworks within a price range
    @Query("SELECT a FROM Artwork a WHERE a.price BETWEEN :minPrice AND :maxPrice")
    List<Artwork> findArtworksByPriceRange(double minPrice, double maxPrice);

    // Update artwork by title
    @Transactional
    @Modifying
    @Query("UPDATE Artwork a SET a.description = :description, a.price = :price, a.artist = :artist WHERE LOWER(a.title) = LOWER(:title)")
    int updateArtworkByTitle(String title, String description, double price, Artist artist);

    // Update artwork by description
    @Transactional
    @Modifying
    @Query("UPDATE Artwork a SET a.title = :title, a.price = :price, a.artist = :artist WHERE LOWER(a.description) = LOWER(:description)")
    int updateArtworkByDescription(String description, String title, double price, Artist artist);

    // Delete artwork by title
    @Transactional
    @Modifying
    @Query("DELETE FROM Artwork a WHERE LOWER(a.title) = LOWER(:title)")
    void deleteArtworkByTitle(String title);

    // Delete artwork by description
    @Transactional
    @Modifying
    @Query("DELETE FROM Artwork a WHERE LOWER(a.description) = LOWER(:description)")
    void deleteArtworkByDescription(String description);
}
