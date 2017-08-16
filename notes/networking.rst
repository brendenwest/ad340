
Reading
----

- https://developer.android.com/training/basics/network-ops/index.html
- https://developer.android.com/training/volley/index.html
- https://developer.android.com/training/id-auth/authenticate.html
- https://developers.google.com/youtube/v3/quickstart/android (optional)
- http://www.baeldung.com/jackson-vs-gson


Watch
----

- https://developer.android.com/training/volley/index.html

Sample app
----
https://github.com/googlesamples/android-NetworkConnect

Goals
----

- connect a network
- perform network operations
- monitor device network status
- load & parse XML or JSON data


Android Networking
----

The Android SDK enables applications to perform network operations, but with some constraints.

Network requests must be made off the main UI thread, and the application manifest must include these permissions:

<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

Network operations should also:

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

With Android 7.0, apps can be notified of Data Saver restrictions the user has set on background/foreground data operations.


Parsing Data
----

Apps can use XmlPullParser to parse an XML response. The parser extracts data for fields of interest and associates this with Java data structures.

Parse routine should be wrapped in aa try ... catch block to handle parsing exceptions.

XML parsing is a forward-only read operation and supports just a sub-set of XML DOM methods.

Network Optimization
----

- bundled transfers can minimize time when radio is at full power
- pre-fetching can reduce tranfers and improve in-app UI, but has risk of unnecessary downloads
- cloud-messaging can replace polling to notify app if server content has changed

- limit data returned from server
- cache files locally
- Use the HttpsURLConnection Response Cache

Object Mapping
----

- Deserialization converts JSON input into Java objects
- Serialization converts Java objects to JSON output

Common libraries - Gson, Jackson
- http://www.baeldung.com/jackson-vs-gson



Terms
----
- request queue
- main (UI) thread
- worker thread
- singleton
- Gson


Practice
----

- load HTML from network & display in textview
- load JSON from network & update a list/recycler view
- load image from url

Helper Libraries
----

- https://github.com/bumptech/glide
- http://square.github.io/okhttp/ 
