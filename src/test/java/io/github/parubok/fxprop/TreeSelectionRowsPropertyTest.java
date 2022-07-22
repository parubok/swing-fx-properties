package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyObjectProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeSelectionRowsPropertyTest {

    @Test
    public void basic_test_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_1 = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_2 = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_3 = new DefaultMutableTreeNode();
            root.add(child_1);
            root.add(child_2);
            root.add(child_3);
            TreeModel model = new DefaultTreeModel(root);
            JTree tree = new JTree(model);
            ReadOnlyObjectProperty<List<Integer>> p = SwingPropertySupport.selectionRowsProperty(tree);
            Assertions.assertTrue(p.get().isEmpty());
            tree.setSelectionRow(1);
            Assertions.assertEquals(Collections.singletonList(1), p.get());
            tree.setSelectionRows(new int[]{1, 0});
            Assertions.assertEquals(Arrays.asList(0, 1), p.get());
            tree.clearSelection();
            Assertions.assertEquals(Collections.emptyList(), p.get());
            tree.setSelectionRow(3);
            Assertions.assertEquals(Collections.singletonList(3), p.get());
        });
    }
}
