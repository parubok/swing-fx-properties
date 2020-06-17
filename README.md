# swing-fx-properties
Adaptation of JavaFX properties for Swing.

When JavaFX 2.0 was released, it introduced an improved approach to component properties handling.
In JavaFX, component properties are type safe, referenced by method (and not via string name like in Swing) and, what is probably the most significant distinction, support binding.

The JavaFX properties implementation, in fact, is not JavaFX specific - it is a generic property mechanism which can be used for any JavaBean (see `javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder`).

This project took the relevant pieces of JavaFX properties code and added support for Swing - so a developer may use JavaFX-style properties with Swing components.

Example (bind 'enabled' property of a label to 'selected' property of a checkbox, so the label is disabled when the checkbox is unselected and vice versa):
```java
import static swingfx.SwingPropertySupport.enabledProperty;
import static swingfx.SwingPropertySupport.selectedProperty;

JCheckBox checkBox = new JCheckBox(); // unselected checkbox
JLabel label = new JLabel();
enabledProperty(label).bind(selectedProperty(checkBox));
// label is now disabled since the checkbox is not selected
checkBox.setSelected(true);
// label is now enabled
```

Since [JavaFX](https://github.com/openjdk/jfx) is licensed under GPL v2 with the Classpath exception, the same license applies to this project.

Requires Java 8.
