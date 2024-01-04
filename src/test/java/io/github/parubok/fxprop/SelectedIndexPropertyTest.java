package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.IntegerProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

class SelectedIndexPropertyTest {
    @Test
    public void basicTest() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultListModel<String> model = new DefaultListModel<>();
            model.addElement("a1");
            model.addElement("b2");
            model.addElement("c3");
            JList<String> list = new JList<>();
            list.setModel(model);

            IntegerProperty p = SwingPropertySupport.selectedIndexProperty(list);
            Assertions.assertEquals(-1, list.getSelectedIndex());
            Assertions.assertEquals(-1, p.getValue());

            list.setSelectedIndex(1);
            Assertions.assertEquals(1, list.getSelectedIndex());
            Assertions.assertEquals(1, p.getValue());

            list.clearSelection();
            Assertions.assertEquals(-1, list.getSelectedIndex());
            Assertions.assertEquals(-1, p.getValue());
        });
    }
}
