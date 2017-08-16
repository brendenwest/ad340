Reading
----

- https://developer.android.com/training/design-navigation/index.html
- https://developer.android.com/training/implementing-navigation/index.html

- https://developer.android.com/guide/topics/ui/menus.html 
- https://developer.android.com/guide/topics/ui/dialogs.html 
- https://developer.android.com/guide/topics/ui/ui-events.html

Watch
----

- Options menu tutorial - https://www.youtube.com/watch?v=EZ-sNN7UWFU 
- Alert dialog tutorial - https://www.youtube.com/watch?v=xPYINCsIQVg&list

Goals
----
Develop understanding & working knowledge of:

- Android UI event listeners and event handling
- Defining app toolbar & high-level navigation
- Creating options menus 
- Creating contextual & popup menus
- Handling menu events
- Creating modal dialogs for alerts & prompts

Navigation

- app model can be viewed as a tree or graph
- model shows entities & how relationships 
- descendant nav = descend hierarchy to child screen
- lateral nav = access a sibling screen

- siblings may be collection- or section-related
- collection-related = individual items in a parent collection
- section-related = difference sections of parent

- UI patterns
  - buttons / dashboard
  - lists - ideal for collections. nested lists not good.
  - grids
  - carousels
  - stacks (cards)

- temporal nav = back : navigate through activity history, regardless of which app
- ancestral nav = up / home : 
	- navigation w/in an app based on hierarchy
	- Sometimes used to show sidebar nav
	- important to also clear back stack

Action Bar (Toolbar in support v7)
 - sidebar menu
 - App / activity title
 - nav icons
 - overflow nav
 - Toolbar requires activity to extend AppCompatActivity

 - can be hid per activity
 - action buttons defined in XML (/res/menu)
 	- each item has id, icon, title, & showAsAction

 - customized in onCreateOptionsMenu() method
   - add()
   - findItem() to modify w/ MenuItem api
 - action handled in onOptionsItemSelected() method

 -  up action enabled by setting parentActivityName on activity in manifest

 - supports several special action bar widgets
   - action view (e.g. SearchView)
   - action provider (e.g. ShareActionProvider)



 * setup
 * can

Menus
 - defined in XML
 - inflated w/ MenuInflater
 - items can be grouped
 - items can be radio or checkboxes w/ checkableBehavior attribute

Options Menu
- menu items for an activity

Context menu 
- floating menu associated w/ an item and invoked via long-click. Provides actions that affect the item.
- registered on items w/ registerForContextMenu()
- created w/ onCreateContextMenu()
- acted w/ onContextItemSelected()

- contextual action bar (ActionMode) preferred
  - implemented in ActionMode.Callback
  - enabled with startActionMode()
  - can be enabled for batch actions in ListView or GridView

Popup menu
- items anchored to a view that invoked the menu

Dialogs
- modal window that prompts the user
- usually implemented as subclass (AlertDialog, DatePickerDialog, TimePickerDialog)
- usually implemented w/in DialogFragment, which handles lifecycle events
- uses Builder class to construct dialog
- Alert dialog has 3 customizable regions - title, content, & action buttons
- can show list of options in content area
- activities can implement a listener interface to receive dialog click events


Practice
 - map out screen diagram w/ entities & relationships
 - add an About activity
 - implement a toolbar with menu items
 - set different menu options on main, about, and other child screens
 - add a contextual action bar one of your screens
 - enable a popup menu
 - enable a dialog w/ 'ok' & 'cancel' buttons

