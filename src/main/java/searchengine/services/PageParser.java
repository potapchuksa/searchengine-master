package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

public class PageParser extends RecursiveAction {

    String path;
    ServiceFunctions serviceFunctions;

    public PageParser(String path, ServiceFunctions serviceFunctions) {
        this.path = path;
        this.serviceFunctions = serviceFunctions;
    }

    @Override
    protected void compute() {
        try {
            Document document = Jsoup.connect(path).get();
            serviceFunctions.exists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
