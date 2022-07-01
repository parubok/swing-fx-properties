package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyObjectProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyObjectPropertyBase;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED_PATH;

final class SelectionPathPropertyImpl {
    private static class TreeSelectionPathProperty extends ReadOnlyObjectPropertyBase<TreePath> {
        private final JTree tree;
        private TreeSelectionModel selectionModel;
        private TreePath value;
        private final TreeSelectionListener selectionListener = e -> selectionPathChanged();

        TreeSelectionPathProperty(JTree tree) {
            this.tree = tree;
            this.value = tree.getSelectionPath();
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
        public TreePath get() {
            return value;
        }

        @Override
        public JTree getBean() {
            return tree;
        }

        @Override
        public String getName() {
            return "selectionPath";
        }

        void selectionPathChanged() {
            TreePath path = tree.getSelectionPath();
            if (!Objects.equals(path, this.value)) {
                this.value = path;
                fireValueChangedEvent();
            }
        }
    }

    private static final PropertyChangeListener TREE_SELECTION_MODEL_PROPERTY_LISTENER = e -> {
        JTree tree = (JTree) e.getSource();
        TreeSelectionPathProperty p = (TreeSelectionPathProperty) tree.getClientProperty(PROP_SELECTED_PATH);
        p.updateSelectionModel();
        p.selectionPathChanged();
    };

    static ReadOnlyObjectProperty<TreePath> getProperty(JTree tree) {
        Objects.requireNonNull(tree, "tree");
        TreeSelectionPathProperty p = (TreeSelectionPathProperty) tree.getClientProperty(PROP_SELECTED_PATH);
        if (p == null) {
            p = new TreeSelectionPathProperty(tree);
            tree.putClientProperty(PROP_SELECTED_PATH, p);
            p.updateSelectionModel();
            tree.addPropertyChangeListener(JTree.SELECTION_MODEL_PROPERTY, TREE_SELECTION_MODEL_PROPERTY_LISTENER);
        }
        return p;
    }
}
