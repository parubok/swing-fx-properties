package org.swingfx.demo;

import swingfx.beans.property.ObjectProperty;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import static org.swingfx.SwingPropertySupport.selectedItemProperty;

class SelectedItemJComboBox extends DemoTab {
    SelectedItemJComboBox() {
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(new JLabel("bindBidirectional:"));
        int c = 10;
        String[] values = new String[c];
        for (int i = 0; i < c; i++) {
            values[i] = "value_" + i;
        }
        JComboBox<String> combo1 = new JComboBox<>(new DefaultComboBoxModel<>(values));
        add(combo1);

        JComboBox<String> combo2 = new JComboBox<>(new DefaultComboBoxModel<>(values));
        add(combo2);

        ObjectProperty<String> p1 = selectedItemProperty(combo1);
        ObjectProperty<String> p2 = selectedItemProperty(combo2);
        p1.bindBidirectional(p2);
    }

    @Override
    String getTitle() {
        return "selectedItemProperty(JComboBox)";
    }
}
