package ru.ifmo.rain.kamenev.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloUDPClient implements HelloClient {

    private static int TIMEOUT = 100;

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        ExecutorService pull = Executors.newFixedThreadPool(threads);
        InetSocketAddress address = new InetSocketAddress(host, port);
        for (int number = 0; number < threads; number++) {
            pull.submit(new Worker(requests, number, prefix, address));
        }
        pull.shutdown();

        try {
            if (!pull.awaitTermination(threads * requests, TimeUnit.MINUTES)) {
                pull.shutdownNow();
            }
        } catch (InterruptedException ignored) {
            //
        }

    }

    private static class Worker implements Runnable {
        private int requests;
        private int threadNumber;
        private final String prefix;
        private final InetSocketAddress address;

        public Worker(int requests, int threadNumber, String prefix, InetSocketAddress address) {
            this.requests = requests;
            this.threadNumber = threadNumber;
            this.prefix = prefix;
            this.address = address;
        }

        @Override
        public void run() {
            int requestNumber = 0;
            String request = null;
            DatagramPacket requestPacket = null;

            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(TIMEOUT);
                byte[] bufRequest;
                byte[] bufResponse = new byte[socket.getReceiveBufferSize()];
                while (!Thread.currentThread().isInterrupted() && requestNumber < requests) {
                    if (request == null) {
                        request = prefix + threadNumber + "_" + requestNumber;
                        bufRequest = request.getBytes(StandardCharsets.UTF_8);
                        requestPacket = new DatagramPacket(bufRequest, 0, bufRequest.length, address);
                    }

                    try {
                        socket.send(requestPacket);
                    } catch (IOException e) {
                        System.err.println("Can't send packet");
                        if (!socket.isClosed()) {
                            continue;
                        } else {
                            return;
                        }
                    }

                    DatagramPacket responsePacket = new DatagramPacket(bufResponse, bufResponse.length);
                    try {
                        socket.receive(responsePacket);
                        String response = new String(responsePacket.getData(), responsePacket.getOffset(), responsePacket.getLength(), StandardCharsets.UTF_8);

                        // too much time, maybe wrong
                        /*while (!response.equals("Hello, " + request)) {
                            socket.receive(responsePacket);
                            response = new String(responsePacket.getData(), responsePacket.getOffset(), responsePacket.getLength(), StandardCharsets.UTF_8);
                        }*/

                        if (response.equals("Hello, " + request)) {
                            System.out.println(response);
                            request = null;
                            ++requestNumber;
                        }
                    } catch (IOException e) {
                        System.err.println("Can't receive packet");
                        if (socket.isClosed()) {
                            return;
                        }
                    }
                }

            } catch (SocketException e) {
                System.err.println("Can't open port");
            }
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length != 5) {
            System.out.println("Expected five arguments");
            return;
        }
        for (String arg : args) {
            if (arg == null) {
                System.out.println("Arguments can't be null");
                return;
            }
        }

        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String prefix = args[2];
            int threads = Integer.parseInt(args[3]);
            int requests = Integer.parseInt(args[4]);
            new HelloUDPClient().run(host, port, prefix, threads, requests);
        } catch (NumberFormatException e) {
            System.out.println("Expected integer arguments");
        }
    }
}