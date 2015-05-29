package com.chuckbjones.kittenweather;

final class Modules {
    static Object[] list() {
        return new Object[] {
                new AndroidModule(),
                new TestModule()
        };
    }

    private Modules() {
        // No instances.
    }
}
