Reading
----

- Overview - https://developer.android.com/guide/topics/ui/overview.html 
- Layouts - https://developer.android.com/guide/topics/ui/declaring-layout.html 
- Input Controls - https://developer.android.com/guide/topics/ui/controls.html
- Styles & Themes - https://developer.android.com/guide/topics/ui/themes.html 
- https://developer.android.com/guide/topics/resources/layout-resource.html

Goals
----
Develop understanding & working knowledge of:

- Android View & ViewGroup objects
- Common Android layouts
- Common Android input controls
- Populating layouts with dynamic data
- Click Event handlers
- Applying UI styles & themes

Practice
----
- create a linear layout 
	- w/ vertical orientation
	- w/ horizontal orientation
	- w/ children having varied layout weights
- create a relative layout
	- w/ children relative to parent
	- w/ children relative to each other
- create a web view
- create view group w/ exact sizes
- create view group w/ relative sizes
- log element location & size at runtime 
- create list view:
	- w/ header
	- w/ divider
	- w/ 2-item views


Overview
----

- Android UI defined by Views and ViewGroups
- ViewGroups can contain views or nested ViewGroups
- common Android input controls are subclasses of View or ViewGroup


- Layouts can be defined in XML or at run-time. XML preferred for separation of code & presentation, 
- XML element names correspond to class names (eg TextView) and attribute names correspond to methods
- Android apps 'inflate' a layout by initializing each node into a runtime object
- layout XML has a single root element & one or more child elements

- Activity layout is loaded in the onCreate() method w/ setContentView()
- layouts referenced w/ R.layout.<layout_name>

- View objects inherit attributes from classes they extend and from the View class
- Views must have a unique ID. ID is important for relative layouts, where views need to reference their siblings
- Views can be instantiated at runtime, usually in the onCreate() method like so:

	Button myButton = (Button) findViewById(R.id.my_button);

- Views have layout parameters defined by their parent ViewGroup that define their size & position
- A view must define layout params appropriate for its parent
- A view must define layout_width & layout_height. Can specify exact measures, but relative measures are preferred
	- match_parent = size to what parent allows
	- wrap_content = size to what content requires
- gravity = alignment

- Measured size defines a view's dimensions w/in parent
- width & height define a view's actual size on the screen

Linear Layout
 - all children aligned in single direction (vertical or horizontal)
 - children can implement layout_weight to specify how much space to occupy on screen

 Relative Layout
 - child views are positioned relative to parent and each other
 - can eliminate nested view groups and optimize performance

List View
- subclass of AdapterView
- view group that displays scrollable list of items
- items can be view or view group
- populated w/ static content (string-array) or from dynamic data source via Adapter
- can have header, footer, dividers

Dynamic data
- AdapterView bound to adapter instance which retrieves data from an external source and creates a View that represents each data entry.
- ArrayAdapter - creates a view for each array item by calling toString() on each item and placing the contents in a TextView.
- SimpleCursorAdapter - for cursor (e.g. DB) data. You must specify a layout to use for each row in the Cursor and which columns in the Cursor should be inserted into which views of the layout. Changes to underlying data should call notifyDataSetChanged() to refresh the view


Recycler View
- display items from data set too large to display all at once, to optimize performance
- RecyclerView uses layout manager to fill with view holders representing individual items
- RecyclerView creates view holders & binds them to data as user scrolls, using an adapter
- RecyclerView binds more view holders than are visible, to be ready for scrolling into view. Rebinds those off screen longest
- View holder is re-used instead of recreated or inflated



