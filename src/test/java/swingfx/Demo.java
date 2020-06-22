package swingfx;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;

import static swingfx.SwingPropertySupport.enabledProperty;
import static swingfx.SwingPropertySupport.focusedProperty;
import static swingfx.SwingPropertySupport.foregroundProperty;
import static swingfx.SwingPropertySupport.selectedProperty;
import static swingfx.SwingPropertySupport.selectedRowCountProperty;
import static swingfx.SwingPropertySupport.textProperty;
import static swingfx.SwingPropertySupport.visibleProperty;

/**
 * GUI to demo component property binding.
 */
public class Demo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Demo::buildUI);
    }

    private static void buildUI() {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Old", oldTab());
        tabbedPane.addTab("selectedRowCount", selectedRowCountTab());
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        JFrame frame = new JFrame("swing-fx-properties");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private static JPanel selectedRowCountTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
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
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel countLabel = new JLabel();
        bottomPanel.add(countLabel, BorderLayout.WEST);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        textProperty(countLabel).bind(selectedRowCountProperty(table).asString("Selected Rows: %d"));

        JPanel topPanel = new JPanel();
        panel.add(topPanel, BorderLayout.NORTH);

        JButton clearSelectionButton = new JButton("Clear Selection");
        clearSelectionButton.addActionListener(e -> table.clearSelection());
        topPanel.add(clearSelectionButton);
        enabledProperty(clearSelectionButton).bind(selectedRowCountProperty(table).greaterThanOrEqualTo(1));

        JButton deleteRowButton = new JButton("Delete Row");
        deleteRowButton.addActionListener(e -> tableModel.removeRow(table.getSelectedRow()));
        topPanel.add(deleteRowButton);
        enabledProperty(deleteRowButton).bind(selectedRowCountProperty(table).isEqualTo(1));

        return panel;
    }

    private static JPanel oldTab() {
        JPanel oldPanel = new JPanel(new BorderLayout(10, 10));
        oldPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
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
        oldPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel countLabel = new JLabel();
        JLabel hasFocusLabel = new JLabel("Table is Focused!");
        hasFocusLabel.setForeground(Color.BLUE);
        bottomPanel.add(countLabel, BorderLayout.WEST);
        bottomPanel.add(hasFocusLabel, BorderLayout.EAST);
        oldPanel.add(bottomPanel, BorderLayout.SOUTH);
        textProperty(countLabel).bind(selectedRowCountProperty(table).asString("Selected Rows: %d"));
        visibleProperty(hasFocusLabel).bind(focusedProperty(table));

        JPanel topPanel = new JPanel();
        oldPanel.add(topPanel, BorderLayout.NORTH);

        JButton clearSelectionButton = new JButton("Clear Selection");
        clearSelectionButton.addActionListener(e -> table.clearSelection());
        topPanel.add(clearSelectionButton);
        enabledProperty(clearSelectionButton).bind(selectedRowCountProperty(table).greaterThanOrEqualTo(1));

        JButton deleteRowButton = new JButton("Delete Row");
        deleteRowButton.addActionListener(e -> tableModel.removeRow(table.getSelectedRow()));
        topPanel.add(deleteRowButton);
        enabledProperty(deleteRowButton).bind(selectedRowCountProperty(table).isEqualTo(1));

        JCheckBox checkBox = new JCheckBox("Value:");
        topPanel.add(checkBox);

        JTextField textField = new JTextField("text field");
        textField.setColumns(10);
        topPanel.add(textField);

        JLabel textFieldFocusLabel = new JLabel();
        topPanel.add(textFieldFocusLabel);
        foregroundProperty(textFieldFocusLabel).bind(focusedProperty(textField)
                .asObject(focused -> focused ? Color.BLUE : Color.RED));
        textProperty(textFieldFocusLabel).bind(focusedProperty(textField)
                .asObject(focused -> focused ? "Text field has focus!" : "Text field has NO focus!"));

        enabledProperty(textField).bind(selectedProperty(checkBox));

        return oldPanel;
    }
}
