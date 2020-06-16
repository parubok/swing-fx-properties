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

package swingfx.util;

/**
 * Interface representing a builder. Builders are objects that are used to
 * construct other objects.
 *
 * @since JavaFX 2.0
 */
@FunctionalInterface
public interface Builder<T> {
    /**
     * Builds and returns the object.
     */
    public T build();
}
