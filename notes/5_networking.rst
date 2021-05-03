Network Operations
====

Reading
----
- https://developer.android.com/guide/topics/connectivity
- https://developer.android.com/training/basics/network-ops/
- https://developer.android.com/training/volley/index.html
- https://developer.android.com/training/efficient-downloads/
- https://github.com/google/gson/blob/master/UserGuide.md
- https://docs.google.com/presentation/d/1osOpeT2U4JnJpSP4YUk7SJ6qAsqxm4FIRw0q6CEvLRo/edit

Practice
----
- https://developer.android.com/codelabs/kotlin-android-training-internet-data#0

Goals
----
Understand Android best practices for:

- connecting to a network
- performing asynchronous network operations
- monitoring device network status
- loading & parsing XML or JSON data
- Mapping data to Java objects & vice versa


Android Networking
----

The Android SDK enables applications to perform network operations, but with some constraints.

Network requests must be made off the main UI thread, and the application manifest must include these permissions:

::
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

Network operations should also:

- check network connectivity
- ensure application data security,
- minimize network usage
- optimize battery consumption

Android applications can use the standard SDK features to perform network requests, but 3rd party libraries such as Volley can be useful for common types of requests.

If using the Android SDK, applications can use a headless fragment encapsulate asynchronous network operations. The containing activity should also implement the 'DownloadCallback' interface to handle network response.

A headless fragment doesn't reference any UI elements and is used only to encapsulate logic and handle lifecycle events. 

If using an AsyncTask for downloads, the application must ensure the task is cancelled if the containing activity is destroyed before the task completes.

Also, the fragment needs to handle configuration changes such as device rotation with setRetainInstance(true).

The AsyncTask receives a callback parameter for providing results back to the main UI thread. Task should return either valid results or error information.

AsycTask supports onPreExecute() event where the application can check network status.

The HttpsURLConnection API enables network connection and requests. It receives the response body as an InputStream, so your application needs to decode or convert it into a target data type (e.g. String).

Typical sequence of events are:

- The Activity starts a NetworkFragment and passes in a specified URL.
- When a user action triggers the Activity's downloadData() method, the NetworkFragment executes the DownloadTask.
- The AsyncTask method onPreExecute() runs first (on the UI thread) and cancels the task if the device is not connected to the Internet.
- The AsyncTask method doInBackground() then runs on the background thread and calls the downloadUrl() method.
- The downloadUrl() method takes a URL string as a parameter and uses an HttpsURLConnection object to fetch the web content as an InputStream.
- The InputStream is passed to the readStream() method, which converts the stream to a string.
- Finally, once the background work is complete, the AsyncTask's onPostExecute() method runs on the UI thread and uses the DownloadCallback to send the result back to the UI as a String.

More complex applications can use AsyncTaskLoader for more complex applications with multiple downloads.

Managing Network State
----
Before you perform network operations, it's good practice to check the state of network connectivity. Among other things, this could prevent your app from inadvertently using the wrong radio. If a network connection is unavailable, your application should respond gracefully. To check the network connection, you typically use the following classes:

- ConnectivityManager: Answers queries about the state of network connectivity. It also notifies applications when network connectivity changes.
- NetworkInfo: Describes the status of a network interface of a given type (currently either Mobile or Wi-Fi).

Since Android 7.0, apps can be notified of Data Saver restrictions the user has set on background/foreground data operations.


Parsing Data
----

Apps can use XmlPullParser to parse an XML response. The parser extracts data for fields of interest and associates this with Java data structures.

Parse routine should be wrapped in aa try ... catch block to handle parsing exceptions.

XML parsing is a forward-only read operation and supports just a sub-set of XML DOM methods.

JSON parsing is somewhat simpler, using classes provided in the Android SDK as described here - https://www.tutorialspoint.com/android/android_json_parser.htm (Links to an external site.)

Object Mapping
----
Applications working with network data often need to convert network response data to Java objects or vice versa.

- **Deserialization** - converts network response input into Java objects
- **Serialization** - converts Java objects to string output for submission on a network request

Android developers will often use either `Gson<https://github.com/google/gson>`_ or `Jackson<http://tutorials.jenkov.com/java-json/jackson-objectmapper.html>`_  for serialization/deserialization.

Network Optimization
----

- bundled transfers can minimize time when radio is at full power
- pre-fetching can reduce tranfers and improve in-app UI, but has risk of unnecessary downloads
- cloud-messaging can replace polling to notify app if server content has changed

- limit data returned from server
- cache files locally
- Use the HttpsURLConnection Response Cache

Helper Libraries
----

- https://github.com/google/gson
- https://github.com/FasterXML/jackson
- https://github.com/bumptech/glide
- http://square.github.io/okhttp/ 
- https://square.github.io/picasso/
