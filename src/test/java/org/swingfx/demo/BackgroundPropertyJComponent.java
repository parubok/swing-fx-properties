package org.swingfx.demo;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import static org.swingfx.SwingPropertySupport.backgroundProperty;
import static org.swingfx.SwingPropertySupport.selectedItemProperty;

class BackgroundPropertyJComponent extends DemoTab {
    BackgroundPropertyJComponent() {
        DefaultComboBoxModel<String> colorModel = new DefaultComboBoxModel<>();
        colorModel.addElement("#FF0000");
        colorModel.addElement("#00FF00");
        colorModel.addElement("#0000FF");
        JComboBox<String> colorCombo = new JComboBox<>(colorModel);
        add(colorCombo);

        JPanel colorPanel = new JPanel(null);
        colorPanel.setOpaque(true);
        colorPanel.setPreferredSize(new Dimension(100, 100));
        colorPanel.setBorder(new LineBorder(Color.BLACK));
        add(colorPanel);

        backgroundProperty(colorPanel).bind(selectedItemProperty(colorCombo).asObject(s -> Color.decode(s)));
    }

    @Override
    String getTitle() {
        return "backgroundProperty(JComponent)";
    }
}
