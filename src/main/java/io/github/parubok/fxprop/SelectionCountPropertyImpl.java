package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerPropertyBase;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED_ROW_COUNT;

final class SelectionCountPropertyImpl {
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

    static ReadOnlyIntegerProperty getProperty(JTree tree) {
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
}
