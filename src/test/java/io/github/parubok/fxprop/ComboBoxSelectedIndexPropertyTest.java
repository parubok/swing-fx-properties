package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.IntegerProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

public class ComboBoxSelectedIndexPropertyTest {
    @Test
    public void basicTest() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("item1");
            model.addElement("item2");
            model.addElement("item3");
            JComboBox<String> combo = new JComboBox<>(model);
            IntegerProperty p = SwingPropertySupport.selectedIndexProperty(combo);
            Assertions.assertEquals(0, p.get());
            combo.setSelectedIndex(1);
            Assertions.assertEquals(1, p.get());
            p.set(2);
            Assertions.assertEquals(2, combo.getSelectedIndex());
            p.set(-1);
            Assertions.assertEquals(-1, combo.getSelectedIndex());
        });
    }

    @Test
    public void removeSelectedItem() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("item1");
            model.addElement("item2");
            model.addElement("item3");
            JComboBox<String> combo = new JComboBox<>(model);
            IntegerProperty p = SwingPropertySupport.selectedIndexProperty(combo);
            combo.setSelectedIndex(2);
            Assertions.assertEquals(2, p.get());
            model.removeElementAt(2);
            Assertions.assertEquals(1, p.get());
        });
    }

    @Test
    public void invalidIndex() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("item1");
            JComboBox<String> combo = new JComboBox<>(model);
            IntegerProperty p = SwingPropertySupport.selectedIndexProperty(combo);
            Assertions.assertThrows(IllegalArgumentException.class, () -> p.set(20));
        });
    }
}
