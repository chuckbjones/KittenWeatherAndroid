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

## Monkey
Android's [UI/Application Exerciser Monkey](http://developer.android.com/tools/help/monkey.html) tool is an analog to UIAutoMonkey for iOS.

Because it generates completely random touch, keypress, and button events, the monkey can cause some havoc on your device. It has even been know to [take occasional screenshots and randomly start playing music on its own](http://www.everybodytests.com/2014/08/why-is-android-monkey-so-naughty.html).

So, it's a good idea to disable system events (eg. pressing the power or volume buttons) and special keypress event. This is done by setting the frequency of those events to 0 using monkey's command line options. 

If you are running Lollipop or higher, you can also [pin](https://support.google.com/nexus/answer/6118421?hl=en) the application under test to prevent monkey from escaping from your app and changing your Wi-Fi settings, etc.

### Unleash the Monkey
The monkey lives on your device (or emulator). To set him loose, you will need [adb](http://developer.android.com/tools/help/adb.html) installed and in your path. If using Android Studio, `adb` will most likely be installed under `~/Library/Android/sdk/platform-tools/adb` (OS X).

Connect your device and run monkey via `adb shell` from the terminal:

    adb shell monkey -v -p com.chuckbjones.kittenweather \
        --pct-touch 70 \
        --pct-motion 10 \
        --pct-trackball 6 \
        --pct-nav 6 \
        --pct-majornav 5 \
        --pct-rotation 1 \
        --pct-pinchzoom 1 \
        --pct-flip 1 \
        --pct-anyevent 0 \
        --pct-syskeys 0 \
        --pct-appswitch 0 \
        2000

Here, we are specifying the frequencies of each event type as percentages. If specifying a number for each type, they must add up to 100. If any are omitted, their respective default is used, adjusted in relation to the numbers provided so that they all add up to 100%.

Here are the default frequencies pulled from the [source code](https://android.googlesource.com/platform/development.git/+/master/cmds/monkey/src/com/android/commands/monkey/MonkeySourceRandom.java):

    mFactors[FACTOR_TOUCH] = 15.0f;
    mFactors[FACTOR_MOTION] = 10.0f;
    mFactors[FACTOR_TRACKBALL] = 15.0f;
    mFactors[FACTOR_ROTATION] = 0.0f;
    mFactors[FACTOR_NAV] = 25.0f;
    mFactors[FACTOR_MAJORNAV] = 15.0f;
    mFactors[FACTOR_SYSOPS] = 2.0f;
    mFactors[FACTOR_APPSWITCH] = 2.0f;
    mFactors[FACTOR_FLIP] = 1.0f;
    mFactors[FACTOR_ANYTHING] = 13.0f;
    mFactors[FACTOR_PINCHZOOM] = 2.0f;

Note that there are a number of options that are not listed in the tool's documentation. I haven't begun to explore [all of them](https://android.googlesource.com/platform/development.git/+/master/cmds/monkey/src/com/android/commands/monkey/Monkey.java), but one of note is `--pct-rotation`. This option has a default frequency of 0%, meaning you must specify it if you want the monkey to test orientation changes, a common source of crashiness in Android apps. 

The 2000 at the end of the command is the number of events to trigger. It doesn't have a switch associated with it, so it must appear last in the command.

## TODO: MonkeyImage
 *I think we can use the MonkeyImage class with the Android monkeyrunner tool as an analog to FBSnapshotTestCase for iOS.*
