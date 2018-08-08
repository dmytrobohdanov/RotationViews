# RotationViews
To use this you should create class extends `RotationistsAdapter`, make instance of our class and call `initAdapter()`

### Views
`RotationistsAdapter` has deal with set of 2 types of views, the rights one and the lefts one. 
Both views should extend `RotationistsAdapter.ViewHolder`. It is not nessasary to use same views on left and right side

Your main view that holds this item should contain 2 `RelativeLayout`s as a holder for left and right set of views. 
Views will add on top of content of `RelativeLayout`, you can put whatever there but should be prepeared for this

### Data
you can use any data for you app, just bind data and view in `onBindLeftViewHolder` and `onBindRightViewHolder` abstract methods of `RotationistsAdapter` class

### Settings
you can manipulate animation's settings by editing `Settings` class, you can change speed, return time, angles there
