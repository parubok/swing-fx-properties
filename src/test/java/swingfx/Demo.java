package swingfx;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;

import static swingfx.SwingPropertySupport.enabledProperty;
import static swingfx.SwingPropertySupport.focusedProperty;
import static swingfx.SwingPropertySupport.selectedRowCountProperty;
import static swingfx.SwingPropertySupport.textProperty;
import static swingfx.SwingPropertySupport.visibleProperty;

/**
 * GUI to demonstrate component property binding.
 */
public class Demo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Demo::buildUI);
    }

    private static void buildUI() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

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
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JLabel countLabel = new JLabel();
        JLabel hasFocusLabel = new JLabel("Table is Focused!");
        hasFocusLabel.setForeground(Color.BLUE);
        bottomPanel.add(countLabel);
        bottomPanel.add(hasFocusLabel);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        textProperty(countLabel).bind(selectedRowCountProperty(table).asString("Selected Rows: %d"));
        visibleProperty(hasFocusLabel).bind(focusedProperty(table));

        JPanel topPanel = new JPanel();
        contentPanel.add(topPanel, BorderLayout.NORTH);

        JButton actionButton = new JButton("Action!");
        topPanel.add(actionButton);
        enabledProperty(actionButton).bind(selectedRowCountProperty(table).greaterThan(0));

        JButton deleteRowButton = new JButton("Delete Row");
        deleteRowButton.addActionListener(e -> tableModel.removeRow(table.getSelectedRow()));
        topPanel.add(deleteRowButton);
        enabledProperty(deleteRowButton).bind(selectedRowCountProperty(table).isEqualTo(1, 0.0));

        JTextField textField = new JTextField();
        textField.setColumns(10);
        topPanel.add(textField);

        JFrame frame = new JFrame("Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
