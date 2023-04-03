![Java CI with Maven](https://github.com/parubok/swing-fx-properties/workflows/Java%20CI%20with%20Maven/badge.svg)
[![Latest Version](https://img.shields.io/maven-central/v/io.github.parubok/swing-fx-properties)](https://search.maven.org/search?q=a:swing-fx-properties)
[![javadoc](https://javadoc.io/badge2/io.github.parubok/swing-fx-properties/javadoc.svg)](https://javadoc.io/doc/io.github.parubok/swing-fx-properties)

# swing-fx-properties
Adaptation of [JavaFX properties](https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm) for [Swing](https://en.wikipedia.org/wiki/Swing_(Java)).

JavaFX introduced an improved approach to component properties handling.
In JavaFX, component properties are type safe, referenced by method (and not via string name like in Swing) and, what is probably the most significant improvement, support [binding](https://docs.oracle.com/javase/8/javafx/properties-binding-tutorial/binding.htm).

The JavaFX properties implementation, in fact, is not JavaFX specific - it is a generic property mechanism which can be used for any JavaBean (see [`JavaBeanObjectPropertyBuilder`](https://javadoc.io/doc/io.github.parubok/swing-fx-properties/latest/io/github/parubok/swingfx/beans/property/adapter/JavaBeanObjectPropertyBuilder.html)).

This project took the relevant pieces of JavaFX properties code and added support for Swing - to allow usage of JavaFX-style properties and related functionality with Swing components.

**Note:** The default JavaFX handling of binding evaluation errors was changed from logging the error and returning some default value to throwing [`BindingEvaluationException`](https://javadoc.io/doc/io.github.parubok/swing-fx-properties/latest/io/github/parubok/swingfx/beans/binding/BindingEvaluationException.html). For some cases, this library also provides alternative methods which allow to specify a default value which is returned instead of throwing `BindingEvaluationException`.
Example:
- `Bindings.valueAt(ObservableList<E> op, int index)` - throws [`BindingEvaluationException`](https://javadoc.io/doc/io.github.parubok/swing-fx-properties/latest/io/github/parubok/swingfx/beans/binding/BindingEvaluationException.html) in case of invalid index.
- `Bindings.valueAt(ObservableList<E> op, int index, E defaultValue)` - returns the specified default value in case of invalid index.

The Swing properties are obtained via static methods of class [`SwingPropertySupport`](https://javadoc.io/doc/io.github.parubok/swing-fx-properties/latest/io/github/parubok/fxprop/SwingPropertySupport.html).

Example 1 (bind 'enabled' property of a label to 'selected' property of a checkbox, so the label is disabled when the checkbox is unselected and vice versa):

```java
import static io.github.parubok.fxprop.SwingPropertySupport.enabledProperty;
import static io.github.parubok.fxprop.SwingPropertySupport.selectedProperty;

JCheckBox checkBox = new JCheckBox(); // unselected checkbox
JLabel label = new JLabel();
enabledProperty(label).bind(selectedProperty(checkBox));
// label is now disabled since the checkbox is not selected
checkBox.setSelected(true);
// label is now enabled
```

Example 2 (bind 'enabled' property of an action to 'selectedRowCount' property of a table, so the action is disabled when the table has no selected rows and vice versa):

```java
import static io.github.parubok.fxprop.SwingPropertySupport.enabledProperty;
import static io.github.parubok.fxprop.SwingPropertySupport.selectedProperty;

Action action = ...; // for example, 'delete table rows' action
JTable table = ...;
enabledProperty(action).bind(selectedRowCountProperty(table).greaterThanOrEqualTo(1));
```

The following APIs were added to the original APIs of JavaFX:
- `Bindings.createObjectBinding(ObservableValue<K> value1, ObservableValue<T> value2, BiFunction<K, T, D> func)`
- `Bindings.createObjectBinding(ObservableValue<D> value1, ObservableValue<F> value2, ObservableValue<G> value3, TriFunction<D, F, G, R> func)`
- `Bindings.stringValueAt(ObservableMap<K, String> op, K key, String defaultValue)`
- `Bindings.valueAt(ObservableMap<K, V> op, K key, V defaultValue)`
- `Bindings.valueAt(ObservableList<E> op, int index, E defaultValue)`
- `Bindings.valueAt(ObservableList<E> op, ObservableIntegerValue index, E defaultValue)`
- `ObservableMap.valueAt(K key, V defaultValue)`
- `ObservableList.valueAt(int index, E defaultValue)`
- `ObservableValue.asObject(Function<T, K> func)`
- `ObservableValue.asBoolean(Predicate<T> predicate)`
- `ObservableValue.asStringExpression(String format)`

**Note:** As specified in `bind` [method JavaDoc](https://docs.oracle.com/javase/8/javafx/api/javafx/beans/property/Property.html#bind-javafx.beans.value.ObservableValue-), JavaFX has all the bind calls implemented through weak listeners. This means the bound property can be garbage collected and stopped from being updated unless there is a strong reference pointing to it.

Includes a stand-alone demo `io.github.parubok.fxprop.demo.Demo` under `test` source sub-root. 

Since [JavaFX](https://github.com/openjdk/jfx) is licensed under [GPL v2 with the Classpath exception](http://openjdk.java.net/legal/gplv2+ce.html), the same license applies to this project.

This project has no dependencies (except JUnit 5, for testing).

Requires Java 8 or later.

### Installation

Releases are available in [Maven Central](https://repo1.maven.org/maven2/io/github/parubok/swing-fx-properties/)

#### Maven

Add this snippet to the pom.xml `dependencies` section:

```xml
<dependency>
    <groupId>io.github.parubok</groupId>
    <artifactId>swing-fx-properties</artifactId>
    <version>1.22</version>
</dependency>
```

#### Gradle

Add this snippet to the build.gradle `dependencies` section:

```groovy
implementation 'io.github.parubok:swing-fx-properties:1.22'
```