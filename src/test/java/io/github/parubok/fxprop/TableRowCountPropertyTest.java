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
    public void test1() throws Exception {
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
            rowSorter.setRowFilter(new RowFilter<Object, Integer>() {
                @Override
                public boolean include(Entry<?, ? extends Integer> entry) {
                    return true;
                }
            });
            Assertions.assertEquals(Arrays.asList(Integer.valueOf(0), Integer.valueOf(20)), values);
        });
    }
}
