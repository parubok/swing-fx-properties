package swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.ReadOnlyBooleanProperty;
import swingfx.beans.property.ReadOnlyIntegerProperty;
import swingfx.beans.property.ReadOnlyIntegerPropertyBase;
import swingfx.beans.property.StringProperty;

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
import java.beans.PropertyChangeListener;
import java.util.Objects;

/**
 * Collection of static methods which provide access to various properties of Swing components via JavaFX-style
 * property objects.
 */
public class SwingPropertySupport {

    static String PROP_SELECTED_ROW_COUNT = "swingfx-property-selected-row-count";
    static String PROP_TABLE_MODEL_ROW_COUNT = "swingfx-property-table-model-row-count";

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

    /**
     * @param component Component. Not null.
     * @return Read-only property which value is {@code true} when the component has focus and {@code false} when not.
     * @see JComponent#hasFocus()
     */
    public static ReadOnlyBooleanProperty focusedProperty(JComponent component) {
        return FocusedPropertyImpl.focusedProperty(component);
    }
}
