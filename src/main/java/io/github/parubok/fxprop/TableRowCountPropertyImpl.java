package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerPropertyBase;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.RowSorterListener;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_TABLE_ROW_COUNT;

final class TableRowCountPropertyImpl {

    private static class TableRowCountProperty extends ReadOnlyIntegerPropertyBase {
        private final JTable table;
        private int value;
        private RowSorter<?> rowSorter;
        private final RowSorterListener rowSorterListener;

        TableRowCountProperty(JTable table) {
            this.table = Objects.requireNonNull(table);
            this.value = table.getRowCount();
            this.rowSorterListener = e -> rowCountChanged();
            updateRowSorter();
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
            return "rowCount";
        }

        void rowCountChanged() {
            int c = table.getRowCount();
            if (this.value != c) {
                this.value = c;
                fireValueChangedEvent();
            }
        }

        void updateRowSorter() {
            if (rowSorter != null) {
                rowSorter.removeRowSorterListener(rowSorterListener);
            }
            rowSorter = table.getRowSorter();
            if (rowSorter != null) {
                rowSorter.addRowSorterListener(rowSorterListener);
            }
        }
    }

    private static final PropertyChangeListener ROW_SORTER_PROPERTY_LISTENER = e -> {
        JTable table = (JTable) e.getSource();
        TableRowCountProperty p = (TableRowCountProperty) table.getClientProperty(PROP_TABLE_ROW_COUNT);
        p.updateRowSorter();
        p.rowCountChanged();
    };

    static ReadOnlyIntegerProperty getProperty(JTable table) {
        Objects.requireNonNull(table, "table");
        TableRowCountProperty p = (TableRowCountProperty) table.getClientProperty(PROP_TABLE_ROW_COUNT);
        if (p == null) {
            p = new TableRowCountProperty(table);
            table.putClientProperty(PROP_TABLE_ROW_COUNT, p);
            table.addPropertyChangeListener("rowSorter", ROW_SORTER_PROPERTY_LISTENER);
        }
        return p;
    }
}
