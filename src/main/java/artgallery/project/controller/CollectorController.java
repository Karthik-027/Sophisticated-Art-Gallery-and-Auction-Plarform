package artgallery.project.controller;

import artgallery.project.model.Collector;
import artgallery.project.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/collectors")
public class CollectorController {

    @Autowired
    private CollectorService collectorService;

    @GetMapping
    public ResponseEntity<List<Collector>> getAllCollectors() {
        return ResponseEntity.ok(collectorService.getAllCollectors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collector> getCollectorById(@PathVariable Long id) {
        return collectorService.getCollectorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Collector> createCollector(@RequestBody Collector collector) {
        Collector createdCollector = collectorService.createOrUpdateCollector(collector);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCollector);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collector> updateCollector(@PathVariable Long id, @RequestBody Collector collectorDetails) {
        if (!collectorService.getCollectorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        collectorDetails.setId(id);
        Collector updatedCollector = collectorService.createOrUpdateCollector(collectorDetails);
        return ResponseEntity.ok(updatedCollector);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollector(@PathVariable Long id) {
        if (!collectorService.getCollectorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        collectorService.deleteCollectorById(id);
        return ResponseEntity.noContent().build();
    }

    // Delete collector by name
    @DeleteMapping("/delete/name")
    public ResponseEntity<String> deleteCollectorByName(@RequestParam String name) {
        int deleted = collectorService.deleteCollectorByName(name);
        return deleted > 0 ? ResponseEntity.ok("Collector deleted successfully") : ResponseEntity.notFound().build();
    }

    // Delete collector by preference
    @DeleteMapping("/delete/preference")
    public ResponseEntity<String> deleteCollectorByPreference(@RequestParam String preference) {
        int deleted = collectorService.deleteCollectorByPreference(preference);
        return deleted > 0 ? ResponseEntity.ok("Collector deleted successfully") : ResponseEntity.notFound().build();
    }

    // Get paginated collectors
    @GetMapping("/paginated")
    public ResponseEntity<List<Collector>> getPaginatedCollectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Collector> paginatedCollectors = collectorService.getPaginatedCollectors(page, size).getContent();
        return ResponseEntity.ok(paginatedCollectors);
    }

    // Search collectors by name
    @GetMapping("/search/name")
    public ResponseEntity<List<Collector>> findCollectorsByName(@RequestParam String name) {
        List<Collector> collectors = collectorService.findCollectorsByName(name);
        return ResponseEntity.ok(collectors);
    }

    // Search collectors by preference
    @GetMapping("/search/preference")
    public ResponseEntity<List<Collector>> findCollectorsByPreference(@RequestParam String preference) {
        List<Collector> collectors = collectorService.findCollectorsByPreference(preference);
        return ResponseEntity.ok(collectors);
    }
}
