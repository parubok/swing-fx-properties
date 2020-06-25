package org.swingfx;

/**
 * Keys for {@link javax.swing.JComponent#getClientProperty(Object)}/{@link javax.swing.JComponent#putClientProperty(Object, Object)} and {@link javax.swing.text.Document#getProperty(Object)}/{@link javax.swing.text.Document#putProperty(Object, Object)} to store/retrieve SwingFX property objects.
 */
public enum ClientProps {
    PROP_ENABLED,
    PROP_FOCUSED,
    PROP_VISIBLE,
    PROP_SELECTED,
    PROP_TEXT,
    PROP_ICON,
    PROP_SELECTED_ROW_COUNT,
    PROP_TABLE_MODEL_ROW_COUNT,
    PROP_FOREGROUND,
    PROP_BACKGROUND,
    PROP_SELECTED_ITEM,
    PROP_MOUSE_OVER,
    PROP_BORDER,
    PROP_VALID_INPUT
}
