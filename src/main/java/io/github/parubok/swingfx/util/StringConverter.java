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

package io.github.parubok.swingfx.util;

/**
 * Converter defines conversion behavior between strings and objects.
 * The type of objects and formats of strings are defined by the subclasses
 * of Converter.
 * @since JavaFX 2.0
 */
public abstract class StringConverter<T> {
    /**
    * Converts the object provided into its string form.
    * Format of the returned string is defined by the specific converter.
    * @return a string representation of the object passed in.
    */
    public abstract String toString(T object);

    /**
    * Converts the string provided into an object defined by the specific converter.
    * Format of the string and type of the resulting object is defined by the specific converter.
    * @return an object representation of the string passed in.
    */
    public abstract T fromString(String string);
}
