package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl {

    private final SitesList sites;

    public void startIndexing() {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (Site site : sites.getSites()) {
            forkJoinPool.invoke(new PageParser(site.getUrl(), new CheckAndDelay()));
        }
    }

}
