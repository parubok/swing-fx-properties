/*
 * Copyright (c) 2013, 2014, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.swingfx.property.adapter;

import swingfx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import swingfx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;

public final class JavaBeanQuickAccessor {

    private JavaBeanQuickAccessor() {
    }

    public static <T> ReadOnlyJavaBeanObjectProperty<T> createReadOnlyJavaBeanObjectProperty(Object bean, String name) throws NoSuchMethodException {
        return ReadOnlyJavaBeanObjectPropertyBuilder.<T>create().bean(bean).name(name).build();
    }

}
