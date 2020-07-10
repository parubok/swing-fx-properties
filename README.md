# swing-fx-properties
Adaptation of [JavaFX properties](https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm) for Swing.

JavaFX introduced an improved approach to component properties handling.
In JavaFX, component properties are type safe, referenced by method (and not via string name like in Swing) and, what is probably the most significant improvement, support [binding](https://docs.oracle.com/javase/8/javafx/properties-binding-tutorial/binding.htm).

The JavaFX properties implementation, in fact, is not JavaFX specific - it is a generic property mechanism which can be used for any JavaBean (see `javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder`).

This project took the relevant pieces of JavaFX properties code and added support for Swing - to allow usage of JavaFX-style properties and related functionality with Swing components.

**Note:** The default JavaFX handling of binding evaluation errors was changed from logging the error and returning some default value to throwing `BindingEvaluationException`. For some cases, this library also provides alternative methods which allow to specify a default value which is returned instead of throwing `BindingEvaluationException`.
Example:
- `Bindings.valueAt(ObservableList<E> op, int index)` - throws `BindingEvaluationException` in case of invalid index.
- `Bindings.valueAt(ObservableList<E> op, int index, E defaultValue)` - returns the specified default value in case of invalid index.

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

The following APIs were added to the original APIs of JavaFX:
- `Bindings.createObjectBinding(ObservableValue<K> value1, ObservableValue<T> value2, BiFunction<K, T, D> func)`
- `Bindings.createObjectBinding(ObservableValue<D> value1, ObservableValue<F> value2, ObservableValue<G> value3, TriFunction<D, F, G, R> func)`
- `Bindings.stringValueAt(ObservableMap<K, String> op, K key, String defaultValue)`
- `Bindings.valueAt(ObservableMap<K, V> op, K key, V defaultValue)`
- `Bindings.valueAt(ObservableList<E> op, int index, E defaultValue)`
- `ObservableMap.valueAt(K key, V defaultValue)`
- `ObservableList.valueAt(int index, E defaultValue)`
- `ObservableValue.asObject(Function<T, K> func)`
- `ObservableValue.asBoolean(Predicate<T> predicate)`
- `ObservableValue.asStringExpression(String format)`

**Note:** As specified in `bind` [method JavaDoc](https://docs.oracle.com/javase/8/javafx/api/javafx/beans/property/Property.html#bind-javafx.beans.value.ObservableValue-), JavaFX has all the bind calls implemented through weak listeners. This means the bound property can be garbage collected and stopped from being updated.

Since [JavaFX](https://github.com/openjdk/jfx) is licensed under [GPL v2 with the Classpath exception](http://openjdk.java.net/legal/gplv2+ce.html), the same license applies to this project.

Requires Java 8 or later.
