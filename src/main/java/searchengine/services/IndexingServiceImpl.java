package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.model.PageEntity;
import searchengine.model.SiteEntity;
import searchengine.model.Status;
import searchengine.repositories.PageRepository;
import searchengine.repositories.SiteRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    private final SitesList sites;
    @Override
    public void startIndexing() {

        databaseCleanup();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<PageParser> tasks = new ArrayList<>();
        for (Site site : sites.getSites()) {
            SiteEntity siteEntity = new SiteEntity();
            siteEntity.setStatus(Status.INDEXING);
            siteEntity.setStatusTime(LocalDateTime.now());
            siteEntity.setUrl(site.getUrl());
            siteEntity.setName(site.getName());
            siteRepository.save(siteEntity);
            tasks.add(new PageParser(pageRepository, siteEntity, site.getUrl()));
        }
        for (PageParser task : tasks) {
            forkJoinPool.invoke(task);
        }
        for (PageParser task : tasks) {
            task.join();
        }
    }

    @Override
    public void stopIndexing() {

    }

    public void databaseCleanup() {
        List<SiteEntity> siteEntities = new ArrayList<>();
        for (Site site : sites.getSites()) {
            siteEntities.addAll(siteRepository.findAllByUrl(site.getUrl()));
        }
        List<PageEntity> pageEntities = new ArrayList<>();
        for (SiteEntity siteEntity : siteEntities) {
            pageEntities.addAll(pageRepository.findAllBySite(siteEntity));
        }
        pageRepository.deleteAll(pageEntities);
        siteRepository.deleteAll(siteEntities);
    }

}
