# KittenWeather Android

Demo project for showcasing various testing tools and frameworks for Android apps.
Ported from the iOS version created by [@SeanMcTex](https://github.com/SeanMcTex/KittenWeather).
See that project for details on the techniques used.

## Installation and Running
 * You'll need a recent version of Android Studio that supports local unit tests. I used v1.2.1.1.
 * Import the top level build.gradle file as a new project in Android Studio.
 * Add a new Build Configuration for JUnit:
    - Run -> Edit Configurations...
    - Click '+' -> JUnit
    - Test Kind: "All in Package"
    - Package: "com.chuckbjones.kittenweather"
    - Search for Tests: in single module
 * Run... -> JUnit tests.

## Libraries Used
 * Dagger - http://square.github.io/dagger
 * ButterKnife - http://jakewharton.github.io/butterknife
 * Retrofit - http://square.github.io/retrofit
 * Picasso - http://square.github.io/picasso
 * FloatingActionButton - https://github.com/makovkastar/FloatingActionButton
 * JUnit - http://junit.org/
 * Hamcrest - http://hamcrest.org/
 * junit-quickcheck - https://github.com/pholser/junit-quickcheck

## QuickCheck
 *junit-quickcheck* is used as an analog for *Fox* in iOS.

 To install junit-quickcheck, add the following to your build.gradle file:

    dependencies {
            testCompile 'junit:junit:4.12'
            testCompile 'org.hamcrest:hamcrest-library:1.3'
            testCompile 'com.pholser:junit-quickcheck-core:0.4'
            testCompile 'com.pholser:junit-quickcheck-generators:0.4'
    }

 Here is an analogous test to `testIdentityKelvin` in the iOS version. junit-quickcheck defaults to
 a sample size of 100 and a range of 0.0 to 1.0 for floats, so we override those in the parameter annotation:

     @Theory
     public void identityKelvin(@ForAll(sampleSize = 10000) @InRange(min = "-10000", max = "10000") float kelvinTemperatureNumber) {
         Temperature temperature = Temperature.fromKelvin(kelvinTemperatureNumber);
         float difference = Math.abs(kelvinTemperatureNumber - temperature.kelvinDegrees());
         assertThat(difference, is(lessThan(MATCHING_TOLERANCE)));
     }

## TODO: Monkey
 *Should be able to use the Android Monkey tool as an analog to UIAutoMonkey for iOS.*

## TODO: MonkeyImage
 *I think we can use the MonkeyImage class with the Android monkeyrunner tool as an analog to FBSnapshotTestCase for iOS.*
