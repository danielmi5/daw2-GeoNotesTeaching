package com.example.geonotesteaching;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class VirtualDemo {
    private static final int tasks = 50;

    public static void runIO() {
        var exec = Executors.newVirtualThreadPerTaskExecutor();
        try {

            for (int i = 0; i < tasks; i++) {
                int numTask = i+1;
                exec.submit(() -> {
                    try {

                        int sleep = ThreadLocalRandom.current().nextInt(200, 301);
                        Thread.sleep(sleep);

                        System.out.println("Hilo actual de la tarea " + numTask + " --> " +  Thread.currentThread() +
                                " (sleep: " + sleep + " ms)");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            exec.close();
        }
    }


    public static void main(String[] args) {
        runIO();
    }
}
