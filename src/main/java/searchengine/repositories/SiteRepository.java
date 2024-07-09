package searchengine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import searchengine.model.SiteEntity;

import java.util.List;

public interface SiteRepository extends JpaRepository<SiteEntity, Integer> {
    long deleteByUrl(String url);
    List<SiteEntity> findAllByUrl(String url);

}
