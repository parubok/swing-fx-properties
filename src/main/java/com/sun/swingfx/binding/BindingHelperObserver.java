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

package com.sun.swingfx.binding;

import java.lang.ref.WeakReference;

import swingfx.beans.InvalidationListener;
import swingfx.beans.Observable;
import swingfx.beans.binding.Binding;

public class BindingHelperObserver implements InvalidationListener {

    private final WeakReference<Binding<?>> ref;

    public BindingHelperObserver(Binding<?> binding) {
        if (binding == null) {
            throw new NullPointerException("Binding has to be specified.");
        }
        ref = new WeakReference<Binding<?>>(binding);
    }

    @Override
    public void invalidated(Observable observable) {
        final Binding<?> binding = ref.get();
        if (binding == null) {
            observable.removeListener(this);
        } else {
            binding.invalidate();
        }
    }

}
