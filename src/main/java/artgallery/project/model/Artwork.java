package artgallery.project.model;

import jakarta.persistence.*;

@Entity
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double price;
    private Long artistId;  // Store artist's ID as a reference instead of mapping

    // Default constructor
    public Artwork() {}

    public Artwork(Long id, String title, String description, double price, Long artistId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.artistId = artistId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }
}
