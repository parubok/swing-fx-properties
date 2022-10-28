package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;
import io.github.parubok.swingfx.beans.value.ObservableValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableRowCountPropertyTest {

    @Test
    public void setRowFilter() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(20, 3);
            JTable table = new JTable();
            table.setModel(model);
            TableRowSorter<?> rowSorter = new TableRowSorter<>(model);
            table.setRowSorter(rowSorter);

            List<Number> values = new ArrayList<>();
            ReadOnlyIntegerProperty p = SwingPropertySupport.rowCountProperty(table);
            p.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    values.add(oldValue);
                    values.add(newValue);
                }
            });
            rowSorter.setRowFilter(new RowFilter<Object, Integer>() {
                @Override
                public boolean include(Entry<?, ? extends Integer> entry) {
                    return false;
                }
            });
            Assertions.assertEquals(Arrays.asList(Integer.valueOf(20), Integer.valueOf(0)), values);

            values.clear();
            rowSorter.setRowFilter(null);
            Assertions.assertEquals(Arrays.asList(Integer.valueOf(0), Integer.valueOf(20)), values);
            // no event on the same filter:
            rowSorter.setRowFilter(null);
            Assertions.assertEquals(Arrays.asList(Integer.valueOf(0), Integer.valueOf(20)), values);
        });
    }

    @Test
    public void removeRowFromTableModel() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultTableModel model = new DefaultTableModel(20, 3);
            JTable table = new JTable();
            table.setModel(model);
            TableRowSorter<?> rowSorter = new TableRowSorter<>(model);
            table.setRowSorter(rowSorter);

            List<Number> values = new ArrayList<>();
            ReadOnlyIntegerProperty p = SwingPropertySupport.rowCountProperty(table);
            p.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    values.add(oldValue);
                    values.add(newValue);
                }
            });
            model.removeRow(19);
            Assertions.assertEquals(Arrays.asList(Integer.valueOf(20), Integer.valueOf(19)), values);
        });
    }

    @Test
    public void noEventOnUpdateTableModel() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultTableModel model = new DefaultTableModel(20, 3);
            JTable table = new JTable();
            table.setModel(model);
            TableRowSorter<?> rowSorter = new TableRowSorter<>(model);
            table.setRowSorter(rowSorter);

            List<Number> values = new ArrayList<>();
            ReadOnlyIntegerProperty p = SwingPropertySupport.rowCountProperty(table);
            p.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    values.add(newValue);
                }
            });
            model.setValueAt("", 0, 0);
            Assertions.assertTrue(values.isEmpty());
        });
    }
}
