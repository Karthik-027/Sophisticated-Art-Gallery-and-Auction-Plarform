package artgallery.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double price;
    
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    @JsonBackReference
    private Artist artist; // Establishing the relationship with Artist

    // Default constructor
    public Artwork() {}

    public Artwork(Long id, String title, String description, double price, Artist artist) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.artist = artist;
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
