package ru.ifmo.rain.kamenev.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ListIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IterativeParallelism implements ListIP {

    private int threadsNumber;
    private int eachCount;
    private int restCount;

    private void joinThreads(final List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private <T> void validateInput(int threadsNumbers, List<? extends T> values) {
        if (threadsNumbers <= 0) {
            throw new IllegalArgumentException("Amount of threads must be at least 1.");
        }
        Objects.requireNonNull(values);
    }

    private <T> void init(int i, List<? extends T> list) {
        validateInput(i, list);
        threadsNumber = Math.max(1, Math.min(i, list.size()));
        eachCount = list.size() / threadsNumber;
        restCount = list.size() % threadsNumber;
    }

    private <T, R> R task(int i, List<? extends T> values, Function<Stream<? extends T>, R> function, Function<Stream<R>, R> resultGrabber) throws InterruptedException {
        init(i, values);
        final List<Stream<? extends T>> threadTasks = new ArrayList<>();
        int l, r = 0;
        for (int j = 0; j < threadsNumber; j++) {
            l = r;
            r = l + eachCount;
            if (restCount > 0) {
                r++;
                restCount--;
            }
            threadTasks.add(values.subList(l, r).stream());
        }
        List<R> result;
        List<Thread> threads = new ArrayList<>();
        result = new ArrayList<>(Collections.nCopies(threadsNumber, null));
        for (int j = 0; j < threadsNumber; j++) {
            final int pos = j;
            Thread thread = new Thread(() -> result.set(pos, function.apply(threadTasks.get(pos))));
            thread.start();
            threads.add(thread);
        }
        joinThreads(threads);
        return resultGrabber.apply(result.stream());
    }

    @Override
    public <T> T maximum(int i, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        if (list.isEmpty()) {
            throw new NoSuchElementException("List can't be empty");
        }
        return task(i, list, stream -> stream.max(comparator).orElseThrow(), stream -> stream.max(comparator).orElseThrow());
    }

    @Override
    public <T> T minimum(int i, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        return maximum(i, list, Collections.reverseOrder(comparator));
    }

    @Override
    public <T> boolean all(int i, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return task(i, list, stream -> stream.allMatch(predicate), stream -> stream.allMatch(item -> item));
    }

    @Override
    public <T> boolean any(int i, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return !all(i, list, predicate.negate());
    }

    @Override
    public String join(int i, List<?> list) throws InterruptedException {
        return task(i, list, stream -> stream.map(Object::toString).collect(Collectors.joining()), stream -> stream.collect(Collectors.joining()));
//        return map(i, list, o -> o).stream().map(Object::toString).collect(Collectors.joining());
    }

    @Override
    public <T> List<T> filter(int i, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return task(i, list, stream -> stream.filter(predicate).collect(Collectors.toList()), listStream -> listStream.flatMap(Collection::stream).collect(Collectors.toList()));
    }

    @Override
    public <T, U> List<U> map(int i, List<? extends T> list, Function<? super T, ? extends U> function) throws InterruptedException {
        return task(i, list, stream -> stream.map(function).collect(Collectors.toList()), listStream -> listStream.flatMap(Collection::stream).collect(Collectors.toList()));
    }
}