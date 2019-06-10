package ru.ifmo.rain.kamenev.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.*;

public class WebCrawler implements Crawler {

    public static final int DOWNLOADERS = 10;
    public static final int EXTRACTORS = 10;
    public static final int PERHOST = 10;
    public static final int DEPTH = 1;

    private Downloader downloader;
    private int downloaders;
    private int extractors;
    private int perHost;

    private final ExecutorService downloadPool;
    private final ExecutorService extractPool;
    private final Map<String, HostService> hosts;

    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.downloaders = downloaders;
        this.extractors = extractors;
        this.perHost = perHost;
        downloadPool = Executors.newFixedThreadPool(downloaders);
        extractPool = Executors.newFixedThreadPool(extractors);
        hosts = new ConcurrentHashMap<>();
    }

    @Override
    public Result download(String url, int depth) {
        final Phaser phaser = new Phaser(1);
        final Set<String> visited = ConcurrentHashMap.newKeySet();
        final Map<String, IOException> errors = new ConcurrentHashMap<>();
        final Set<String> downloaded = ConcurrentHashMap.newKeySet();
        visited.add(url);
        tryToDownload(url, depth, phaser, visited, downloaded,
                errors);
        phaser.arriveAndAwaitAdvance();
        return new Result(new ArrayList<>(downloaded), errors);
    }

    private void tryToDownload(String url,
                               int depth,
                               Phaser phaser,
                               Set<String> visited,
                               Set<String> downloaded,
                               Map<String, IOException> errors) {
        try {
            final String host = URLUtils.getHost(url);
            HostService hostService = hosts.computeIfAbsent(host, s -> new HostService());
            phaser.register();
            hostService.add(() -> {
                try {
                    final Document document = downloader.download(url);
                    downloaded.add(url);
                    if (depth > 1) {
                        phaser.register();
                        extractPool.submit(() -> {
                            try {
                                document.extractLinks()
                                        .stream()
                                        .filter(visited::add)
                                        .forEach(link -> tryToDownload(link, depth - 1, phaser, visited, downloaded, errors));
                            } catch (IOException ignored) {
                            } finally {
                                phaser.arrive();
                            }
                        });
                    }
                } catch (IOException e) {
                    errors.put(url, e);
                } finally {
                    phaser.arrive();
                    hostService.submitNext();
                }
            });
        } catch (MalformedURLException e) {
            errors.put(url, e);
        }
    }

    @Override
    public void close() {
        downloadPool.shutdownNow();
        extractPool.shutdownNow();
    }

    public static void main(String[] args) {
        if (args == null || args.length < 1 || args.length > 5) {
            System.out.println("Wrong arguments number: should be from 1 to 5");
            return;
        }
        for (String arg : args) {
            Objects.requireNonNull(arg);
        }
        try {
            int depth = args.length > 1 ? Integer.parseInt(args[1]) : DEPTH;
            int downloaders = args.length > 2 ? Integer.parseInt(args[2]) : DOWNLOADERS;
            int extractors = args.length > 3 ? Integer.parseInt(args[3]) : EXTRACTORS;
            int perHost = args.length > 4 ? Integer.parseInt(args[4]) : PERHOST;
            try (Crawler crawler = new WebCrawler(new CachingDownloader(), downloaders, extractors, perHost)) {
                crawler.download(args[0], depth);
            } catch (IOException e) {
                System.out.println("Can't create CashingDownloader");
            }
        } catch (NumberFormatException e) {
            System.out.println("Incorrect arguments type");
        }

    }

    private class HostService {
        private final Queue<Runnable> waitingTasks;
        private int runnedTasks;

        public HostService() {
            waitingTasks = new ArrayDeque<>();
            runnedTasks = 0;
        }

        public synchronized void add(Runnable task) {
            if (runnedTasks >= perHost) {
                waitingTasks.add(task);
            } else {
                downloadPool.submit(task);
                ++runnedTasks;
            }
        }

        public synchronized void submitNext() {
            final Runnable task = waitingTasks.poll();
            if (task != null) {
                downloadPool.submit(task);
            } else {
                --runnedTasks;
            }

        }
    }
}
