Location & Maps
====

Reading
----
- https://developer.android.com/training/location/
- https://developer.android.com/training/permissions/requesting
- https://developers.google.com/android/guides/setup
- https://developers.google.com/android/guides/api-client
- https://developer.android.com/training/maps/
- https://developers.google.com/maps/documentation/android-sdk/intro

Practice
----
- https://developers.google.com/maps/documentation/android-sdk/map-with-marker
- https://developers.google.com/codelabs/maps-platform/maps-platform-101-android#0

Learning Outcomes
----
Understand how to use Android device location and map capabilities for:

- Obtaining device location
- changing location settings
- receiving location updates
- converting location to an address
- displaying maps & markers

Locations
====
Android provides several approaches for detecting the device location, with Google Play Services being the preferred approach.

Developers using device location must account for a number of considerations;
- user permissions
- location accuracy
- update frequency

High accuracy and frequent updates incur a power consumption cost.

Permissions
----
Android applications must specify their location requirements in the AndroidManifest.xml. Android provides different permissions for different granularities of location:
::

    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

- COARSE_LOCATION = accuracy to within a city block
- FINE_LOCATION = accuracy can be within a few meters. requires more

Android 10+ requires apps to request location permissions at run-time, so apps need to account for this possibility.

Device Location
----
Applications can quickly obtain the last known device location. This value might be updated by any app on the device and is cached to minimize network requests.


Location updates
++++
Applications can request periodic updates of device location if needed. Developers need to consider frequency of updates, to optimize battery performance, and ensure update requests are cancelled when the activity is stopped.


Maps
====
Android applications can show maps via different providers, but the Android SDK is optimized to work with Google Maps.

This approach requires a **Google Maps API key** and the **Google Play Services maps sdk**.

Once your application is configured, it can display a map within an activity using the **SupportMapFragment** container to load the map asynchronously.

The fragment invokes an **onMapReady** callback once the map has loaded, where your application can customize the map (e.g. zoom level, centering, markers, etc.)
