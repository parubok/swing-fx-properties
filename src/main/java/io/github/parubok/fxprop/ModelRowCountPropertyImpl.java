package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerPropertyBase;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_TABLE_MODEL_ROW_COUNT;

final class ModelRowCountPropertyImpl {
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

    static ReadOnlyIntegerProperty getProperty(JTable table) {
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
}
