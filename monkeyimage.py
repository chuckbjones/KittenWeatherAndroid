#! /usr/bin/env monkeyrunner
#
# Influenced by: http://dtmilano.blogspot.com/2011/04/monkeyrunner-visual-image-comparison.html
#

import sys, getopt, subprocess
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice, MonkeyImage

PKG = 'com.chuckbjones.kittenweather'
ACTIVITY = 'com.chuckbjones.kittenweather.MainActivity'
RUNCOMPONENT = PKG + '/' + ACTIVITY
TIMEOUT = 30
REF = 'img/reference.png'
SCR = 'img/screenshot.png'
CMP = 'img/comparison.png'
ACCEPTANCE = 0.99

device = None

def testSnapshot(doinstall=True):
    if doinstall:
        print "building application..."
        subprocess.call(['./gradlew', 'assembleMonkeyDebug'])

        print "installing application..."
        device.installPackage('app/build/outputs/apk/app-monkey-debug.apk')
    
    print "launching application..."
    device.startActivity(component=RUNCOMPONENT)
    MonkeyRunner.sleep(1)

    print "taking snapshot..."
    screenshot = device.takeSnapshot()
        
    # uncomment the following line to update the reference image
    # screenshot.writeToFile(REF)

    print "retrieving reference snapshot..."
    reference = MonkeyRunner.loadImageFromFile(REF)


    print "comparing snapshots..."
    same = False
    try:
        same = screenshot.sameAs(reference, ACCEPTANCE)
    except:
        print "Unexpected error:", sys.exc_info()[0]

    if not same:
        print "comparison failed, getting visual comparison..."
        screenshot.writeToFile(SCR)
        subprocess.call(["/usr/local/bin/compare", REF, SCR, CMP])
        sys.exit(1)


def main(argv):
    doinstall = True
    try:                                
        opts, args = getopt.getopt(argv, "hn", ["help", "noinstall"])
    except getopt.GetoptError:
        print "monkeyimage.py [--noinstall]"
        sys.exit(2)
    for opt, arg in opts:
        if opt in ("-h", "--help"):
            print "monkeyimage.py [--noinstall]"                
            sys.exit()     
        elif opt in ("-n", "--noinstall"):
            doinstall = False

    global device
    print "waiting for connection..."
    device = MonkeyRunner.waitForConnection(TIMEOUT)
    if device:
       testSnapshot(doinstall)


if __name__ == '__main__':
     main(sys.argv[1:])
