package artgallery.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String bio;
    private String email;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("artist")
    @JsonManagedReference
    private List<Artwork> artworks; // One-to-Many with Artwork

    @Transient // Not mapped in DB, used for easier access
    public List<Auction> getAuctions() {
        return artworks.stream()
            .flatMap(artwork -> artwork.getAuctions().stream())
            .distinct()
            .collect(Collectors.toList());
    }

    // Default constructor
    public Artist() {}

    public Artist(Long id, String name, String bio, String email, List<Artwork> artworks) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.email = email;
        this.artworks = artworks;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
}
