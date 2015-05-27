#!/bin/bash
# Plug in a device and run this script to exercise your monkey.
#
# NOTE: Pin the app under test to prevent swiping down the notification window and. Otherwise, monkey will
# spend most of its time disabling your wifi.

# Default event percentages taken from the monkey source:
# mFactors[FACTOR_TOUCH] = 15.0f;
# mFactors[FACTOR_MOTION] = 10.0f;
# mFactors[FACTOR_TRACKBALL] = 15.0f;
# mFactors[FACTOR_ROTATION] = 0.0f;
# mFactors[FACTOR_NAV] = 25.0f;
# mFactors[FACTOR_MAJORNAV] = 15.0f;
# mFactors[FACTOR_SYSOPS] = 2.0f;
# mFactors[FACTOR_APPSWITCH] = 2.0f;
# mFactors[FACTOR_FLIP] = 1.0f;
# mFactors[FACTOR_ANYTHING] = 13.0f;
# mFactors[FACTOR_PINCHZOOM] = 2.0f;

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
