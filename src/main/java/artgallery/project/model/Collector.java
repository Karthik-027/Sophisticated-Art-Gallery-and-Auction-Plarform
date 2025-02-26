package artgallery.project.model;

import jakarta.persistence.*;

@Entity
public class Collector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String preference;

    public Collector() {
    }

    public Collector(Long id, String name, String preference) {
        this.id = id;
        this.name = name;
        this.preference = preference;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreference() {
        return preference;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
