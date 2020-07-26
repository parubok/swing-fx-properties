package org.swingfx.demo;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

import static org.swingfx.SwingPropertySupport.enabledProperty;
import static org.swingfx.SwingPropertySupport.selectedProperty;

class EnabledPropertyJComponent extends DemoTab {
    EnabledPropertyJComponent() {
        super(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);

        JCheckBox checkBox = new JCheckBox("Value:");
        topPanel.add(checkBox);

        JTextField textField = new JTextField("text");
        textField.setColumns(10);
        topPanel.add(textField);

        enabledProperty(textField).bind(selectedProperty(checkBox));
    }

    @Override
    String getTitle() {
        return "enabledProperty(JComponent)";
    }
}
