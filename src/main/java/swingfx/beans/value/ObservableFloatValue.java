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
 * An observable float value.
 *
 * @see ObservableValue
 * @see swingfx.beans.value.ObservableNumberValue
 *
 *
 * @since JavaFX 2.0
 */
public interface ObservableFloatValue extends ObservableNumberValue {

    /**
     * Returns the current value of this {@code ObservableFloatValue}.
     *
     * @return The current value
     */
    float get();
}
