package org.swingfx.demo;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

import static org.swingfx.SwingPropertySupport.enabledProperty;
import static org.swingfx.SwingPropertySupport.selectedRowCountProperty;
import static org.swingfx.SwingPropertySupport.textProperty;

class SelectedRowCountPropertyJTable extends DemoTab {
    SelectedRowCountPropertyJTable() {
        super(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        DefaultTableModel tableModel = new DefaultTableModel(20, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable();
        table.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel countLabel = new JLabel();
        bottomPanel.add(countLabel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);
        textProperty(countLabel).bind(selectedRowCountProperty(table).asString("Selected Rows: %d"));

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);

        JButton clearSelectionButton = new JButton("Clear Selection");
        clearSelectionButton.addActionListener(e -> table.clearSelection());
        topPanel.add(clearSelectionButton);
        enabledProperty(clearSelectionButton).bind(selectedRowCountProperty(table).greaterThanOrEqualTo(1));

        JButton deleteRowButton = new JButton("Delete Row");
        deleteRowButton.addActionListener(e -> tableModel.removeRow(table.getSelectedRow()));
        topPanel.add(deleteRowButton);
        enabledProperty(deleteRowButton).bind(selectedRowCountProperty(table).isEqualTo(1));
    }

    @Override
    String getTitle() {
        return "selectedRowCountProperty(JTable)";
    }
}
