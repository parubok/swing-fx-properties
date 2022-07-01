package io.github.parubok.fxprop.demo;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import static io.github.parubok.fxprop.SwingPropertySupport.backgroundProperty;
import static io.github.parubok.fxprop.SwingPropertySupport.mouseOverProperty;

class MouseOverPropertyJComponent extends DemoTab {
    MouseOverPropertyJComponent() {
        JPanel colorPanel = new JPanel(null);
        colorPanel.setOpaque(true);
        colorPanel.setPreferredSize(new Dimension(100, 100));
        colorPanel.setBorder(new LineBorder(Color.BLACK));
        add(colorPanel);

        backgroundProperty(colorPanel).bind(mouseOverProperty(colorPanel).asObject(b -> b ? Color.RED : Color.BLUE));
    }

    @Override
    String getTitle() {
        return "mouseOverProperty(JComponent)";
    }
}
