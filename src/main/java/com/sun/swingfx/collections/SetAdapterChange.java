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

package com.sun.swingfx.collections;

import swingfx.collections.ObservableSet;
import swingfx.collections.SetChangeListener.Change;

public class SetAdapterChange<E> extends Change<E> {
    private final Change<? extends E> change;

    public SetAdapterChange(ObservableSet<E> set, Change<? extends E> change) {
        super(set);
        this.change = change;
    }

    @Override
    public String toString() {
        return change.toString();
    }

    @Override
    public boolean wasAdded() {
        return change.wasAdded();
    }

    @Override
    public boolean wasRemoved() {
        return change.wasRemoved();
    }

    @Override
    public E getElementAdded() {
        return change.getElementAdded();
    }

    @Override
    public E getElementRemoved() {
        return change.getElementRemoved();
    }

}
