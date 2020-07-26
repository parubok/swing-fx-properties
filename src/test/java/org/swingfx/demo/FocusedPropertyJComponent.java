package org.swingfx.demo;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;

import static org.swingfx.SwingPropertySupport.focusedProperty;
import static org.swingfx.SwingPropertySupport.foregroundProperty;
import static org.swingfx.SwingPropertySupport.textProperty;

class FocusedPropertyJComponent extends DemoTab {
    FocusedPropertyJComponent() {
        super(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);

        JTextField textField = new JTextField("text");
        textField.setColumns(10);
        topPanel.add(textField);

        JLabel textFieldFocusLabel = new JLabel();
        topPanel.add(textFieldFocusLabel);
        foregroundProperty(textFieldFocusLabel).bind(focusedProperty(textField)
                .asObject(focused -> focused ? Color.BLUE : Color.RED));
        textProperty(textFieldFocusLabel).bind(focusedProperty(textField)
                .asObject(focused -> focused ? "Text field has focus!" : "Text field has NO focus!"));


        add(new JScrollPane(new JTextArea()), BorderLayout.CENTER);
    }

    @Override
    String getTitle() {
        return "focusedProperty(JComponent)";
    }
}
