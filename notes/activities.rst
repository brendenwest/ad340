Android Activities & Intents

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


Activities:
- custom apps subclass activity
- activity usually corresponds to screen, but may be smaller & float above other windows
- app activities are loosely bound
- activities can start activities in other apps
- started via explicit or implicit intents
- receives result as an Intent object in the onActivityResult() callback
- each app launched from homescreen is a task, with activities in LIFO stack


Intents:
- message object that describes the activity to start and carries any necessary data
- passed to startActivity() or startActivityForResult()
- an activity with no intent filters for can be started only with an explicit intent
- intent action can be custom, but usually those defined in Intent class
- Extras - key-value pairs of additional info required for the action. Bundle example?
- The Intent class specifies many EXTRA_* constants for standardized data types
- Context



XML
- tags
- attributes
- namespaces
