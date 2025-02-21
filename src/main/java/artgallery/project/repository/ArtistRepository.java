package artgallery.project.repository;

import artgallery.project.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    // 1. Find artists by name (case-insensitive)
    @Query("SELECT a FROM Artist a WHERE LOWER(a.name) = LOWER(?1)")
    List<Artist> findByNameIgnoreCase(String name);

    // 2. Find all artists containing a keyword in their bio
    @Query("SELECT a FROM Artist a WHERE a.bio LIKE %?1%")
    List<Artist> findByBioContaining(String keyword);

    // 3. Delete artists by name
    @Modifying
    @Transactional
    @Query("DELETE FROM Artist a WHERE LOWER(a.name) = LOWER(?1)")
    void deleteByName(String name);

    // 4. Add (POST) a new artist (handled by JpaRepository's save method)

    // 5. Find all artists (GET all)
    @Query("SELECT a FROM Artist a")
    List<Artist> findAllArtists();

    // 6. Find an artist by email (case-insensitive)
    @Query("SELECT a FROM Artist a WHERE LOWER(a.email) = LOWER(?1)")
    Artist findByEmailIgnoreCase(String email);

    // 7. Update artist's bio (PUT)
    @Modifying
    @Transactional
    @Query("UPDATE Artist a SET a.bio = ?2 WHERE LOWER(a.name) = LOWER(?1)")
    void updateBioByName(String name, String newBio);

    // 8. Update artist's email (PUT)
    @Modifying
    @Transactional
    @Query("UPDATE Artist a SET a.email = ?2 WHERE LOWER(a.name) = LOWER(?1)")
    void updateEmailByName(String name, String newEmail);
}
