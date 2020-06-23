package swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.ObjectProperty;
import swingfx.beans.property.ReadOnlyBooleanProperty;
import swingfx.beans.property.ReadOnlyIntegerProperty;
import swingfx.beans.property.StringProperty;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import java.awt.Color;

/**
 * Collection of static methods which provide access to various properties of Swing components via JavaFX-style
 * property objects.
 */
public class SwingPropertySupport {

    private SwingPropertySupport() {
    }

    /**
     * @return Property object for 'enabled' property of the specified component.
     * @see JComponent#setEnabled(boolean)
     * @see JComponent#isEnabled()
     */
    public static BooleanProperty enabledProperty(JComponent component) {
        return ComponentEnabledPropertyImpl.enabledProperty(component);
    }

    /**
     * @return Property object for 'enabled' property of the specified action.
     * @see Action#setEnabled(boolean)
     * @see Action#isEnabled()
     */
    public static BooleanProperty enabledProperty(Action action) {
        return ActionEnabledPropertyImpl.enabledProperty(action);
    }

    /**
     * @return Property object for 'visible' property of the specified component.
     * @see JComponent#isVisible()
     * @see JComponent#setVisible(boolean)
     */
    public static BooleanProperty visibleProperty(JComponent component) {
        return VisiblePropertyImpl.visibleProperty(component);
    }

    /**
     * @return Property object for 'selected' property of the specified abstract button (e.g. {@link javax.swing.JCheckBox}, {@link javax.swing.JRadioButton}, etc).
     * @see AbstractButton#setSelected(boolean)
     * @see AbstractButton#isSelected()
     */
    public static BooleanProperty selectedProperty(AbstractButton absButton) {
        return SelectedPropertyImpl.selectedProperty(absButton);
    }

    /**
     * @return Property object for 'text' property of the specified label.
     * @see JLabel#setText(String)
     * @see JLabel#getText()
     */
    public static StringProperty textProperty(JLabel label) {
        return TextPropertyImpl.textProperty(label);
    }

    /**
     * Note: the returned property correctly handles change of the table selection model.
     *
     * @param table Table. Not null.
     * @return Read-only property which value is the number of selected rows in the table.
     * @see JTable#getSelectedRowCount()
     */
    public static ReadOnlyIntegerProperty selectedRowCountProperty(JTable table) {
        return SelectedRowCountPropertyImpl.selectedRowCountProperty(table);
    }

    /**
     * Note: the returned property correctly handles change of the tree selection model.
     *
     * @param tree Tree. Not null.
     * @return Read-only property which value is the number of selected rows in the tree.
     * @see JTree#getSelectionCount()
     */
    public static ReadOnlyIntegerProperty selectionCountProperty(JTree tree) {
        return SelectionCountPropertyImpl.selectionCountProperty(tree);
    }

    /**
     * Note: the returned property correctly handles change of the table model.
     *
     * @param table Table. Not null.
     * @return Read-only property which value is the number of rows in the table model.
     */
    public static ReadOnlyIntegerProperty modelRowCountProperty(JTable table) {
        return ModelRowCountPropertyImpl.modelRowCountProperty(table);
    }

    /**
     * @param component Component. Not null.
     * @return Read-only property which value is {@code true} when the component has focus and {@code false} when not.
     * @see JComponent#hasFocus()
     */
    public static ReadOnlyBooleanProperty focusedProperty(JComponent component) {
        return FocusedPropertyImpl.focusedProperty(component);
    }

    /**
     * @param component Component. Not null.
     * @return Property object for 'foreground' property of the specified component.
     * @see JComponent#setForeground(Color)
     * @see JComponent#getForeground()
     */
    public static ObjectProperty<Color> foregroundProperty(JComponent component) {
        return ForegroundPropertyImpl.foregroundProperty(component);
    }

    /**
     * @param component Component. Not null.
     * @return Property object for 'background' property of the specified component.
     * @see JComponent#setBackground(Color)
     * @see JComponent#getBackground()
     */
    public static ObjectProperty<Color> backgroundProperty(JComponent component) {
        return BackgroundPropertyImpl.backgroundProperty(component);
    }

    /**
     * @param comboBox Combo box. Not null.
     * @return Property object for 'selectedItem' property of the specified combo box. Value {@code null} of the property means no selection.
     * @see JComboBox#setSelectedItem(Object)
     * @see JComboBox#getSelectedItem()
     */
    public static <E> ObjectProperty<E> selectedItemProperty(JComboBox<E> comboBox) {
        return SelectedItemPropertyImpl.selectedItemProperty(comboBox);
    }
}
