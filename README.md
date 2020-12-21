# Custom Popup Menu
> A customized library module that utilizes the ```android.widget.PopupWindow``` class coupled with ```android.widget.LinearLayout``` to display a beautiful, modern, and intuitive popup menu with icons.
## Usage
### Implementation into an Android Studio Project
Add the following lines into your build.gradle file
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Now implement this project using 
```
dependencies {
	        implementation 'com.github.HafizYasir:CustomPopupMenu:Tag'
	}
```

### Basic Usage
A demo app is provided in this repository. You can take a look at it.

The Custom Popup Menu utilizes the Builder Pattern and you can use the ```CustomPopupMenu.MenuBuilder``` class to create an instance. Doing so requires the following values:
- A ```Context``` instance to create the menu with.
- A ```View``` to anchor the popup menu with, along with three ```int``` values for ```xOffset```, ```yOffset```, and ```gravity```. The last of these can be obtained from the ```android.View.Gravity``` class.
- An interface instance of type ```CustomPopupMenu.IMenuItemClickListener``` used to pass click events. The parameter ```int id``` of the interface method ```menuItemClicked``` contains the menu item id you have declared in your ```R.menu.``` exampleMenu file.
- In the ```CustomPopupMenu.MenuBuilder.setMenuResources(Integer... menuResources)``` method, pass in the menu resources declared using xml in your ```R.menu``` file. Multiple menu files can be passed by separating with commas and when the menu is shown, items of each file will be separated using a horizontal line.

### Adding a horizontal separator 
To add a horizontal separator between two menu items, declare an item like you normally do in the xml file. In place of the ```android:id="@+id/someId"``` attribute, use the id ```@id/cpm_add_underline``` **without the plus sign after the @ symbol**.
#### Example of separator
```<item android:id="@id/cpm_add_underline" android:title="Type anything here. It will be ignored"/>```


### Example
```
new CustomPopupMenu.MenuBuilder(context)
                .setAnchorAndParams(anchorView, xOffset, yOffset, Gravity.NO_GRAVITY)
                .setClickListener(menuListener)
                .setMenuResources(resource_one, resource_two)
                .build().showMenu()
```

## Screenshots
[![Screenshot](https://github.com/HafizYasir/CustomPopupMenu/blob/master/CustomPopupMenu/src/main/res/mipmap/example_two.jpg "Screenshot")](https://github.com/HafizYasir/CustomPopupMenu/blob/master/CustomPopupMenu/src/main/res/mipmap/example_two.jpg "Screenshot")
