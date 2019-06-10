package ru.ifmo.rain.kamenev.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class HelloUDPServer implements HelloServer {

    private ExecutorService senderThreads;
    private ExecutorService listenerThread;
    private DatagramSocket socket;

    @Override
    public void start(int port, int countOfThread) {
        System.out.println("Started");
        senderThreads = new ThreadPoolExecutor(countOfThread, countOfThread, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.DiscardPolicy());
        listenerThread = Executors.newSingleThreadExecutor();
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println("Can't open Datagram socket");
        }

        listenerThread.submit(new Listener(socket, senderThreads));
    }

    private static class Listener implements Runnable {

        private DatagramSocket socket;
        private ExecutorService threadPool;

        public Listener(DatagramSocket socket, ExecutorService threadPool) {
            this.socket = socket;
            this.threadPool = threadPool;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    byte[] receiveBuffer = new byte[socket.getReceiveBufferSize()];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    try {
                        socket.receive(receivePacket);
                        threadPool.submit(new Sender(socket, receivePacket));
                    } catch (IOException e) {
                        if (socket.isClosed()) {
                            return;
                        }
                        System.err.println("Error during receiving packet:");
                    }
                }
            } catch (SocketException ignored) {
                //
            }

        }
    }

    private static class Sender implements Runnable {
        private DatagramSocket socket;
        private DatagramPacket receivePacket;


        public Sender(DatagramSocket socket, DatagramPacket receivePacket) {
            this.socket = socket;
            this.receivePacket = receivePacket;
        }

        @Override
        public void run() {
            try {
                String message = "Hello, " + new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
                byte[] sendBuffer = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getSocketAddress());
                socket.send(sendPacket);
            } catch (IOException e) {
                if (socket.isClosed()) {
                    return;
                }
                System.err.println("Error during sending answer");
            }
        }

    }

    @Override
    public void close() {
        socket.close();
        listenerThread.shutdownNow();
        senderThreads.shutdownNow();
        try {
            senderThreads.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
            //
        }
        System.out.println("Closed");
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("Expected 2 arguments");
        } else if (args[0] == null || args[1] == null) {
            System.out.println("Arguments can't be null");
        } else {
            int numberOfPort = Integer.parseInt(args[0]);
            int countOfThread = Integer.parseInt(args[1]);
            try (HelloUDPServer server = new HelloUDPServer()) {
                server.start(numberOfPort, countOfThread);
            }
        }
    }

}
