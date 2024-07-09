package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import searchengine.repositories.PageRepository;

@Component
@RequiredArgsConstructor
public class SiteAccessSynchronizer {

    private final PageRepository repository;
    public synchronized void exists(String path) {
        if (!repository.findByPath(path).isPresent()) {
            //repository.save()
        }
    }

    public synchronized void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
