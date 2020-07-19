package org.swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.ReadOnlyObjectProperty;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

class SelectionPathPropertyImplTest {
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
            tree.setSelectionPath(new TreePath(new Object[] { root, child_1 }));
            Assertions.assertEquals(new TreePath(new Object[] { root, child_1 }), p.get());
            tree.addSelectionPath(new TreePath(new Object[] { root, child_2 }));
            Assertions.assertEquals(new TreePath(new Object[] { root, child_1 }), p.get());
            tree.removeSelectionPath(new TreePath(new Object[] { root, child_1 }));
            Assertions.assertEquals(new TreePath(new Object[] { root, child_2 }), p.get());
            tree.clearSelection();
            Assertions.assertNull(p.get());
        });
    }
}
