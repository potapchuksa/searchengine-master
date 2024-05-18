package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import searchengine.config.Site;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

public class PageParser extends RecursiveAction {

    String path;
    CheckAndDelay checkAndDelay;

    public PageParser(String path, CheckAndDelay checkAndDelay) {
        this.path = path;
        this.checkAndDelay = checkAndDelay;
    }

    @Override
    protected void compute() {
        try {
            Document document = Jsoup.connect(path).get();
            checkAndDelay.run(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
