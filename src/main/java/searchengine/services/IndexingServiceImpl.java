package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;

import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService {

    private final SitesList sites;
    @Override
    public void startIndexing() {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (Site site : sites.getSites()) {
            forkJoinPool.invoke(new PageParser(site.getUrl(), new ServiceFunctions()));
        }
    }

    @Override
    public void stopIndexing() {

    }

}
