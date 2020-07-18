package org.swingfx;

import com.sun.swingfx.collections.ObservableListWrapper;
import swingfx.beans.property.ListProperty;
import swingfx.beans.property.SimpleListProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static org.swingfx.ClientProps.PROP_SELECTED_ROWS;

final class SelectedRowsPropertyImpl {
    private static class TableSelectedRowsProperty extends SimpleListProperty<Integer> {
        boolean adjustingTableSelection;
        private ListSelectionModel selectionModel;
        private final ListSelectionListener selectionListener = e -> selectedRowsChanged();

        TableSelectedRowsProperty(JTable table) {
            super(table, "selectedRows", new ObservableListWrapper<>(getSelectedRows(table)));
        }

        void updateSelectionModel() {
            if (this.selectionModel != null) {
                this.selectionModel.removeListSelectionListener(this.selectionListener);
            }
            this.selectionModel = ((JTable) getBean()).getSelectionModel();
            this.selectionModel.addListSelectionListener(this.selectionListener);
        }

        void selectedRowsChanged() {
            if (!adjustingTableSelection) {
                List<Integer> tableValue = getSelectedRows((JTable) getBean());
                if (!new HashSet<>(tableValue).equals(new HashSet<>(get()))) { // compare disregarding order
                    if (tableValue.isEmpty()) {
                        get().clear();
                    } else {
                        get().setAll(tableValue);
                    }
                }
            }
        }
    }

    private static ArrayList<Integer> getSelectedRows(JTable table) {
        final ArrayList<Integer> selectedRows = new ArrayList<>();
        if (!table.getSelectionModel().isSelectionEmpty()) {
            int[] rows = table.getSelectedRows();
            for (int i = 0; i < rows.length; i++) {
                selectedRows.add(Integer.valueOf(rows[i]));
            }
        }
        return selectedRows;
    }

    private static final PropertyChangeListener TABLE_SELECTION_MODEL_PROPERTY_LISTENER = e -> {
        JTable table = (JTable) e.getSource();
        TableSelectedRowsProperty p = (TableSelectedRowsProperty) table.getClientProperty(PROP_SELECTED_ROWS);
        p.updateSelectionModel();
        p.selectedRowsChanged();
    };

    private static final ChangeListener<List<Integer>> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        TableSelectedRowsProperty p = (TableSelectedRowsProperty) observable;
        JTable table = (JTable) p.getBean();
        List<Integer> tableValue = getSelectedRows(table);
        List<Integer> propValue = p.get();
        if (!tableValue.equals(propValue)) {
            p.adjustingTableSelection = true;
            try {
                if (propValue.isEmpty()) {
                    table.clearSelection();
                } else {
                    int min = Collections.min(propValue);
                    boolean contInterval = true; // values in the interval may be unsorted
                    for (int i = 1; i < propValue.size(); i++) {
                        if (!propValue.contains(min + i)) {
                            contInterval = false;
                            break;
                        }
                    }
                    if (contInterval) {
                        table.getSelectionModel().setSelectionInterval(min, min + propValue.size() - 1);
                    } else {
                        table.clearSelection();
                        propValue.forEach(index -> table.getSelectionModel().addSelectionInterval(index, index));
                    }
                }
            } finally {
                p.adjustingTableSelection = false;
            }
        }
    };

    static ListProperty<Integer> getProperty(JTable table) {
        Objects.requireNonNull(table, "table");
        TableSelectedRowsProperty p = (TableSelectedRowsProperty) table.getClientProperty(PROP_SELECTED_ROWS);
        if (p == null) {
            p = new TableSelectedRowsProperty(table);
            table.putClientProperty(PROP_SELECTED_ROWS, p);
            p.updateSelectionModel();
            p.addListener(FX_PROP_LISTENER);
            table.addPropertyChangeListener("selectionModel", TABLE_SELECTION_MODEL_PROPERTY_LISTENER);
        }
        return p;
    }
}
