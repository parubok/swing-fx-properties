package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyObjectProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyObjectPropertyBase;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED_ROWS;

final class TreeSelectionRowsPropertyImpl {

    private static List<Integer> getSelectedRows(JTree tree) {
        int[] selRows = tree.getSelectionRows();
        if (selRows == null || selRows.length == 0) {
            return Collections.emptyList();
        }
        if (selRows.length == 1) {
            return Collections.singletonList(selRows[0]);
        }
        List<Integer> list = new ArrayList<>(selRows.length);
        for (int selRow : selRows) {
            list.add(Integer.valueOf(selRow));
        }
        list.sort(Comparator.naturalOrder());
        return Collections.unmodifiableList(list);
    }

    private static class TreeSelectionRowsProperty extends ReadOnlyObjectPropertyBase<List<Integer>> {
        private final JTree tree;
        private TreeSelectionModel selectionModel;
        private List<Integer> value;
        private final TreeSelectionListener selectionListener = e -> selectionRowsChanged();

        TreeSelectionRowsProperty(JTree tree) {
            this.tree = tree;
            this.value = getSelectedRows(tree);
        }

        @Override
        public List<Integer> get() {
            return value;
        }

        @Override
        public JTree getBean() {
            return tree;
        }

        @Override
        public String getName() {
            return "selectionRows";
        }

        void updateSelectionModel() {
            TreeSelectionModel selectionModel = this.tree.getSelectionModel();
            if (this.selectionModel != null) {
                this.selectionModel.removeTreeSelectionListener(this.selectionListener);
            }
            this.selectionModel = selectionModel;
            this.selectionModel.addTreeSelectionListener(this.selectionListener);
        }

        void selectionRowsChanged() {
            List<Integer> newSelRows = getSelectedRows(tree);
            if (!newSelRows.equals(this.value)) {
                this.value = newSelRows;
                fireValueChangedEvent();
            }
        }
    }

    private static final PropertyChangeListener TREE_SELECTION_MODEL_PROPERTY_LISTENER = e -> {
        JTree tree = (JTree) e.getSource();
        TreeSelectionRowsProperty p = (TreeSelectionRowsProperty) tree.getClientProperty(PROP_SELECTED_ROWS);
        p.updateSelectionModel();
        p.selectionRowsChanged();
    };

    static ReadOnlyObjectProperty<List<Integer>> getProperty(JTree tree) {
        Objects.requireNonNull(tree, "tree");
        TreeSelectionRowsProperty p = (TreeSelectionRowsProperty) tree.getClientProperty(PROP_SELECTED_ROWS);
        if (p == null) {
            p = new TreeSelectionRowsProperty(tree);
            tree.putClientProperty(PROP_SELECTED_ROWS, p);
            p.updateSelectionModel();
            tree.addPropertyChangeListener(JTree.SELECTION_MODEL_PROPERTY, TREE_SELECTION_MODEL_PROPERTY_LISTENER);
        }
        return p;
    }
}
