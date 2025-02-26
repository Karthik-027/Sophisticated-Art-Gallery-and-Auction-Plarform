package artgallery.project.service;

import artgallery.project.model.Collector;
import artgallery.project.repository.CollectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CollectorService {

    @Autowired
    private CollectorRepository collectorRepository;

    // Get all collectors
    public List<Collector> getAllCollectors() {
        return collectorRepository.findAll();
    }

    // Get collector by ID
    public Optional<Collector> getCollectorById(Long id) {
        return collectorRepository.findById(id);
    }

    // Create or update collector
    public Collector createOrUpdateCollector(Collector collector) {
        return collectorRepository.save(collector);
    }

    // Delete collector by ID
    public void deleteCollectorById(Long id) {
        collectorRepository.deleteById(id);
    }

    // Delete collector by name
    @Transactional
    public int deleteCollectorByName(String name) {
        return collectorRepository.deleteCollectorByName(name);
    }

    // Delete collector by preference
    @Transactional
    public int deleteCollectorByPreference(String preference) {
        return collectorRepository.deleteCollectorByPreference(preference);
    }

    // Get paginated collectors
    public Page<Collector> getPaginatedCollectors(int page, int size) {
        return collectorRepository.findAll(PageRequest.of(page, size));
    }

    // Find collectors by name
    public List<Collector> findCollectorsByName(String name) {
        return collectorRepository.findByName(name);
    }

    // Find collectors by preference
    public List<Collector> findCollectorsByPreference(String preference) {
        return collectorRepository.findByPreference(preference);
    }
}
