/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

package swingfx.beans.value;

/**
 * A common interface of all sub-interfaces of {@link swingfx.beans.value.ObservableValue} that wrap
 * a number.
 *
 * @param T
 *            The type of the wrapped value (a {@code Number}).
 *
 * @see swingfx.beans.value.ObservableValue
 * @see ObservableDoubleValue
 * @see ObservableFloatValue
 * @see ObservableIntegerValue
 * @see ObservableLongValue
 *
 *
 * @since JavaFX 2.0
 */
public interface ObservableNumberValue extends ObservableValue<Number> {

    /**
     * Returns the value of this {@code ObservableNumberValue} as an {@code int}
     * . If the value is not an {@code int}, a standard cast is performed.
     *
     * @return The value of this {@code ObservableNumberValue} as an {@code int}
     */
    int intValue();

    /**
     * Returns the value of this {@code ObservableNumberValue} as a {@code long}
     * . If the value is not a {@code long}, a standard cast is performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a {@code long}
     */
    long longValue();

    /**
     * Returns the value of this {@code ObservableNumberValue} as a
     * {@code float}. If the value is not a {@code float}, a standard cast is
     * performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a
     *         {@code float}
     */
    float floatValue();

    /**
     * Returns the value of this {@code ObservableNumberValue} as a
     * {@code double}. If the value is not a {@code double}, a standard cast is
     * performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a
     *         {@code double}
     */
    double doubleValue();

}
