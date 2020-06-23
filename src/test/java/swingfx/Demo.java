package swingfx;

import swingfx.beans.property.ObjectProperty;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import static swingfx.SwingPropertySupport.backgroundProperty;
import static swingfx.SwingPropertySupport.enabledProperty;
import static swingfx.SwingPropertySupport.focusedProperty;
import static swingfx.SwingPropertySupport.foregroundProperty;
import static swingfx.SwingPropertySupport.selectedItemProperty;
import static swingfx.SwingPropertySupport.selectedProperty;
import static swingfx.SwingPropertySupport.selectedRowCountProperty;
import static swingfx.SwingPropertySupport.textProperty;

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
        tabbedPane.addTab("Tab 1", tab1());
        tabbedPane.addTab("Tab 2", tab2());
        tabbedPane.addTab("Tab 3", tab3());
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        JFrame frame = new JFrame("swing-fx-properties");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private static JPanel tab1() {
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

    private static JPanel tab2() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel();
        panel.add(topPanel, BorderLayout.NORTH);

        JCheckBox checkBox = new JCheckBox("Value:");
        topPanel.add(checkBox);

        JTextField textField = new JTextField("text");
        textField.setColumns(10);
        topPanel.add(textField);

        JLabel textFieldFocusLabel = new JLabel();
        topPanel.add(textFieldFocusLabel);
        foregroundProperty(textFieldFocusLabel).bind(focusedProperty(textField)
                .asObject(focused -> focused ? Color.BLUE : Color.RED));
        textProperty(textFieldFocusLabel).bind(focusedProperty(textField)
                .asObject(focused -> focused ? "Text field has focus!" : "Text field has NO focus!"));

        enabledProperty(textField).bind(selectedProperty(checkBox));

        return panel;
    }

    private static JPanel tab3() {
        JPanel panel = new JPanel(null);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panel1 = new JPanel();
        panel.add(panel1);
        panel1.add(new JLabel("bindBidirectional:"));
        int c = 10;
        String[] values = new String[c];
        for (int i = 0; i < c; i++) {
            values[i] = "value_" + i;
        }
        JComboBox<String> combo1 = new JComboBox<>(new DefaultComboBoxModel<>(values));
        panel1.add(combo1);

        JComboBox<String> combo2 = new JComboBox<>(new DefaultComboBoxModel<>(values));
        panel1.add(combo2);

        ObjectProperty<String> p1 = selectedItemProperty(combo1);
        ObjectProperty<String> p2 = selectedItemProperty(combo2);
        p1.bindBidirectional(p2);

        JPanel panel2 = new JPanel();
        panel.add(panel2);

        DefaultComboBoxModel<String> colorModel = new DefaultComboBoxModel<>();
        colorModel.addElement("#FF0000");
        colorModel.addElement("#00FF00");
        colorModel.addElement("#0000FF");
        JComboBox<String> colorCombo = new JComboBox<>(colorModel);
        panel2.add(colorCombo);

        JPanel colorPanel = new JPanel(null);
        colorPanel.setOpaque(true);
        colorPanel.setPreferredSize(new Dimension(100, 100));
        colorPanel.setBorder(new LineBorder(Color.BLACK));
        panel2.add(colorPanel);

        backgroundProperty(colorPanel).bind(selectedItemProperty(colorCombo).asObject(s -> Color.decode(s)));

        return panel;
    }
}
