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

package swingfx.beans.value;

import swingfx.collections.ObservableList;

/**
 * An observable reference to an {@link ObservableList}.
 *
 * @see ObservableList
 * @see swingfx.beans.value.ObservableObjectValue
 * @see ObservableValue
 *
 * @param <E> the type of the {@code List} elements
 * @since JavaFX 2.1
 */
public interface ObservableListValue<E> extends ObservableObjectValue<ObservableList<E>>, ObservableList<E> {
}
