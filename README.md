# swing-fx-properties
Adaptation of [JavaFX properties](https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm) for Swing.

When JavaFX 2.0 was released, it introduced an improved approach to component properties handling.
In JavaFX, component properties are type safe, referenced by method (and not via string name like in Swing) and, what is probably the most significant distinction, support binding.

The JavaFX properties implementation, in fact, is not JavaFX specific - it is a generic property mechanism which can be used for any JavaBean (see `javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder`).

This project took the relevant pieces of JavaFX properties code and added support for Swing - so a developer may use JavaFX-style properties with Swing components.

**Note:** The default JavaFX handling of binding evaluation errors was changed from logging the error and returning some default value to throwing `BindingEvaluationException`.

The Swing properties are obtained via static methods of class `org.swingfx.SwingPropertySupport`. Currently this class provides support for 17 various properties.

Example 1 (bind 'enabled' property of a label to 'selected' property of a checkbox, so the label is disabled when the checkbox is unselected and vice versa):
```java
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import static org.swingfx.SwingPropertySupport.enabledProperty;
import static org.swingfx.SwingPropertySupport.selectedProperty;

JCheckBox checkBox = new JCheckBox(); // unselected checkbox
JLabel label = new JLabel();
enabledProperty(label).bind(selectedProperty(checkBox));
// label is now disabled since the checkbox is not selected
checkBox.setSelected(true);
// label is now enabled
```

Example 2 (bind 'enabled' property of an action to 'selectedRowCount' property of a table, so the action is disabled when the table has no selected rows and vice versa):
```java
import javax.swing.JTable;
import javax.swing.Action;

import static org.swingfx.SwingPropertySupport.enabledProperty;
import static org.swingfx.SwingPropertySupport.selectedRowCountProperty;

Action action = ...; // e.g. delete table rows
JTable table = ...;
enabledProperty(action).bind(selectedRowCountProperty(table).greaterThanOrEqualTo(1));
```

For convenience, the following APIs were added to the original APIs of JavaFX:
- `Bindings.createObjectBinding(ObservableValue<K> value1, ObservableValue<T> value2, BiFunction<K, T, D> func)`
- `Bindings.stringValueAt(ObservableMap<K, String> op, K key, String defaultValue)`
- `Bindings.valueAt(ObservableMap<K, V> op, K key, V defaultValue)`
- `ObservableMap.valueAt(K key, V defaultValue)`
- `ObservableValue.asObject(Function<T, K> func)`
- `ObservableValue.asBoolean(Predicate<T> predicate)`
- `ObservableValue.asStringExpression(String format)`

Since [JavaFX](https://github.com/openjdk/jfx) is licensed under [GPL v2 with the Classpath exception](http://openjdk.java.net/legal/gplv2+ce.html), the same license applies to this project.

**Important Note:** As specified in `bind` [method JavaDoc](https://docs.oracle.com/javase/8/javafx/api/javafx/beans/property/Property.html#bind-javafx.beans.value.ObservableValue-), JavaFX has all the bind calls implemented through weak listeners. This means the bound property can be garbage collected and stopped from being updated.

Requires Java 8 or later.
