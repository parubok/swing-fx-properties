package org.swingfx;

/**
 * Keys for {@link javax.swing.JComponent#getClientProperty(Object)} and {@link javax.swing.JComponent#putClientProperty(Object, Object)} to store/retrieve SwingFX property objects.
 */
public interface ClientProps {
    String PROP_ENABLED = "swingfx-property-enabled";
    String PROP_FOCUSED = "swingfx-property-focused";
    String PROP_VISIBLE = "swingfx-property-visible";
    String PROP_SELECTED = "swingfx-property-selected";
    String PROP_TEXT = "swingfx-property-text";
    String PROP_SELECTED_ROW_COUNT = "swingfx-property-selected-row-count";
    String PROP_TABLE_MODEL_ROW_COUNT = "swingfx-property-table-model-row-count";
    String PROP_FOREGROUND = "swingfx-property-foreground";
    String PROP_BACKGROUND = "swingfx-property-background";
    String PROP_SELECTED_ITEM = "swingfx-property-selected-item";
}
