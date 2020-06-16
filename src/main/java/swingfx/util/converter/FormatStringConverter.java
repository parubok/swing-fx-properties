/*
 * Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package swingfx.util.converter;

import java.text.*;
import swingfx.beans.NamedArg;
import swingfx.util.StringConverter;

/**
 * <p>{@link StringConverter} implementation that can use a {@link Format}
 * instance.</p>
 *
 * @since JavaFX 2.2
 */
public class FormatStringConverter<T> extends StringConverter<T> {

    // ------------------------------------------------------ Private properties

    final Format format;

    // ------------------------------------------------------------ Constructors

    public FormatStringConverter(@NamedArg("format") Format format) {
        this.format = format;
    }

    // ------------------------------------------------------- Converter Methods

    /** {@inheritDoc} */
    @Override public T fromString(String value) {
        // If the specified value is null or zero-length, return null
        if (value == null) {
            return null;
        }

        value = value.trim();

        if (value.length() < 1) {
            return null;
        }

        // Create and configure the parser to be used
        Format _format = getFormat();

        // Perform the requested parsing, and attempt to conver the output
        // back to T
        final ParsePosition pos = new ParsePosition(0);
        T result = (T) _format.parseObject(value, pos);
        if (pos.getIndex() != value.length()) {
            throw new RuntimeException("Parsed string not according to the format");
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override public String toString(T value) {
        // If the specified value is null, return a zero-length String
        if (value == null) {
            return "";
        }

        // Create and configure the formatter to be used
        Format _format = getFormat();

        // Perform the requested formatting
        return _format.format(value);
    }

    /**
     * <p>Return a <code>Format</code> instance to use for formatting
     * and parsing in this {@link StringConverter}.</p>
     */
    protected Format getFormat() {
        return format;
    }
}
