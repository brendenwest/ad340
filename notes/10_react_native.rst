React Native
====

Reading
----
- https://reactnative.dev/docs/getting-started
- https://reactnative.dev/docs/tutorial
- https://docs.expo.io/introduction/why-not-expo/
- https://reactnative.dev/docs/native-modules-android
- https://reactjs.org/docs/hello-world.html

JavaScript Reference
----
- https://www.tutorialspoint.com/javascript/index.htm
- https://codeburst.io/es6-tutorial-for-beginners-5f3c4e7960be


Learning Outcomes
----
- React web development
- React Native approach to mobile applications
- RN Application setup
- Common RN UI components
- Networking in RN
- Platform-specific RN features
- Integration with existing native mobile applications


Overview
----
React Native (RN) is a variant of the React JavaScript framework, optimized for developing mobile applications. RN applications can be developed in JavaScript, using conventions similar to web applications, but are compiled to native code using platform-specific tools (Xcode or Android Studio)

RN provides a set of pre-defined UI components that align with common UI components in the iOS and Android SDK's.

RN applications can also use platform-specific native code for custom functionality.

Development approaches
----
RN applications can be developed using either Expo (a support framework) or the React Native CLI. Developers should understand the tradeoffs of each, since it can be difficult to change strategies later in a project.

Expo
^^^^
- easy to get started
- does not require installation of Android Studio or XCode
- does not support full capabilities of native SDK's (e.g. push notifications)
- lacks support for some 3rd party plugins (e.g. Facebook integration)

Native CLI
^^^^
- requires installation of platform IDE's to generate builds
- provides greater support for native SDK's
- 3rd party plugins more likely to be supported

Installation notes
----
After following the initial getting-started instructions for React Native, you should also update your app/build.gradle to use current Android SDK versions:
::

    compileSdkVersion 32
    buildToolsVersion "30.0.2"
    minSdkVersion 28
    targetSdkVersion 32

Development Notes
----
The initialization step creates a project structure with android and ios folders, but much of your application code lives outside those folders in the App.js file & any supporting JavaScript files you might create.

.. image:: ../images/react_project_structure.png
  :width: 490

You can open the 'android' folder via Android Studio as you would any Android project.

Because React Native application code mostly lives outside the platform-specific folder, steps are necessary to make that code available for execution on an Android device or emulator.

One approach is to first start an Android Studio emulator, then start up React Native's Metro Bundler using the 'react-native run-android' command. This command starts the Metro Bundler as a node.js web server on your local computer and generates an Android bundle file.

.. image:: ../images/react_metro_bundler.png
  :width: 490

  With no emulator running, you'll see a command-line error like this:
  ::

    BUILD FAILED in 2s
    27 actionable tasks: 1 executed, 26 up-to-date
    Could not install the app on the device, read the error above for details.
    Make sure you have an Android emulator running or a device connected and have
    set up your Android development environment:
    https://facebook.github.io/react-native/docs/getting-started.html

With emulator & Metro Bundler running, you can edit App.js and reload into the emulator by double-tapping 'r'.

The Metro Bundler window will display React rendering errors, if any:

.. image:: ../images/react_metro_error.png
  :width: 490

You can also view React Native debug information, including results of JavaScript console.log() commands, in Android Studio's logcat:

.. image:: ../images/react_native_debug.png
  :width: 490

React -v- React Native
____

React Native overlaps significantly with React:
- Based on JavaScript
- Use declarative syntax (JSX), components, and props to control application UI
- Use Class components or Hooks to maintain state
- Support other web technologies (e.g. CSS)

However, React Native has significant differences that allow it to integrate with iOS and Android native SDK's.
- Requires platform IDE (or Expo) to compile app
- Has a **core** set of UI components corresponding to native UI components, built to support touch interaction and variable screen size/density
- Can use platform-specific native components

