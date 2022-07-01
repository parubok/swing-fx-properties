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

import io.github.parubok.swingfx.collections.ObservableList;

/**
 * A writable reference to an {@link ObservableList}.
 *
 * @see ObservableList
 * @see WritableObjectValue
 * @see WritableListValue
 *
 * @param <E> the type of the {@code List} elements
 * @since JavaFX 2.1
 */
public interface WritableListValue<E> extends WritableObjectValue<ObservableList<E>>, ObservableList<E> {
}
