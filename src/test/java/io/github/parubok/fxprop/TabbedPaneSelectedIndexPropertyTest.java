package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.IntegerProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class TabbedPaneSelectedIndexPropertyTest {
    @Test
    public void basic_test() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTabbedPane tabbedPane = new JTabbedPane();
            JPanel tab1 = new JPanel();
            tabbedPane.addTab("tab1", tab1);
            JPanel tab2 = new JPanel();
            tabbedPane.addTab("tab2", tab2);
            tabbedPane.setSelectedIndex(0);
            IntegerProperty p = SwingPropertySupport.selectedIndexProperty(tabbedPane);
            Assertions.assertEquals(0, p.get());
            tabbedPane.setSelectedIndex(1);
            Assertions.assertEquals(1, p.get());
            p.set(0);
            Assertions.assertEquals(0, tabbedPane.getSelectedIndex());
            tabbedPane.setSelectedIndex(-1);
            Assertions.assertEquals(-1, p.get());
        });
    }

    @Test
    public void insert_remove_tab() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTabbedPane tabbedPane = new JTabbedPane();
            JPanel tab1 = new JPanel();
            tabbedPane.addTab("tab1", tab1);
            JPanel tab2 = new JPanel();
            tabbedPane.addTab("tab2", tab2);
            tabbedPane.setSelectedIndex(1);
            IntegerProperty p = SwingPropertySupport.selectedIndexProperty(tabbedPane);
            Assertions.assertEquals(1, p.get());
            tabbedPane.insertTab("tab1_1", null, new JPanel(), "", 0);
            Assertions.assertEquals(2, p.get());
            tabbedPane.removeTabAt(0);
            Assertions.assertEquals(1, p.get());
        });
    }

    @Test()
    public void invalid_index() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTabbedPane tabbedPane = new JTabbedPane();
            JPanel tab1 = new JPanel();
            tabbedPane.addTab("tab1", tab1);
            JPanel tab2 = new JPanel();
            tabbedPane.addTab("tab2", tab2);
            tabbedPane.setSelectedIndex(0);
            IntegerProperty p = SwingPropertySupport.selectedIndexProperty(tabbedPane);
            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> p.set(10));
            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> p.set(-10));
        });
    }
}
