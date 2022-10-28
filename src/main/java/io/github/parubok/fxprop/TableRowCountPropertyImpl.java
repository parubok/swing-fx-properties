package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerPropertyBase;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_TABLE_ROW_COUNT;

final class TableRowCountPropertyImpl {

    private static class TableRowCountProperty extends ReadOnlyIntegerPropertyBase {
        private final JTable table;
        private int value;
        private RowSorter<?> rowSorter;
        private final RowSorterListener rowSorterListener;
        private TableModel tableModel;
        private final TableModelListener tableModelListener;

        TableRowCountProperty(JTable table) {
            super();
            this.table = Objects.requireNonNull(table);
            this.value = table.getRowCount();
            this.rowSorterListener = e -> rowCountPossiblyChanged();
            this.tableModelListener = e -> {
                // if (e.getType() != TableModelEvent.UPDATE) {
                    rowCountPossiblyChanged();
                //}
            };
            updateRowSorter();
            updateTableModel();
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

        void rowCountPossiblyChanged() {
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

        void updateTableModel() {
            if (tableModel != null) {
                tableModel.removeTableModelListener(tableModelListener);
            }
            tableModel = table.getModel();
            if (tableModel != null) {
                tableModel.addTableModelListener(tableModelListener);
            }
        }
    }

    private static final PropertyChangeListener ROW_SORTER_PROPERTY_LISTENER = e -> {
        JTable table = (JTable) e.getSource();
        TableRowCountProperty p = (TableRowCountProperty) table.getClientProperty(PROP_TABLE_ROW_COUNT);
        p.updateRowSorter();
        p.rowCountPossiblyChanged();
    };

    private static final PropertyChangeListener TABLE_MODEL_PROPERTY_LISTENER = e -> {
        JTable table = (JTable) e.getSource();
        TableRowCountProperty p = (TableRowCountProperty) table.getClientProperty(PROP_TABLE_ROW_COUNT);
        p.updateTableModel();
        p.rowCountPossiblyChanged();
    };

    static ReadOnlyIntegerProperty getProperty(JTable table) {
        Objects.requireNonNull(table, "table");
        TableRowCountProperty p = (TableRowCountProperty) table.getClientProperty(PROP_TABLE_ROW_COUNT);
        if (p == null) {
            p = new TableRowCountProperty(table);
            table.putClientProperty(PROP_TABLE_ROW_COUNT, p);
            table.addPropertyChangeListener("rowSorter", ROW_SORTER_PROPERTY_LISTENER);
            table.addPropertyChangeListener("model", TABLE_MODEL_PROPERTY_LISTENER);
        }
        return p;
    }
}
