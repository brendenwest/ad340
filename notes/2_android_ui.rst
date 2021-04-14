Reading
====
- https://developer.android.com/training/basics/firstapp/building-ui
- https://developer.android.com/studio/write/layout-editor.html#intro
- Overview - https://developer.android.com/guide/topics/ui/overview.html 
- Layouts - https://developer.android.com/guide/topics/ui/declaring-layout.html [all sub-categories]
- Input Controls - https://developer.android.com/guide/topics/ui/controls.html
- Styles & Themes - https://developer.android.com/guide/topics/ui/themes.html

Goals
====
Develop understanding & working knowledge of:

- Android UI layouts
- Android Views & ViewGroups
- Common Android input controls
- Populating layouts with dynamic data
- Handling UI events
- Applying UI styles & themes

Practice
====
- https://developer.android.com/training/basics/firstapp/building-ui

Notes
====

- Android UI is composed of Views and ViewGroups
- ViewGroups can contain views or nested ViewGroups
- common Android input controls are subclasses of View or ViewGroup

Layouts
++++
- Layouts can be defined in XML or at run-time. XML is preferred for separation of code & presentation,
- XML element names correspond to class names (eg TextView) and attribute names correspond to methods
- layout XML has a single root element & one or more child elements
- Views must have a unique ID. This is esp. important for relative layouts, where views need to reference their siblings

Run-time
++++
- Android apps 'inflate' a layout by initializing each node into a runtime object
- Activity layout is loaded in the onCreate() method w/ setContentView()
- layouts referenced w/ R.layout.<layout_name>
- View objects inherit attributes from classes they extend and from the View class
- Views must have a unique ID. ID is important for relative layouts, where views need to reference their siblings
- Views can be instantiated at runtime, usually in the onCreate() method like so:

::

	Button myButton = (Button) findViewById(R.id.my_button);

Size & Position
++++
- Views have layout parameters defined by their parent ViewGroup that define their size & position
- A view must define layout params appropriate for its parent
- A view must define **layout_width** & **layout_height**. Can specify exact measures, but relative measures are preferred:
	- match_parent = size to what parent allows
	- wrap_content = size to what content requires
    - gravity = alignment
- Measured size defines a view's dimensions within the parent
- width & height define a view's actual size on the screen

Linear Layout
++++
 - all children aligned in single direction (vertical or horizontal)
 - children can implement **layout_weight** to specify how much space to occupy on screen

Relative Layout
++++
 - child views are positioned relative to parent and each other
 - can eliminate nested view groups and optimize performance

List View
++++
- is a subclass of AdapterView
- is a ViewGroup that displays a scrollable list of items
- items can be view or view group
- populated w/ static content (string-array) or from dynamic data source via Adapter
- can have header, footer, dividers

Dynamic data
++++
- the AdapterView is bound to an adapter instance which retrieves data from an external source and creates a View that represents each data entry.
- Android has two main types of adapters:
    - **ArrayAdapter** - creates a view for each array item by calling toString() on each item and placing the contents in a TextView.
    - **SimpleCursorAdapter** - for cursor data (e.g. DB). You must specify a layout to use for each row in the Cursor and which columns in the Cursor should be inserted into which views of the layout. Changes to underlying data should call notifyDataSetChanged() to refresh the view


Recycler View
++++
- Designed to optimized performance for data sets too large to display all at once
- Uses a layout manager to populate view holders representing individual items
- Creates view holders & binds them to data, via an adapter, as user scrolls
- RecyclerView binds more view holders than are visible, to be ready for scrolling into view. Rebinds those off screen longest
- View holder is re-used instead of recreated or inflated


