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

package swingfx.collections;

import swingfx.beans.NamedArg;
import swingfx.beans.WeakListener;

import java.lang.ref.WeakReference;

/**
 * A {@code WeakSetChangeListener} can be used, if an {@link swingfx.collections.ObservableSet}
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks, that can occur if observers are not unregistered from observed
 * objects after use.
 * <p>
 * {@code WeakSetChangeListener} are created by passing in the original
 * {@link swingfx.collections.SetChangeListener}. The {@code WeakSetChangeListener} should then be
 * registered to listen for changes of the observed object.
 * <p>
 * Note: You have to keep a reference to the {@code SetChangeListener}, that
 * was passed in as long as it is in use, otherwise it will be garbage collected
 * to soon.
 *
 * @see swingfx.collections.SetChangeListener
 * @see ObservableSet
 * @see WeakListener
 *
 * @param <E>
 *            The type of the observed value
 *
 * @since JavaFX 2.1
 */
public final class WeakSetChangeListener<E> implements swingfx.collections.SetChangeListener<E>, WeakListener {

    private final WeakReference<swingfx.collections.SetChangeListener<E>> ref;

    /**
     * The constructor of {@code WeakSetChangeListener}.
     *
     * @param listener
     *            The original listener that should be notified
     */
    public WeakSetChangeListener(@NamedArg("listener") swingfx.collections.SetChangeListener<E> listener) {
        if (listener == null) {
            throw new NullPointerException("Listener must be specified.");
        }
        this.ref = new WeakReference<swingfx.collections.SetChangeListener<E>>(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean wasGarbageCollected() {
        return (ref.get() == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChanged(Change<? extends E> change) {
        final SetChangeListener<E> listener = ref.get();
        if (listener != null) {
            listener.onChanged(change);
        } else {
            // The weakly reference listener has been garbage collected,
            // so this WeakListener will now unhook itself from the
            // source bean
            change.getSet().removeListener(this);
        }
    }
}
