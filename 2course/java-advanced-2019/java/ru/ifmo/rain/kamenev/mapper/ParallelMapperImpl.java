package ru.ifmo.rain.kamenev.mapper;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class ParallelMapperImpl implements ParallelMapper {

    private final ArrayList<Thread> threads;
    private final SynchronizedQueue queue;
    private  Counter counter;

    public ParallelMapperImpl(int threadsNumber) {
        queue = new SynchronizedQueue();
        threads = new ArrayList<>();
        for (int i = 0; i < threadsNumber; ++i) {
            Thread thread = new Thread(new Worker());
            thread.start();
            threads.add(thread);
        }
    }

    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> function, List<? extends T> args) throws InterruptedException {
        List<R> result = new ArrayList<>(Collections.nCopies(args.size(), null));
        counter = new Counter(args.size());
        for (int index = 0; index < args.size(); ++index) {
            queue.push(new Task<T, R>(function, counter, result, index, args.get(index)));
        }
        synchronized (counter) {
            while (counter.get() > 0) {
                counter.wait();
            }
        }
        return result;
    }

    @Override
    public void close() {
        threads.forEach(Thread::interrupt);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
                // Do nothing
            }
        });
    }

    private class SynchronizedQueue {

        private LinkedList<Task> tasks = new LinkedList<>();

        public synchronized void push(Task task) {
            tasks.push(task);
            notify();
        }

        public synchronized Task pop() {
            return tasks.pop();
        }

        public synchronized boolean isEmpty() {
            return tasks.isEmpty();
        }
    }

    private class Counter {

        private int count;

        Counter(int count) {
            this.count = count;
        }

        synchronized int get() {
            return count;
        }

        synchronized void dec() {
            count--;
            if (count <= 0) {
                notify();
            }
        }

    }

    private class Task<T, R> {

        private final Function<? super T, ? extends R> function;
        private final Counter counter;
        private final List<R> result;
        private final int index;
        private final T arg;

        Task(Function<? super T, ? extends R> function, Counter counter, List<R> result, int index, T arg) {
            this.function = function;
            this.counter = counter;
            this.result = result;
            this.index = index;
            this.arg = arg;
        }

        void applyFunction() {
            result.set(index, function.apply(arg));
        }
    }

    private class Worker implements Runnable {

        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Task task;
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            queue.wait();
                        }
                        task = queue.pop();
                    }
                    task.applyFunction();
                    task.counter.dec();
                }
            } catch (InterruptedException ignored) {
                //Do nothing
            }
        }
    }
}