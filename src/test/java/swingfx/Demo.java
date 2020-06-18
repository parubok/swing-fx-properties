package swingfx;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

import static swingfx.SwingPropertySupport.enabledProperty;
import static swingfx.SwingPropertySupport.selectedRowCountProperty;
import static swingfx.SwingPropertySupport.textProperty;

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

        DefaultTableModel tableModel = new DefaultTableModel(1_000, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int column = 0; column < tableModel.getColumnCount(); column++) {
                tableModel.setValueAt("cell " + row + ", " + column, row, column);
            }
        }
        JTable table = new JTable();
        table.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JLabel countLabel = new JLabel();
        contentPanel.add(countLabel, BorderLayout.SOUTH);
        textProperty(countLabel).bind(selectedRowCountProperty(table).asString("Selected Rows: %d"));

        JPanel topPanel = new JPanel();
        contentPanel.add(topPanel, BorderLayout.NORTH);
        JButton actionButton = new JButton("Action!");
        topPanel.add(actionButton);
        enabledProperty(actionButton).bind(selectedRowCountProperty(table).greaterThan(0));

        JFrame frame = new JFrame("Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
