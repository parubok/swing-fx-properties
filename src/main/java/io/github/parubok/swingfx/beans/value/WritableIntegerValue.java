/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package io.github.parubok.swingfx.beans.value;

/**
 * A writable int value.
 *
 * @see WritableValue
 * @see WritableNumberValue
 *
 *
 * @since JavaFX 2.0
 */
public interface WritableIntegerValue extends WritableNumberValue {

    /**
     * Get the wrapped value.
     * Unlike {@link #getValue()},
     * this method returns primitive int.
     * Needs to be identical to {@link #getValue()}.
     *
     * @return The current value
     */
    int get();

    /**
     * Set the wrapped value.
     * Unlike {@link #setValue(java.lang.Number) },
     * this method uses primitive int.
     *
     * @param value
     *            The new value
     */
    void set(int value);

    /**
     * Set the wrapped value.
     * <p>
     * Note: this method should accept null without throwing an exception,
     * setting "0" instead.
     *
     * @param value
     *            The new value
     */
    @Override
    void setValue(Number value);
}
