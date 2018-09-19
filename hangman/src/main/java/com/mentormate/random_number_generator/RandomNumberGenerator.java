package com.mentormate.random_number_generator;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {

    public static int generate(int n) {
        return ThreadLocalRandom.current().nextInt(n);
    }
}
