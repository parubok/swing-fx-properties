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

package io.github.parubok.com.sun.swingfx.binding;

import io.github.parubok.fxprop.misc.Logging;
import io.github.parubok.swingfx.beans.InvalidationListener;
import io.github.parubok.swingfx.beans.Observable;
import io.github.parubok.swingfx.beans.binding.Binding;

import java.lang.ref.WeakReference;

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
            Logging.getLogger().fine("Weak reference to binding was cleared.");
            observable.removeListener(this);
        } else {
            binding.invalidate();
        }
    }

}
