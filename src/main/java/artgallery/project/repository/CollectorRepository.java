package artgallery.project.repository;

import artgallery.project.model.Collector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CollectorRepository extends JpaRepository<Collector, Long> {

    List<Collector> findByName(String name);

    List<Collector> findByPreference(String preference);

    @Transactional
    @Modifying
    @Query("DELETE FROM Collector c WHERE c.name = :name")
    int deleteCollectorByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Collector c WHERE c.preference = :preference")
    int deleteCollectorByPreference(String preference);
}
