package io.github.parubok.fxprop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;
import io.github.parubok.swingfx.beans.value.ObservableValue;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectionCountPropertyTest {
    @Test
    public void tree_selectionCount_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_1 = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_2 = new DefaultMutableTreeNode();
            root.add(child_1);
            root.add(child_2);
            TreeModel model = new DefaultTreeModel(root);
            JTree tree = new JTree(model);
            ReadOnlyIntegerProperty selCountProp = SwingPropertySupport.selectionCountProperty(tree);

            List<Number> values = new ArrayList<>();
            selCountProp.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
                    Assertions.assertEquals(selCountProp, observable);
                    values.add(oldValue);
                    values.add(newValue);
                }
            });

            Assertions.assertEquals(0, selCountProp.get());
            tree.setSelectionPath(new TreePath(new Object[]{root, child_1}));
            tree.addSelectionPath(new TreePath(new Object[]{root, child_2}));
            Assertions.assertEquals(2, selCountProp.get());
            Assertions.assertIterableEquals(Arrays.asList(0, 1, 1, 2), values);
            tree.clearSelection();
            Assertions.assertEquals(0, selCountProp.get());
            Assertions.assertIterableEquals(Arrays.asList(0, 1, 1, 2, 2, 0), values);
        });
    }
}
