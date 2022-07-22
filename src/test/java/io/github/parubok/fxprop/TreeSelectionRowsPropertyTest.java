package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyObjectProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;
import io.github.parubok.swingfx.beans.value.ObservableValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeSelectionRowsPropertyTest {

    @Test
    public void basic_test() throws Exception {
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

    @Test
    public void listener_test() throws Exception {
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
            List<List<Integer>> newValues = new ArrayList<>();
            p.addListener(new ChangeListener<List<Integer>>() {
                @Override
                public void changed(ObservableValue<? extends List<Integer>> observable, List<Integer> oldValue, List<Integer> newValue) {
                    newValues.add(newValue);
                }
            });
            tree.setSelectionRow(1);
            Assertions.assertEquals(1, newValues.size());
            Assertions.assertEquals(Collections.singletonList(1), newValues.get(0));
            newValues.clear();

            tree.addSelectionRow(3);
            Assertions.assertEquals(1, newValues.size());
            Assertions.assertEquals(Arrays.asList(1, 3), newValues.get(0));
            newValues.clear();
        });
    }

    @Test
    public void new_selection_model_test() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_1 = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_2 = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_3 = new DefaultMutableTreeNode();
            root.add(child_1);
            root.add(child_2);
            root.add(child_3);
            JTree tree = new JTree(new DefaultTreeModel(root));
            ReadOnlyObjectProperty<List<Integer>> p = SwingPropertySupport.selectionRowsProperty(tree);
            List<List<Integer>> newValues = new ArrayList<>();
            p.addListener(new ChangeListener<List<Integer>>() {
                @Override
                public void changed(ObservableValue<? extends List<Integer>> observable, List<Integer> oldValue, List<Integer> newValue) {
                    newValues.add(newValue);
                }
            });

            tree.setSelectionModel(new DefaultTreeSelectionModel());

            // test that the property still works:
            tree.setSelectionRow(1);
            Assertions.assertEquals(Collections.singletonList(1), p.get());
            Assertions.assertEquals(1, newValues.size());
            tree.setSelectionRow(3);
            Assertions.assertEquals(Collections.singletonList(3), p.get());
            Assertions.assertEquals(2, newValues.size());
        });
    }

    @Test
    public void immutable_list_test() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_1 = new DefaultMutableTreeNode();
            root.add(child_1);
            JTree tree = new JTree(new DefaultTreeModel(root));
            ReadOnlyObjectProperty<List<Integer>> p = SwingPropertySupport.selectionRowsProperty(tree);
            tree.setSelectionRow(0);
            Assertions.assertThrows(UnsupportedOperationException.class, () -> p.get().add(10));
            Assertions.assertThrows(UnsupportedOperationException.class, () -> p.get().remove(0));
        });
    }
}
