package com.khoubyari.api;

import com.intuit.karate.junit5.Karate;

public class KarateRunner {
    @Karate.Test
    Karate testSample() {
        return Karate.run("com.khoubyari.api").relativeTo(getClass());
    }
}
