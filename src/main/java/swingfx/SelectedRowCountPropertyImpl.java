package swingfx;

import swingfx.beans.property.ReadOnlyIntegerProperty;
import swingfx.beans.property.ReadOnlyIntegerPropertyBase;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static swingfx.ClientProps.PROP_SELECTED_ROW_COUNT;

final class SelectedRowCountPropertyImpl {
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

    static ReadOnlyIntegerProperty getProperty(JTable table) {
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
}
