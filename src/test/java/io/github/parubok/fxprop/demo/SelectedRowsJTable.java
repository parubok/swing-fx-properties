package io.github.parubok.fxprop.demo;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.stream.Collectors;

import static io.github.parubok.fxprop.SwingPropertySupport.selectedRowsProperty;
import static io.github.parubok.fxprop.SwingPropertySupport.textProperty;

class SelectedRowsJTable extends DemoTab {
    SelectedRowsJTable() {
        super(new BorderLayout());
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

        JLabel label = new JLabel();
        textProperty(label).bind(selectedRowsProperty(table).asObject(selectedRowsList -> selectedRowsList.stream()
                .map(r -> Integer.toString(r)).collect(Collectors.joining(", ", "Selected rows: [", "]"))));
        add(label, BorderLayout.SOUTH);
    }

    @Override
    String getTitle() {
        return "selectedRowsProperty(JTable)";
    }
}
