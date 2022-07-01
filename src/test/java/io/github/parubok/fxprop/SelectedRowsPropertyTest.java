package io.github.parubok.fxprop;

import io.github.parubok.com.sun.swingfx.collections.ObservableListWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.parubok.swingfx.beans.property.ListProperty;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Arrays;

class SelectedRowsPropertyTest {

    private static JTable newTable() {
        TableModel model = new DefaultTableModel(20, 3);
        JTable table = new JTable();
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return table;
    }

    @Test
    void bindBidirectional() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTable table1 = newTable();
            ListProperty<Integer> selRowsProp1 = SwingPropertySupport.selectedRowsProperty(table1);
            JTable table2 = newTable();
            ListProperty<Integer> selRowsProp2 = SwingPropertySupport.selectedRowsProperty(table2);
            selRowsProp1.bindBidirectional(selRowsProp2);

            table1.getSelectionModel().setSelectionInterval(0, 0);
            Assertions.assertArrayEquals(new int[]{0}, table2.getSelectedRows());

            table1.getSelectionModel().addSelectionInterval(1, 1);
            Assertions.assertArrayEquals(new int[]{0, 1}, table2.getSelectedRows());

            table1.clearSelection();
            Assertions.assertArrayEquals(new int[0], table2.getSelectedRows());

            table2.getSelectionModel().addSelectionInterval(1, 1);
            Assertions.assertArrayEquals(new int[]{1}, table1.getSelectedRows());
        });
    }

    @Test
    void set_list() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTable table = newTable();
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            p.add(3);
            Assertions.assertArrayEquals(new int[]{3}, table.getSelectedRows());
            p.set(new ObservableListWrapper<>(new ArrayList<>(Arrays.asList(4, 6))));
            Assertions.assertArrayEquals(new int[]{4, 6}, table.getSelectedRows());

            Assertions.assertFalse(p.isEmpty());
            p.clear();
            Assertions.assertTrue(p.isEmpty());
            Assertions.assertArrayEquals(new int[0], table.getSelectedRows());

            table.getSelectionModel().setSelectionInterval(2, 5);
            Assertions.assertEquals(Arrays.asList(2, 3, 4, 5), p.get());
        });
    }

    @Test
    void unsorted_rows() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTable table = newTable();
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            Assertions.assertTrue(p.isEmpty());
            Assertions.assertEquals(0, table.getSelectedRowCount());
            p.get().setAll(9, 7, 8); // unsorted
            Assertions.assertEquals(Arrays.asList(9, 7, 8), p.get());
            Assertions.assertArrayEquals(new int[]{7, 8, 9}, table.getSelectedRows());

            table.addRowSelectionInterval(11, 12);
            Assertions.assertEquals(Arrays.asList(7, 8, 9, 11, 12), p.get());

            table.addRowSelectionInterval(17, 15);
            Assertions.assertEquals(Arrays.asList(7, 8, 9, 11, 12, 15, 16, 17), p.get());

            table.clearSelection();
            Assertions.assertTrue(p.isEmpty());
        });
    }

    @Test
    void initial_value() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTable table = newTable();
            table.selectAll();
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            Assertions.assertEquals(table.getSelectedRowCount(), p.size());
        });
    }

    @Test
    void invalid_rows() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTable table = newTable();
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            Assertions.assertThrows(IllegalArgumentException.class, () -> p.addAll(Integer.valueOf(100)));
            Assertions.assertThrows(IllegalArgumentException.class, () -> p.addAll(Integer.valueOf(-1)));
            Assertions.assertThrows(IllegalArgumentException.class, () -> p.addAll(Integer.valueOf(0),
                    Integer.valueOf(-1)));
        });
    }

    @Test
    void change_selection_model() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTable table = newTable();
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            table.addRowSelectionInterval(1, 2);
            Assertions.assertEquals(Arrays.asList(1, 2), p.get());

            table.setSelectionModel(new DefaultListSelectionModel()); // clears selection
            Assertions.assertTrue(p.isEmpty());

            table.addRowSelectionInterval(5, 7);
            Assertions.assertEquals(Arrays.asList(5, 6, 7), p.get());
        });
    }
}
