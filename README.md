Java API Applet Example
=========

*This is a deprecated technology. Do not use Applets for new development*.

Sample application utilizing the Pyramid Java API for Pyramid bill validators. With this API
you can quickly configure your application to operate with a bill validator. This application shows RS-232 state transition and reports the denomination of accepted notes. It also demonstrates an application were you may sell access to service for a period of time. Time is added by inserting more money into the bill validator. 
  

## Requirements

1. A supported bill validator
   - Apex 7000
   - Apex 5000
   - Trilogy *requires RS-232 firmware*
   - Curve *require RS-232 firmware*
2. Communication Harness
   - [Recommended Harness](http://shop.pyramidacceptors.com/usb-rs-232-communication-cable-harness-for-apex-05aa0023/)
   - Or a DB9 RS-232 connection or USB UART cable of your choice
3. Your favorite Java editor (we use Netbeans because projects are easy to share)
4. A copy of [PTalk.jar](https://github.com/PyramidTechnologies/jPyramid-RS-232/releases)

## Setup
If you are using Netbeans, simply open the project and satisfy the PTalk and jsoup dependencies. You will then be able to run and debug the application.

If you are using Intellij, the project should import and handle all of the depency stuff for you via gradle.

## Dependencies
If you prefer to handle your own depencies, here is what you will need

 1. The ptalk.jar file from this project (which itself include its bundled dependencies)
 2. jsoup 1.7.3
 3. (Already in PTalk) jSSC latest
 4. (Already in PTalk) Apache commons-collection 4.4+

## Questions
Please [let us know](https://github.com/PyramidTechnologies/Java-API-applet-sample/issues/new).



![](https://googledrive.com/host/0B79TkjL8Nm20QjU0UGhObnBTUE0/logo_2.jpg)
