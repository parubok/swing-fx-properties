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
package com.sun.swingfx.property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import swingfx.beans.property.ReadOnlyObjectProperty;

public final class JavaBeanAccessHelper {

    private static Method JAVA_BEAN_QUICK_ACCESSOR_CREATE_RO;

    private static boolean initialized;

    private JavaBeanAccessHelper() {

    }

    public static <T> ReadOnlyObjectProperty<T> createReadOnlyJavaBeanProperty(Object bean, String propertyName) throws NoSuchMethodException{
        init();
        if (JAVA_BEAN_QUICK_ACCESSOR_CREATE_RO == null) {
            throw new UnsupportedOperationException("Java beans are not supported.");
        }
        try {
            return (ReadOnlyObjectProperty<T>) JAVA_BEAN_QUICK_ACCESSOR_CREATE_RO.invoke(null, bean, propertyName);
        } catch (IllegalAccessException ex) {
            throw new UnsupportedOperationException("Java beans are not supported.");
        } catch (InvocationTargetException ex) {
            if (ex.getCause() instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)ex.getCause();
            }
            throw new UnsupportedOperationException("Java beans are not supported.");
        }
    }

    private static void init() {
        if (!initialized) {
            try {
                Class accessor = Class.forName(
                        "com.sun.swingfx.property.adapter.JavaBeanQuickAccessor",
                        true, JavaBeanAccessHelper.class.getClassLoader());
                JAVA_BEAN_QUICK_ACCESSOR_CREATE_RO =
                        accessor.getDeclaredMethod("createReadOnlyJavaBeanObjectProperty",
                        Object.class, String.class);
            } catch (ClassNotFoundException ex) {
                //ignore
            } catch (NoSuchMethodException ex) {
                //ignore
            }
            initialized = true;
        }
    }

}
