Data Storage & Providers
====

Reading
----
- https://developer.android.com/guide/topics/data/index.html
- https://developer.android.com/training/basics/data-storage/index.html
- https://firebase.google.com/docs/database/android/start

Practice
----
- https://codelabs.developers.google.com/codelabs/android-training-shared-preferences/index.html
- https://codelabs.developers.google.com/codelabs/android-persistence/index.html

Learning Outcomes
----
- storing key-value data with shared preferences
- storing files
- storing relational data on device
- Cloud data storage with Firebase

Overview
----

Android provides several options for storing application data on the device:

- Shared preferences - private primitive data in key-value pairs
- Files:
    - Internal storage -  private data on the device memory
    - External storage - public data on the shared external storage
    - Temporary cache files
- SQLite database - private, on-device relational database

Shared Preferences
----

Android's Shared Preferences class allows read/write access to primitive key-value data (e.g.booleans, floats, ints, longs, and strings) . It is NOT the same as user settings.

An activity can use a single **SharedPreferences** object;
::

    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

or multiple SharedPreferences objects, each identified with a distinct name.
::

    SharedPreferences sharedPref = getActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE);

Data are stored into a SharedPreferences object via an **editor**. Values are inserted with a method specific to the data type (e.g. putString, putBoolean, etc.) and must be **committed** before editing completes:
::

    SharedPreferences settings = getSharedPreferences("mySettings", 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putBoolean(getString(R.string.signed_in_key), bSignedIn);
    editor.apply(); // saves data asynchronously

Values are read from SharedPreferences with type-specific methods such as getBoolean() and getString().
::

    SharedPreferences settings = getActivity().getPreferences(Context.MODE_PRIVATE);
    boolean signedIn = settings.getBoolean(getString(R.string.signed_in_key), False);

File Storage
----
Android applications can files directly on the device's internal storage. By default, files saved to the internal storage are private to the application and not accessible by other applications or the user. Application files are removed when the application is uninstalled.

Android provides an openFileOutput() method, with parameters for file name and privacy mode. This returns a FileOutputStream object you can write to:
::

    String FILENAME = "hello_file";
    String string = "hello world!";

    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
    fos.write(string.getBytes());
    fos.close();

- MODE_PRIVATE will create or replace the file
- MODE_APPEND will append to an existing file

The application can read files with the openFileInput() method, which returns a FileInputStream object.

Android also exposes methods for retrieving directories and file lists:
- getFilesDir()
- getDir()
- fileList()
- deleteFile()

An application can also use *openRawResource()* with R.raw.<filename> to read  a static file saved in the project's *res/raw/* directory at compile time.
