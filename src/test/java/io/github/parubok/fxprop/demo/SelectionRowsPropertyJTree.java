package io.github.parubok.fxprop.demo;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.BorderLayout;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.parubok.fxprop.SwingPropertySupport.enabledProperty;
import static io.github.parubok.fxprop.SwingPropertySupport.selectionRowsProperty;
import static io.github.parubok.fxprop.SwingPropertySupport.textProperty;

class SelectionRowsPropertyJTree extends DemoTab {
    SelectionRowsPropertyJTree() {
        super(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        for (int i = 0; i < 100; i++) {
            root.add(new DefaultMutableTreeNode("child " + (i + 1)));
        }
        JTree tree = new JTree(new DefaultTreeModel(root));
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tree);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel countLabel = new JLabel();
        bottomPanel.add(countLabel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);
        textProperty(countLabel).bind(selectionRowsProperty(tree)
                .asObject(list -> list.stream().map(String::valueOf).collect(Collectors.joining(", "))));

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);

        JButton clearSelectionButton = new JButton("Clear Selection");
        clearSelectionButton.addActionListener(e -> tree.clearSelection());
        topPanel.add(clearSelectionButton);
        enabledProperty(clearSelectionButton).bind(selectionRowsProperty(tree).asInteger(List::size)
                .greaterThanOrEqualTo(1));
    }

    @Override
    String getTitle() {
        return "selectionRowsProperty(JTree)";
    }
}
