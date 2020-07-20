package org.swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.ReadOnlyObjectProperty;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

class SelectionPathPropertyTest {
    private static TreePath path(TreeNode... nodes) {
        return new TreePath(nodes);
    }

    @Test
    void basic_test_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_1 = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_2 = new DefaultMutableTreeNode();
            root.add(child_1);
            root.add(child_2);
            TreeModel model = new DefaultTreeModel(root);
            JTree tree = new JTree(model);
            ReadOnlyObjectProperty<TreePath> p = SwingPropertySupport.selectionPathProperty(tree);
            Assertions.assertNull(p.get());
            tree.setSelectionPath(path(root, child_1));
            Assertions.assertEquals(path(root, child_1), p.get());
            tree.addSelectionPath(path(root, child_2));
            Assertions.assertEquals(path(root, child_1), p.get());
            tree.removeSelectionPath(path(root, child_1));
            Assertions.assertEquals(path(root, child_2), p.get());
            tree.clearSelection();
            Assertions.assertNull(p.get());
        });
    }

    @Test
    void initial_value() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_1 = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_2 = new DefaultMutableTreeNode();
            root.add(child_1);
            root.add(child_2);
            TreeModel model = new DefaultTreeModel(root);
            JTree tree = new JTree(model);
            tree.setSelectionPath(path(root, child_2));
            ReadOnlyObjectProperty<TreePath> p = SwingPropertySupport.selectionPathProperty(tree);
            Assertions.assertEquals(path(root, child_2), p.get());
        });
    }

    @Test
    void change_selection_model() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_1 = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_2 = new DefaultMutableTreeNode();
            root.add(child_1);
            root.add(child_2);
            TreeModel model = new DefaultTreeModel(root);
            JTree tree = new JTree(model);
            tree.setSelectionPath(path(root, child_2));
            ReadOnlyObjectProperty<TreePath> p = SwingPropertySupport.selectionPathProperty(tree);
            Assertions.assertEquals(path(root, child_2), p.get());
            TreeSelectionModel prevSelModel = tree.getSelectionModel();
            tree.setSelectionModel(new DefaultTreeSelectionModel());
            Assertions.assertNull(p.get());
            tree.setSelectionPath(path(root, child_1));
            Assertions.assertEquals(path(root, child_1), p.get());

            prevSelModel.clearSelection();
            Assertions.assertEquals(path(root, child_1), p.get());
        });
    }
}
