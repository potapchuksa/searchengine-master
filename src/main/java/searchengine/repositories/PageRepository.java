package searchengine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import searchengine.model.PageEntity;
import searchengine.model.SiteEntity;

import java.util.List;
import java.util.Optional;

public interface PageRepository extends JpaRepository<PageEntity, Integer> {

    Optional<PageEntity> findByPath(String path);
    Optional<PageEntity> findBySiteAndPath(SiteEntity siteEntity, String path);

    List<PageEntity> findAllBySite(SiteEntity siteEntity);

    void deleteBySite(SiteEntity siteEntity);
}
