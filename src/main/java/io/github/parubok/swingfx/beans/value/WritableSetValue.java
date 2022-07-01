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

import io.github.parubok.swingfx.collections.ObservableSet;

/**
 * A writable reference to an {@link ObservableSet}.
 *
 * @see ObservableSet
 * @see WritableObjectValue
 * @see WritableSetValue
 *
 * @param <E> the type of the {@code Set} elements
 * @since JavaFX 2.1
 */
public interface WritableSetValue<E> extends WritableObjectValue<ObservableSet<E>>, ObservableSet<E> {
}
