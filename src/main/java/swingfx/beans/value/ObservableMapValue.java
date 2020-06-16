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

import swingfx.collections.ObservableMap;

/**
 * An observable reference to an {@link ObservableMap}.
 *
 * @see ObservableMap
 * @see swingfx.beans.value.ObservableObjectValue
 * @see ObservableValue
 *
 * @param <K> the type of the key elements of the {@code Map}
 * @param <V> the type of the value elements of the {@code Map}
 * @since JavaFX 2.1
 */
public interface ObservableMapValue<K, V> extends ObservableObjectValue<ObservableMap<K,V>>, ObservableMap<K, V> {
}
