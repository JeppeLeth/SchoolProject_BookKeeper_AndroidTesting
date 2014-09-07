#!/bin/bash

TEST_NAME=$1
METHOD_NAME=$2
JAR_NAME=BookKeeperUIAutomateTEst.jar
adb push bin/$JAR_NAME /data/local/tmp

if [ -z "$METHOD_NAME" ] ; then
    adb shell uiautomator runtest $JAR_NAME -c edu.kea.pm.bookkeeper.uitest.runnable.$TEST_NAME
else
    adb shell uiautomator runtest $JAR_NAME -c com.enflick.android.TextNow.uiautomator.$TEST_NAME#$METHOD_NAME
fi
