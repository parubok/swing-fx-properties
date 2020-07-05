package org.swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.ListProperty;
import swingfx.beans.property.ObjectProperty;
import swingfx.beans.property.ReadOnlyBooleanProperty;
import swingfx.beans.property.ReadOnlyIntegerProperty;
import swingfx.beans.property.StringProperty;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
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
        return ComponentEnabledPropertyImpl.getProperty(component);
    }

    /**
     * @return Property object for 'enabled' property of the specified action.
     * @see Action#setEnabled(boolean)
     * @see Action#isEnabled()
     */
    public static BooleanProperty enabledProperty(Action action) {
        return ActionEnabledPropertyImpl.getProperty(action);
    }

    /**
     * @return Property object for 'visible' property of the specified component.
     * @see JComponent#isVisible()
     * @see JComponent#setVisible(boolean)
     */
    public static BooleanProperty visibleProperty(JComponent component) {
        return VisiblePropertyImpl.getProperty(component);
    }

    /**
     * @return Property object for 'selected' property of the specified abstract button (e.g. {@link javax.swing.JCheckBox}, {@link javax.swing.JRadioButton}, etc).
     * @see AbstractButton#setSelected(boolean)
     * @see AbstractButton#isSelected()
     */
    public static BooleanProperty selectedProperty(AbstractButton absButton) {
        return SelectedPropertyImpl.getProperty(absButton);
    }

    /**
     * @return Property object for 'text' property of the specified label.
     * @see JLabel#setText(String)
     * @see JLabel#getText()
     */
    public static StringProperty textProperty(JLabel label) {
        return TextPropertyImpl.getProperty(label);
    }

    /**
     * @return Property object for 'icon' property of the specified label.
     * @see JLabel#setIcon(Icon)
     * @see JLabel#getIcon()
     */
    public static ObjectProperty<Icon> iconProperty(JLabel label) {
        return IconPropertyImpl.getProperty(label);
    }

    /**
     * @param table Table. Not null.
     * @return Read-only property which value is the number of selected rows in the table.
     * @see JTable#getSelectedRowCount()
     */
    public static ReadOnlyIntegerProperty selectedRowCountProperty(JTable table) {
        return SelectedRowCountPropertyImpl.getProperty(table);
    }

    /**
     * @param tree Tree. Not null.
     * @return Read-only property which value is the number of selected rows in the tree.
     * @see JTree#getSelectionCount()
     */
    public static ReadOnlyIntegerProperty selectionCountProperty(JTree tree) {
        return SelectionCountPropertyImpl.getProperty(tree);
    }

    /**
     * @param table Table. Not null.
     * @return Read-only property which value is the number of rows in the table model.
     */
    public static ReadOnlyIntegerProperty modelRowCountProperty(JTable table) {
        return ModelRowCountPropertyImpl.getProperty(table);
    }

    /**
     * @param component Component. Not null.
     * @return Read-only property which value is {@code true} when the component has focus and {@code false} when not.
     * @see JComponent#hasFocus()
     */
    public static ReadOnlyBooleanProperty focusedProperty(JComponent component) {
        return FocusedPropertyImpl.getProperty(component);
    }

    /**
     * @param component Component. Not null.
     * @return Property object for 'foreground' property of the specified component.
     * @see JComponent#setForeground(Color)
     * @see JComponent#getForeground()
     */
    public static ObjectProperty<Color> foregroundProperty(JComponent component) {
        return ForegroundPropertyImpl.getProperty(component);
    }

    /**
     * @param component Component. Not null.
     * @return Property object for 'background' property of the specified component.
     * @see JComponent#setBackground(Color)
     * @see JComponent#getBackground()
     */
    public static ObjectProperty<Color> backgroundProperty(JComponent component) {
        return BackgroundPropertyImpl.getProperty(component);
    }

    /**
     * @param comboBox Combo box. Not null.
     * @return Property object for 'selectedItem' property of the specified combo box. Value {@code null} of the property means no selection.
     * @see JComboBox#setSelectedItem(Object)
     * @see JComboBox#getSelectedItem()
     */
    public static <E> ObjectProperty<E> selectedItemProperty(JComboBox<E> comboBox) {
        return SelectedItemPropertyImpl.getProperty(comboBox);
    }

    /**
     * @param component Component. Not null.
     * @return Boolean property which is {@code true} when the mouse cursor is over the component.
     */
    public static ReadOnlyBooleanProperty mouseOverProperty(JComponent component) {
        return MouseOverPropertyImpl.getProperty(component);
    }

    /**
     * @param component Component. Not null.
     * @return Property object for border of the specified component. Null is a valid value for border property.
     * @see JComponent#setBorder(Border)
     * @see JComponent#getBorder()
     */
    public static ObjectProperty<Border> borderProperty(JComponent component) {
        return BorderPropertyImpl.getProperty(component);
    }

    /**
     * @param textComponent Text component which validness will be represented by the property. To support validness
     * checks, the component must have its {@link javax.swing.InputVerifier} properly configured. The property's value
     * will be {@code true} if the input verifier is {@code null}.
     * <p>
     * <b>Note:</b> The property value will be refreshed on every change in the underlying document.
     * </p>
     *
     * @return Boolean property which represents validness of the text component (as defined by its {@link javax.swing.InputVerifier}).
     * @see JComponent#setInputVerifier(InputVerifier)
     * @see JComponent#getInputVerifier()
     */
    public static ReadOnlyBooleanProperty validInputProperty(JTextComponent textComponent) {
        return ValidInputPropertyImpl.getProperty(textComponent);
    }

    /**
     * <p>
     * <b>Note 1</b>: In order to work properly, the selection mode of the table should be set to {@link javax.swing.ListSelectionModel#MULTIPLE_INTERVAL_SELECTION}.
     * </p>
     * <p>
     * <b>Note 2:</b> Order of values in the list is not guaranteed to be identical to the order returned by {@link JTable#getSelectedRows()}.
     * </p>
     *
     * @param table Table. Not null.
     * @return Property which value is indexes of the selected rows of the provided table.
     * @see JTable#getSelectedRows()
     * @see javax.swing.ListSelectionModel#setSelectionMode(int)
     */
    public static ListProperty<Integer> selectedRowsProperty(JTable table) {
        return SelectedRowsPropertyImpl.getProperty(table);
    }
}
