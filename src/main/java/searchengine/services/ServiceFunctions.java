package searchengine.services;

import lombok.RequiredArgsConstructor;
import searchengine.repositories.PageRepository;

@RequiredArgsConstructor
public class ServiceFunctions {

    private final PageRepository repository;
    public synchronized boolean exists(String path) {
        if (!repository.findByPath(path).isPresent()) {
            repository.save()
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
