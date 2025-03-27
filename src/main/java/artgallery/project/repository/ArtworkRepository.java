package artgallery.project.repository;

import artgallery.project.model.Artwork;
import artgallery.project.model.Artist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    // Find artworks by artist ID with indexing for optimization
    List<Artwork> findByArtistId(@Param("artistId") Long artistId);

    // Find artworks by title (case-insensitive) with optional pagination
    @Query("SELECT a FROM Artwork a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Artwork> findArtworksByTitle(@Param("title") String title, Pageable pageable);

    // Find artworks by artist
    List<Artwork> findByArtist(@Param("artist") Artist artist, Pageable pageable);

    // Find artworks within a price range
    @Query("SELECT a FROM Artwork a WHERE a.price BETWEEN :minPrice AND :maxPrice")
    List<Artwork> findArtworksByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

    // Update artwork by title
    @Transactional
    @Modifying
    @Query("UPDATE Artwork a SET a.description = :description, a.price = :price, a.artist = :artist WHERE LOWER(a.title) = LOWER(:title)")
    int updateArtworkByTitle(@Param("title") String title, @Param("description") String description, @Param("price") double price, @Param("artist") Artist artist);

    // Update artwork by description
    @Transactional
    @Modifying
    @Query("UPDATE Artwork a SET a.title = :title, a.price = :price, a.artist = :artist WHERE LOWER(a.description) = LOWER(:description)")
    int updateArtworkByDescription(@Param("description") String description, @Param("title") String title, @Param("price") double price, @Param("artist") Artist artist);

    // Delete artwork by title
    @Transactional
    @Modifying
    @Query("DELETE FROM Artwork a WHERE LOWER(a.title) = LOWER(:title)")
    int deleteArtworkByTitle(@Param("title") String title);

    // Delete artwork by description
    @Transactional
    @Modifying
    @Query("DELETE FROM Artwork a WHERE LOWER(a.description) = LOWER(:description)")
    int deleteArtworkByDescription(@Param("description") String description);
}
