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

import swingfx.collections.ObservableSet;

/**
 * An observable reference to an {@link ObservableSet}.
 *
 * @see ObservableSet
 * @see swingfx.beans.value.ObservableObjectValue
 * @see ObservableValue
 *
 * @param <E> the type of the {@code Set} elements
 * @since JavaFX 2.1
 */
public interface ObservableSetValue<E> extends ObservableObjectValue<ObservableSet<E>>, ObservableSet<E> {
}
