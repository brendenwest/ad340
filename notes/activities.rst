Android Activities & Intents
====

Reading
====

- https://developer.android.com/guide/components/fundamentals.html
- https://developer.android.com/guide/components/intents-filters.html
- https://developer.android.com/guide/components/activities/index.html
- https://www.tutorialspoint.com/android/android_acitivities.htm 

Goals
====

- Understand Android Activities & Intents
- Understand the Activity life cycle
- Define new activities & intents in the application manifest 
- Navigate between activities, with & without messages
 

Exercise
====

- https://developer.android.com/training/basics/firstapp/building-ui.html
- https://developer.android.com/training/basics/firstapp/starting-activity.html


Activities
====

Activities are main building block of Android applications and typically manage the UI of a single application screen. 

Classes corresponding to a screen subclass Android's Activity class. The initial application screen is usually referred to as the MainActivity.

Activities are defined in the application's AndroidManifest.xml.

An application's activities are loosely bound and have minimal dependencies. Individual activities can be started by other applications if so designed.

 

Activity Lifecycle
====

Each Android app is launched as a system task with it's own stack.

Activities are added to the stack when launched, and removed from the stack when the user navigates 'back'.

Activities progress through a 'lifecycle' when launched, or removed, or during certain application events. Android fires lifecycle events that can be handled in code:

- onCreate() - classes must implement this callback to initialize the essential components of the activity,
- onStart() - the activity is visible but not yet interactive,
- onResume() - the activity is ready for user interaction,
- onPause() - called when the user leaves the activity or the app enters background. Activity may still be visible to user,
- onStop() - called when activity is no longer visible to user,
- onDestroy() - called before an activity is destroyed. Final place to ensure activity resources are released.  

Stopped or paused activities may be restarted with the state they had when stopped.

Intents - https://developer.android.com/guide/components/intents-filters.html (Links to an external site.)Links to an external site. 

Android activities are launched via intents - a message object that describes the activity to start and carries any necessary data.

Android supports two types of intents:

- explicit - specifies the component to start by name
- implicit - declares a general action to perform. may be satisfied by any application that supports that intent. An activity defined with no intent filters can be started only with an explicit intent.

Intents carry information that Android uses to launch an activity:

- component name - The name of the component to start
action - A string that specifies the generic action to perform. Usually an action constant defined by the Intent (Links to an external site.)Links to an external site. class, but can be custom.
- data - The URI that references the data to be acted on and/or the MIME type of that data.
- extras - Key-value pairs that carry additional information required to accomplish the requested action. The Intent class specifies many EXTRA_* constants for standardized data types.
 
Intents are passed to startActivity() or startActivityForResult() 