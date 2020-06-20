package swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.ReadOnlyBooleanProperty;
import swingfx.beans.property.ReadOnlyBooleanPropertyBase;
import swingfx.beans.property.ReadOnlyIntegerProperty;
import swingfx.beans.property.ReadOnlyIntegerPropertyBase;
import swingfx.beans.property.SimpleBooleanProperty;
import swingfx.beans.property.SimpleStringProperty;
import swingfx.beans.property.StringProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.util.Objects;

/**
 * Collection of static methods which provide access to various properties of Swing components via JavaFX-style
 * property objects.
 */
public class SwingPropertySupport {

    private static final String PROP_ENABLED = "swingfx-property-enabled";
    private static final String PROP_TEXT = "swingfx-property-text";
    private static final String PROP_SELECTED = "swingfx-property-selected";
    private static final String PROP_VISIBLE = "swingfx-property-visible";
    private static final String PROP_FOCUSED = "swingfx-property-focused";
    private static final String PROP_SELECTED_ROW_COUNT = "swingfx-property-selected-row-count";
    private static final String PROP_TABLE_MODEL_ROW_COUNT = "swingfx-property-table-model-row-count";

    private SwingPropertySupport() {
    }

    // enabled:
    private static final PropertyChangeListener SWING_PROP_LISTENER_ENABLED = e -> {
        JComponent component = (JComponent) e.getSource();
        BooleanProperty p = (BooleanProperty) component.getClientProperty(PROP_ENABLED);
        Boolean newValue = (Boolean) e.getNewValue();
        if (newValue.booleanValue() != p.get()) {
            p.set(newValue.booleanValue());
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER_ENABLED = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (newValue.booleanValue() != component.isEnabled()) {
            component.setEnabled(newValue.booleanValue());
        }
    };

    // selected:
    private static final ItemListener ITEM_LISTENER_SELECTED = e -> {
        AbstractButton absButton = (AbstractButton) e.getSource();
        BooleanProperty p = (BooleanProperty) absButton.getClientProperty(PROP_SELECTED);
        if (absButton.isSelected() != p.get()) {
            p.set(absButton.isSelected());
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER_SELECTED = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        AbstractButton absButton = (AbstractButton) p.getBean();
        if (newValue.booleanValue() != absButton.isSelected()) {
            absButton.setSelected(newValue.booleanValue());
        }
    };

    // visible:
    private static final ComponentListener COMPONENT_LISTENER_VISIBLE = new ComponentAdapter() {
        private void updateProp(ComponentEvent e) {
            JComponent component = (JComponent) e.getComponent();
            BooleanProperty p = (BooleanProperty) component.getClientProperty(PROP_VISIBLE);
            boolean visible = component.isVisible();
            if (visible != p.get()) {
                p.set(visible);
            }
        }

        @Override
        public void componentShown(ComponentEvent e) {
            updateProp(e);
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            updateProp(e);
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER_VISIBLE = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (newValue.booleanValue() != component.isVisible()) {
            component.setVisible(newValue.booleanValue());
        }
    };

    // text:
    private static final PropertyChangeListener SWING_PROP_LISTENER_TEXT = e -> {
        JLabel label = (JLabel) e.getSource();
        StringProperty p = (StringProperty) label.getClientProperty(PROP_TEXT);
        String newValue = (String) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<String> FX_PROP_LISTENER_TEXT = (observable, oldValue, newValue) -> {
        StringProperty p = (StringProperty) observable;
        JLabel label = (JLabel) p.getBean();
        if (!Objects.equals(newValue, label.getText())) {
            label.setText(newValue);
        }
    };

    /**
     * @return Property object for 'enabled' property of the specified component.
     * @see JComponent#setEnabled(boolean)
     * @see JComponent#isEnabled()
     */
    public static BooleanProperty enabledProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        BooleanProperty p = (BooleanProperty) component.getClientProperty(PROP_ENABLED);
        if (p == null) {
            p = new SimpleBooleanProperty(component, "enabled", component.isEnabled());
            component.putClientProperty(PROP_ENABLED, p);
            component.addPropertyChangeListener("enabled", SWING_PROP_LISTENER_ENABLED);
            p.addListener(FX_PROP_LISTENER_ENABLED);
        }
        return p;
    }

    private static final PropertyChangeListener SWING_PROP_LISTENER_ENABLED_FOR_ACTION = e -> {
        if ("enabled".equals(e.getPropertyName())) {
            Action action = (Action) e.getSource();
            BooleanProperty p = (BooleanProperty) action.getValue(PROP_ENABLED);
            Boolean newValue = (Boolean) e.getNewValue();
            if (newValue.booleanValue() != p.get()) {
                p.set(newValue.booleanValue());
            }
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER_ENABLED_FOR_ACTION = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        Action action = (Action) p.getBean();
        if (newValue.booleanValue() != action.isEnabled()) {
            action.setEnabled(newValue.booleanValue());
        }
    };

    /**
     * @return Property object for 'enabled' property of the specified action.
     * @see Action#setEnabled(boolean)
     * @see Action#isEnabled()
     */
    public static BooleanProperty enabledProperty(Action action) {
        Objects.requireNonNull(action, "action");
        BooleanProperty p = (BooleanProperty) action.getValue(PROP_ENABLED);
        if (p == null) {
            p = new SimpleBooleanProperty(action, "enabled", action.isEnabled());
            action.putValue(PROP_ENABLED, p);
            action.addPropertyChangeListener(SWING_PROP_LISTENER_ENABLED_FOR_ACTION);
            p.addListener(FX_PROP_LISTENER_ENABLED_FOR_ACTION);
        }
        return p;
    }

    public static BooleanProperty visibleProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        BooleanProperty p = (BooleanProperty) component.getClientProperty(PROP_VISIBLE);
        if (p == null) {
            p = new SimpleBooleanProperty(component, "visible", component.isVisible());
            component.putClientProperty(PROP_VISIBLE, p);
            component.addComponentListener(COMPONENT_LISTENER_VISIBLE);
            p.addListener(FX_PROP_LISTENER_VISIBLE);
        }
        return p;
    }

    /**
     * @return Property object for 'selected' property of the specified abstract button (e.g. {@link javax.swing.JCheckBox}, {@link javax.swing.JRadioButton}, etc).
     * @see AbstractButton#setSelected(boolean)
     * @see AbstractButton#isSelected()
     */
    public static BooleanProperty selectedProperty(AbstractButton absButton) {
        Objects.requireNonNull(absButton, "absButton");
        BooleanProperty p = (BooleanProperty) absButton.getClientProperty(PROP_SELECTED);
        if (p == null) {
            p = new SimpleBooleanProperty(absButton, "selected", absButton.isSelected());
            absButton.putClientProperty(PROP_SELECTED, p);
            absButton.addItemListener(ITEM_LISTENER_SELECTED);
            p.addListener(FX_PROP_LISTENER_SELECTED);
        }
        return p;
    }

    /**
     * @return Property object for 'text' property of the specified label.
     * @see JLabel#setText(String)
     * @see JLabel#getText()
     */
    public static StringProperty textProperty(JLabel label) {
        Objects.requireNonNull(label, "label");
        StringProperty p = (StringProperty) label.getClientProperty(PROP_TEXT);
        if (p == null) {
            p = new SimpleStringProperty(label, "text", label.getText());
            label.putClientProperty(PROP_TEXT, p);
            label.addPropertyChangeListener("text", SWING_PROP_LISTENER_TEXT);
            p.addListener(FX_PROP_LISTENER_TEXT);
        }
        return p;
    }

    private static class TableSelectedRowCountProperty extends ReadOnlyIntegerPropertyBase {
        private final JTable table;
        private ListSelectionModel selectionModel;
        private int value;
        private final ListSelectionListener selectionListener = e -> selectedRowCountChanged();

        TableSelectedRowCountProperty(JTable table) {
            this.table = table;
            this.value = table.getSelectedRowCount();
        }

        void updateSelectionModel() {
            ListSelectionModel selectionModel = this.table.getSelectionModel();
            if (this.selectionModel != null) {
                this.selectionModel.removeListSelectionListener(this.selectionListener);
            }
            this.selectionModel = selectionModel;
            this.selectionModel.addListSelectionListener(this.selectionListener);
        }

        @Override
        public int get() {
            return value;
        }

        @Override
        public JTable getBean() {
            return table;
        }

        @Override
        public String getName() {
            return "selectedRowCount";
        }

        void selectedRowCountChanged() {
            int c = table.getSelectedRowCount();
            if (this.value != c) {
                this.value = c;
                fireValueChangedEvent();
            }
        }
    }

    private static final PropertyChangeListener TABLE_SELECTION_MODEL_PROPERTY_LISTENER = e -> {
        JTable table = (JTable) e.getSource();
        TableSelectedRowCountProperty p = (TableSelectedRowCountProperty) table.getClientProperty(PROP_SELECTED_ROW_COUNT);
        p.updateSelectionModel();
        p.selectedRowCountChanged();
    };

    /**
     * Note: the returned property correctly handles change of the table selection model.
     *
     * @param table Table. Not null.
     * @return Read-only property which value is the number of selected rows in the table.
     * @see JTable#getSelectedRowCount()
     */
    public static ReadOnlyIntegerProperty selectedRowCountProperty(JTable table) {
        Objects.requireNonNull(table, "table");
        TableSelectedRowCountProperty p = (TableSelectedRowCountProperty) table.getClientProperty(PROP_SELECTED_ROW_COUNT);
        if (p == null) {
            p = new TableSelectedRowCountProperty(table);
            table.putClientProperty(PROP_SELECTED_ROW_COUNT, p);
            p.updateSelectionModel();
            table.addPropertyChangeListener("selectionModel", TABLE_SELECTION_MODEL_PROPERTY_LISTENER);
        }
        return p;
    }

    private static class TreeSelectionCountProperty extends ReadOnlyIntegerPropertyBase {
        private final JTree tree;
        private TreeSelectionModel selectionModel;
        private int value;
        private final TreeSelectionListener selectionListener = e -> selectionCountChanged();

        TreeSelectionCountProperty(JTree tree) {
            this.tree = tree;
            this.value = tree.getSelectionCount();
        }

        void updateSelectionModel() {
            TreeSelectionModel selectionModel = this.tree.getSelectionModel();
            if (this.selectionModel != null) {
                this.selectionModel.removeTreeSelectionListener(this.selectionListener);
            }
            this.selectionModel = selectionModel;
            this.selectionModel.addTreeSelectionListener(this.selectionListener);
        }

        @Override
        public int get() {
            return value;
        }

        @Override
        public JTree getBean() {
            return tree;
        }

        @Override
        public String getName() {
            return "selectionCount";
        }

        void selectionCountChanged() {
            int c = tree.getSelectionCount();
            if (this.value != c) {
                this.value = c;
                fireValueChangedEvent();
            }
        }
    }

    private static final PropertyChangeListener TREE_SELECTION_MODEL_PROPERTY_LISTENER = e -> {
        JTree tree = (JTree) e.getSource();
        TreeSelectionCountProperty p = (TreeSelectionCountProperty) tree.getClientProperty(PROP_SELECTED_ROW_COUNT);
        p.updateSelectionModel();
        p.selectionCountChanged();
    };

    /**
     * Note: the returned property correctly handles change of the tree selection model.
     *
     * @param tree Tree. Not null.
     * @return Read-only property which value is the number of selected rows in the tree.
     * @see JTree#getSelectionCount()
     */
    public static ReadOnlyIntegerProperty selectionCountProperty(JTree tree) {
        Objects.requireNonNull(tree, "tree");
        TreeSelectionCountProperty p = (TreeSelectionCountProperty) tree.getClientProperty(PROP_SELECTED_ROW_COUNT);
        if (p == null) {
            p = new TreeSelectionCountProperty(tree);
            tree.putClientProperty(PROP_SELECTED_ROW_COUNT, p);
            p.updateSelectionModel();
            tree.addPropertyChangeListener(JTree.SELECTION_MODEL_PROPERTY, TREE_SELECTION_MODEL_PROPERTY_LISTENER);
        }
        return p;
    }

    private static class TableModelRowCountProperty extends ReadOnlyIntegerPropertyBase {
        private final JTable table;
        private TableModel model;
        private int value;
        private final TableModelListener modelListener = e -> modelRowCountChanged();

        TableModelRowCountProperty(JTable table) {
            this.table = Objects.requireNonNull(table);
            this.value = table.getModel().getRowCount();
        }

        void updateModel() {
            TableModel model = this.table.getModel();
            if (this.model != null) {
                this.model.removeTableModelListener(this.modelListener);
            }
            this.model = model;
            this.model.addTableModelListener(this.modelListener);
        }

        @Override
        public int get() {
            return value;
        }

        @Override
        public JTable getBean() {
            return table;
        }

        @Override
        public String getName() {
            return "modelRowCount";
        }

        void modelRowCountChanged() {
            int c = table.getModel().getRowCount();
            if (this.value != c) {
                this.value = c;
                fireValueChangedEvent();
            }
        }
    }

    private static final PropertyChangeListener TABLE_MODEL_PROPERTY_LISTENER = e -> {
        JTable table = (JTable) e.getSource();
        TableModelRowCountProperty p = (TableModelRowCountProperty) table.getClientProperty(PROP_TABLE_MODEL_ROW_COUNT);
        p.updateModel();
        p.modelRowCountChanged();
    };

    /**
     * Note: the returned property correctly handles change of the table model.
     *
     * @param table Table. Not null.
     * @return Read-only property which value is the number of rows in the table model.
     */
    public static ReadOnlyIntegerProperty modelRowCountProperty(JTable table) {
        Objects.requireNonNull(table, "table");
        TableModelRowCountProperty p = (TableModelRowCountProperty) table.getClientProperty(PROP_TABLE_MODEL_ROW_COUNT);
        if (p == null) {
            p = new TableModelRowCountProperty(table);
            table.putClientProperty(PROP_TABLE_MODEL_ROW_COUNT, p);
            p.updateModel();
            table.addPropertyChangeListener("model", TABLE_MODEL_PROPERTY_LISTENER);
        }
        return p;
    }

    private static class ComponentFocusedProperty extends ReadOnlyBooleanPropertyBase implements FocusListener {
        private final JComponent component;
        private boolean value;

        ComponentFocusedProperty(JComponent component) {
            this.component = component;
            this.value = component.hasFocus();
            this.component.addFocusListener(this);
        }

        @Override
        public void focusGained(FocusEvent e) {
            valueChanged(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            valueChanged(false);
        }

        @Override
        public boolean get() {
            return value;
        }

        @Override
        public JComponent getBean() {
            return component;
        }

        @Override
        public String getName() {
            return "hasFocus";
        }

        void valueChanged(boolean newValue) {
            if (this.value != newValue) {
                this.value = newValue;
                fireValueChangedEvent();
            }
        }
    }

    /**
     * @see JComponent#hasFocus()
     */
    public static ReadOnlyBooleanProperty focusedProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        ComponentFocusedProperty p = (ComponentFocusedProperty) component.getClientProperty(PROP_FOCUSED);
        if (p == null) {
            p = new ComponentFocusedProperty(component);
            component.putClientProperty(PROP_FOCUSED, p);
        }
        return p;
    }
}