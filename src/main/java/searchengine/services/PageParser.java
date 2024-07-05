package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import searchengine.model.SiteEntity;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

public class PageParser extends RecursiveAction {

    SiteEntity siteEntity;
    String path;
    SiteAccessSynchronizer siteAccessSynchronizer;

    public PageParser(String path, SiteAccessSynchronizer siteAccessSynchronizer) {
        this.path = path;
        this.siteAccessSynchronizer = siteAccessSynchronizer;
    }

    @Override
    protected void compute() {
        // TODO: Проверить, есть ли путь в базе (написать отдельную синхронизированную функцию)
        /* TODO: Загрузить страницу и записать её в базу (должно быть в синхронизированном блоке)
            для заполнения страницы нужны:
            -ID сайта
            -
         */
        // TODO: Распарсить страницу найти все ссылки и создать новые задачи
        try {
            Document document = Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
