package artgallery.project.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String auctionName;
    private String date;
    private String location;

    @ManyToMany(mappedBy = "auctions")
    @JsonIgnoreProperties("auctions")
    private List<Artwork> artworks; // Many-to-Many with Artwork

    // Default constructor
    public Auction() {}

    public Auction(Long id, String auctionName, String date, String location, List<Artwork> artworks) {
        this.id = id;
        this.auctionName = auctionName;
        this.date = date;
        this.location = location;
        this.artworks = artworks;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
}
