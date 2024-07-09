package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import searchengine.model.PageEntity;
import searchengine.model.SiteEntity;
import searchengine.repositories.PageRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

@RequiredArgsConstructor
public class PageParser extends RecursiveAction {

    private final PageRepository repository;
    private final SiteEntity siteEntity;
    private final String path;
    //SiteAccessSynchronizer siteAccessSynchronizer;

//    public PageParser(SiteEntity siteEntity, String path) {
//        this.siteEntity = siteEntity;
//        this.path = path;
//        //this.siteAccessSynchronizer = siteAccessSynchronizer;
//    }

    @Override
    protected void compute() {
        System.out.println(path + " -> " + Thread.currentThread().getName());
        Document document;
        synchronized (siteEntity) {
            if (repository.findBySiteAndPath(siteEntity, path).isPresent()) {
                return;
            }
            try {
                document = Jsoup.connect(path).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            PageEntity pageEntity = new PageEntity();
            pageEntity.setSite(siteEntity);
            pageEntity.setPath(document.location());
            pageEntity.setCode(document.connection().response().statusCode());
            pageEntity.setContent(document.html());

            repository.save(pageEntity);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        // TODO: Распарсить страницу найти все ссылки и создать новые задачи
        List<PageParser> tasks = new ArrayList<>();
        Elements elements = document.select("[href]");
        Set<String> hrefSet = new HashSet<>();
        System.out.println("Doc location: " + document.location());
        for (Element element : elements) {
            String link = element.absUrl("href");
//            System.out.println("\t" + link);
            if (!link.contains(document.location())) {
//                System.out.println("\t" + "!link.contains(path) -> " + link);
                continue;
            }
            if (link.contains("#")) {
//                System.out.println("\t" + "link.contains(\"#\") -> " + link);
                continue;
            }
            if (link.matches(".+\\.\\w+") && !link.matches(".+\\.html")) {
//                System.out.println("\t" + "link.matches(\".+\\\\.\\\\w+\") && !link.matches(\".+\\\\.html\" -> " + link);
                continue;
            }
            if (path.contains(link)) {
//                System.out.println("\t" + "path.contains(link) -> " + link);
                continue;
            }
//            System.out.println("\t" + "link is Ok -> " + link);
            hrefSet.add(link);
        }
        System.out.println("\threfSet: " + hrefSet);
        for (String href : hrefSet) {
            tasks.add(new PageParser(repository, siteEntity, href));
        }
        invokeAll(tasks);
    }
}
