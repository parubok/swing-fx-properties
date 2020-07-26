package org.swingfx.demo;

import swingfx.beans.property.ObjectProperty;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.swingfx.SwingPropertySupport.backgroundProperty;
import static org.swingfx.SwingPropertySupport.enabledProperty;
import static org.swingfx.SwingPropertySupport.focusedProperty;
import static org.swingfx.SwingPropertySupport.foregroundProperty;
import static org.swingfx.SwingPropertySupport.selectedItemProperty;
import static org.swingfx.SwingPropertySupport.selectedProperty;
import static org.swingfx.SwingPropertySupport.textProperty;

/**
 * GUI to demo component property binding.
 */
public class Demo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Demo::buildUI);
    }

    private static void buildUI() {
        Logger.getLogger("org.swingfx").setLevel(Level.FINEST);

        JPanel contentPanel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        addDemoTab(new SelectedRowCountPropertyJTable(), tabbedPane);
        tabbedPane.addTab("Tab 2", tab2());
        tabbedPane.addTab("Tab 3", tab3());
        addDemoTab(new MouseOverPropertyJComponent(), tabbedPane);
        addDemoTab(new SelectedRowsJTable(), tabbedPane);
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private static void addDemoTab(DemoTab demoTab, JTabbedPane tabbedPane) {
        tabbedPane.addTab(demoTab.getTitle(), demoTab);
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
