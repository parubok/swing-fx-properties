package io.github.parubok.fxprop;

import io.github.parubok.fxprop.misc.Logging;
import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerPropertyBase;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Objects;
import java.util.WeakHashMap;

final class TableModelRowCountPropertyImpl {

    /**
     * Weekly keep property instance per model instance.
     */
    private static final WeakHashMap<TableModel, TableModelRowCountProperty> props = new WeakHashMap<>();

    private static final TableModelListener MODEL_LISTENER = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            if (e.getType() != TableModelEvent.UPDATE) {
                TableModel model = (TableModel) e.getSource();
                TableModelRowCountProperty p = props.get(model);
                if (p != null) {
                    p.modelRowCountPossiblyChanged();
                } else {
                    // should not happen normally
                    Logging.getLogger().warning("Weak reference to table model was cleared.");
                }
            }
        }
    };

    private static class TableModelRowCountProperty extends ReadOnlyIntegerPropertyBase {
        private final TableModel model;
        private int value;

        TableModelRowCountProperty(TableModel model) {
            this.model = Objects.requireNonNull(model);
            this.value = model.getRowCount();

            // make sure we have exactly one listener registered:
            this.model.removeTableModelListener(MODEL_LISTENER);
            this.model.addTableModelListener(MODEL_LISTENER);
        }

        @Override
        public int get() {
            return value;
        }

        @Override
        public TableModel getBean() {
            return model;
        }

        @Override
        public String getName() {
            return "rowCount";
        }

        void modelRowCountPossiblyChanged() {
            int c = model.getRowCount();
            if (this.value != c) {
                this.value = c;
                fireValueChangedEvent();
            }
        }
    }

    static ReadOnlyIntegerProperty getProperty(TableModel tableModel) {
        Objects.requireNonNull(tableModel, "tableModel");
        TableModelRowCountProperty p = props.get(tableModel);
        if (p == null) {
            p = new TableModelRowCountProperty(tableModel);
            props.put(tableModel, p);
        }
        return p;
    }
}
