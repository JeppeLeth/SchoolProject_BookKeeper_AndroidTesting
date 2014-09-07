Android Whitebox test cases
=========================

This is are some sample tests I wrote for the BookKeeper application using Android's UI
Automator framework. It will only run on Android 4.3+

To setup a project first time:
* android create uitest-project -n $PROJECT_NAME -t $DEVICE_NUMBER -p path\to\$PROJECT_NAME

To build:
* ant build

To run:
* ./deploy.sh
or
adb push path/to/bin/BookKeeperUIAutomateTEst.jar /data/local/tmp
adb shell uiautomator runtest BookKeeperUIAutomateTEst.jar -c edu.kea.pm.bookkeeper.uitest.runnable.$TEST_CASE

You can run several test cases in sequence by:
adb shell uiautomator runtest BookKeeperUIAutomateTEst.jar -c edu.kea.pm.bookkeeper.uitest.runnable.$TEST_CASE1 -c edu.kea.pm.bookkeeper.uitest.runnable.$TEST_CASE2

You can run induvidual tests sequence by using hashtags:
adb shell uiautomator runtest BookKeeperUIAutomateTEst.jar -c edu.kea.pm.bookkeeper.uitest.runnable.$TEST_CASE#method_name

Find more info on http://developer.android.com/tools/help/uiautomator/index.html

Example of how to run:
* cd "c:\Program Files (x86)\Android\android-sdk\platform-tools\"
* adb push C:\Users\Jeppe\git\bookkeeper-bitbucket\BookKeeperUIAutomateTEst\bin\BookKeeperUIAutomateTEst.jar /data/local/tmp/
* adb shell uiautomator runtest BookKeeperUIAutomateTEst.jar -c edu.kea.pm.bookkeeper.uitest.LaunchBookKeeper