package io.github.parubok.fxprop;

/**
 * Keys for {@link javax.swing.JComponent#getClientProperty(Object)}/{@link javax.swing.JComponent#putClientProperty(Object, Object)} and {@link javax.swing.text.Document#getProperty(Object)}/{@link javax.swing.text.Document#putProperty(Object, Object)} to store/retrieve property objects.
 * @implNote Using of enum ensures the key uniqueness.
 */
enum ClientProps {
    PROP_ENABLED,
    PROP_FOCUSED,
    PROP_VISIBLE,
    PROP_SELECTED,
    PROP_TEXT,
    PROP_SELECTED_TEXT,
    PROP_ICON,
    PROP_ICON_GETTER,
    PROP_ICON_SETTER,
    PROP_SELECTED_ROW_COUNT,
    PROP_SELECTED_ROWS,
    PROP_TABLE_MODEL_ROW_COUNT,
    PROP_TABLE_ROW_COUNT,
    PROP_FOREGROUND,
    PROP_BACKGROUND,
    PROP_SELECTED_ITEM,
    PROP_SELECTED_VALUE,
    PROP_SELECTED_INDEX,
    PROP_MOUSE_OVER,
    PROP_BORDER,
    PROP_VALID_INPUT,
    PROP_SELECTED_PATH,
    PROP_FONT,
}
