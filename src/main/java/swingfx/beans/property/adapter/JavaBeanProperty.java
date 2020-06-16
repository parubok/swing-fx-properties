/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
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

package swingfx.beans.property.adapter;

import swingfx.beans.property.Property;

/**
 * {@code JavaBeanProperty&lt;T&gt;} is the super interface of all adapters between
 * writable Java Bean properties and JavaFX properties.
 *
 * @param T The type of the wrapped property
 * @since JavaFX 2.1
 */
public interface JavaBeanProperty<T> extends ReadOnlyJavaBeanProperty<T>, Property<T> {
}
