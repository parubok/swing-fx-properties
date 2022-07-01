/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

package io.github.parubok.com.sun.swingfx.binding;

import io.github.parubok.swingfx.beans.WeakListener;

/**
 */
public class ExpressionHelperBase {

    protected static int trim(int size, Object[] listeners) {
        for (int index = 0; index < size; index++) {
            final Object listener = listeners[index];
            if (listener instanceof WeakListener) {
                if (((WeakListener)listener).wasGarbageCollected()) {
                    final int numMoved = size - index - 1;
                    if (numMoved > 0)
                        System.arraycopy(listeners, index+1, listeners, index, numMoved);
                    listeners[--size] = null; // Let gc do its work
                    index--;
                }
            }
        }
        return size;
    }

}
