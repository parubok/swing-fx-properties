package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ObjectProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

class SelectedValuePropertyTest {
    @Test
    public void basicTest() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultListModel<String> model = new DefaultListModel<>();
            model.addElement("a1");
            model.addElement("b2");
            model.addElement("c3");
            JList<String> list = new JList<>();
            list.setModel(model);

            ObjectProperty<String> p = SwingPropertySupport.selectedValueProperty(list);
            Assertions.assertNull(list.getSelectedValue());
            Assertions.assertNull(p.getValue());

            list.setSelectedValue("b2", false);
            Assertions.assertEquals("b2", list.getSelectedValue());
            Assertions.assertEquals("b2", p.getValue());

            p.set("c3");
            Assertions.assertEquals("c3", list.getSelectedValue());
            Assertions.assertEquals("c3", p.getValue());

            list.clearSelection();
            Assertions.assertNull(list.getSelectedValue());
            Assertions.assertNull(p.getValue());

            list.setSelectedIndices(new int [] {0, 2});
            Assertions.assertEquals("a1", list.getSelectedValue());
            Assertions.assertEquals("a1", p.getValue());
        });
    }
}
