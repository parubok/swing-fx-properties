package org.swingfx;

import com.sun.swingfx.collections.ObservableListWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.ListProperty;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Arrays;

class SelectedRowsPropertyImplTest {
    @Test
    void basic_test() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(10, 3);
            JTable table1 = new JTable();
            table1.setModel(model);
            table1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ListProperty<Integer> selRowsProp1 = SwingPropertySupport.selectedRowsProperty(table1);
            JTable table2 = new JTable();
            table2.setModel(model);
            table2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
            TableModel model = new DefaultTableModel(10, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            p.add(3);
            Assertions.assertArrayEquals(new int[]{3}, table.getSelectedRows());
            p.set(new ObservableListWrapper<>(new ArrayList<>(Arrays.asList(4, 6))));
            Assertions.assertArrayEquals(new int[]{4, 6}, table.getSelectedRows());

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
            TableModel model = new DefaultTableModel(20, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            Assertions.assertTrue(p.isEmpty());
            Assertions.assertEquals(0, table.getSelectedRowCount());
            p.get().setAll(9, 7, 8); // unsorted
            Assertions.assertEquals(Arrays.asList(9, 7, 8), p.get());
            Assertions.assertArrayEquals(new int[]{7, 8, 9}, table.getSelectedRows());

            table.getSelectionModel().addSelectionInterval(11, 12);
            Assertions.assertEquals(Arrays.asList(7, 8, 9, 11, 12), p.get());

            table.getSelectionModel().addSelectionInterval(17, 15);
            Assertions.assertEquals(Arrays.asList(7, 8, 9, 11, 12, 15, 16, 17), p.get());

            table.clearSelection();
            Assertions.assertTrue(p.isEmpty());
        });
    }

    @Test
    void initial_value() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(20, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            table.selectAll();
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            Assertions.assertEquals(table.getSelectedRowCount(), p.size());
        });
    }

    @Test
    void invalid_rows() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(20, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            Assertions.assertThrows(IllegalArgumentException.class, () -> p.addAll(Integer.valueOf(100)));
        });
    }
}
