# swing-fx-properties
Adaptation of JavaFX properties for Swing.

When JavaFX 2.0 was released, it introduced an improved approach to component properties handling.
In JavaFX component properties are type safe, referenced by method name (and not via string like in Swing) and, what is probably the most significant distinction, support binding.

The JavaFX properties implementation, in fact, is not JavaFX specific - it is a generic property mechanism which can be used for any JavaBean (see javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder).

This project took the relevant pieces of JavaFX properties code and added support for Swing - so a developer may use JavaFx-style properties with Swing components.

Since JavaFX is no longer part of JDK (starting with Java 11), 
