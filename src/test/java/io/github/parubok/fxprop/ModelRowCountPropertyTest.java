package io.github.parubok.fxprop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;
import io.github.parubok.swingfx.beans.value.ObservableValue;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelRowCountPropertyTest {
    @Test
    public void test_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultTableModel model = new DefaultTableModel(3, 2);
            JTable table = new JTable(model);
            ReadOnlyIntegerProperty rowCountProperty = SwingPropertySupport.modelRowCountProperty(table);

            List<Number> values = new ArrayList<>();
            rowCountProperty.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
                    Assertions.assertEquals(rowCountProperty, observable);
                    values.add(oldValue);
                    values.add(newValue);
                }
            });

            Assertions.assertEquals(3, rowCountProperty.get());
            model.removeRow(0);
            Assertions.assertEquals(2, rowCountProperty.get());
            Assertions.assertIterableEquals(Arrays.asList(3, 2), values);

            table.setModel(new DefaultTableModel(10, 2));
            Assertions.assertEquals(10, rowCountProperty.get());
            Assertions.assertIterableEquals(Arrays.asList(3, 2, 2, 10), values);

            model.removeRow(0); // old model - no effect
            Assertions.assertIterableEquals(Arrays.asList(3, 2, 2, 10), values);

            ((DefaultTableModel) table.getModel()).removeRow(0);
            Assertions.assertIterableEquals(Arrays.asList(3, 2, 2, 10, 10, 9), values);
        });
    }
}
