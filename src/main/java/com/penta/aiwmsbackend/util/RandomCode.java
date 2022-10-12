package com.penta.aiwmsbackend.util;

import java.util.Random;

public class RandomCode {

    private static Integer BOUND = 100000000;

    public static Integer generate() {
        Random ram = new Random();
        return ram.nextInt(BOUND);
    }

    public static void main(String[] args) {
        System.out.println(RandomCode.generate());
    }

}
