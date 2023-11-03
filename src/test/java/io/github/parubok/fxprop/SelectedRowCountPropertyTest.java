package io.github.parubok.fxprop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;
import io.github.parubok.swingfx.beans.value.ObservableValue;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SelectedRowCountPropertyTest {
    @Test
    public void test_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(10, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ReadOnlyIntegerProperty selRowCountProp = SwingPropertySupport.selectedRowCountProperty(table);
            Assertions.assertEquals(0, selRowCountProp.get());
            table.getSelectionModel().setSelectionInterval(0, 0);
            Assertions.assertEquals(1, selRowCountProp.get());

            ListSelectionModel oldSelectionModel = table.getSelectionModel();

            // test that the property continues to work after the selection model is replaced:
            table.setSelectionModel(new DefaultListSelectionModel());
            Assertions.assertEquals(0, selRowCountProp.get());
            table.getSelectionModel().setSelectionInterval(0, 0);
            Assertions.assertEquals(1, selRowCountProp.get());

            table.getSelectionModel().setSelectionInterval(0, 2);
            Assertions.assertEquals(3, selRowCountProp.get());
            table.clearSelection();
            Assertions.assertEquals(0, selRowCountProp.get());

            oldSelectionModel.setSelectionInterval(0, 1); // should not affect the property
            Assertions.assertEquals(0, selRowCountProp.get());
        });
    }

    @Test
    public void test_2() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(10, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ReadOnlyIntegerProperty selRowCountProp = SwingPropertySupport.selectedRowCountProperty(table);
            List<Number> values = new ArrayList<>();
            selRowCountProp.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
                    values.add(newValue);
                }
            });
            table.getSelectionModel().setSelectionInterval(0, 1);
            Assertions.assertEquals(2, selRowCountProp.get());
            Assertions.assertIterableEquals(Collections.singletonList(2), values);
            table.clearSelection();
            Assertions.assertIterableEquals(Arrays.asList(2, 0), values);
            table.clearSelection(); // should not fire event
            Assertions.assertIterableEquals(Arrays.asList(2, 0), values);
        });
    }
}
