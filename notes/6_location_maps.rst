Location & Maps
====

Reading
----
- https://developer.android.com/training/location/
- https://developers.google.com/android/guides/setup
- https://developers.google.com/android/guides/api-client
- https://developer.android.com/training/maps/
- https://developers.google.com/maps/documentation/android-sdk/intro

Practice
----
- https://developers.google.com/maps/documentation/android-sdk/map-with-marker

Learning Outcomes
----
Understand how to use Android device location and map capabilities for:

- getting last known location
- requesting device location
- changing location settings
- receiving location updates
- converting location to an address
- displaying maps & current location

Location Overview
----
Android provides several approaches for detecting the device location, with Google Play Services being the preferred approach.

Android applications must specify in the AndroidManifest.xml what location permissions are needed. Android provides different permissions for different granularities of location:

::
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

Last known location
----
Applications can obtain the last known device location. This location value can be updated by any app on the device and is cached to minimize network requests.

Location updates
----
Applications can request periodic updates of location. Developers need to consider frequency of updates, to optimize battery performance and ensure update requests are cancelled when the activity is stopped.

Checklist
----
- location permissions in manifest
- handler to check that location permission was granted
- Google Play services in build.gradle (if using maps or geocoding)
- Google Maps api key (if using maps)