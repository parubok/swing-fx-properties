/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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

package io.github.parubok.com.sun.swingfx.property.adapter;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used for registering and disposing various
 * data associated with java objects.
 *
 * The object can register itself by calling the addRecord method and
 * providing a descendant of the Runnable class with overridden
 * run() method.
 *
 * When the object becomes phantom-reachable, the run() method
 * of the associated Runnable object will be called.
 */
public class Disposer implements Runnable {
    private static final ReferenceQueue queue = new ReferenceQueue();
    private static final Map<Object, Runnable> records = new ConcurrentHashMap<>();
    private static Disposer disposerInstance;

    static {
        disposerInstance = new Disposer();

        java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction() {
                public Object run() {
                    /* The thread must be a member of a thread group
                     * which will not get GCed before VM exit.
                     * Make its parent the top-level thread group.
                     */
                    ThreadGroup tg = Thread.currentThread().getThreadGroup();
                    for (ThreadGroup tgn = tg;
                         tgn != null;
                         tg = tgn, tgn = tg.getParent());
                    Thread t =
                        new Thread(tg, disposerInstance, "Property Disposer");
                    t.setContextClassLoader(null);
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    t.start();
                    return null;
                }
            }
        );
    }

    /**
     * Registers the object and the data for later disposal.
     * @param target Object to be registered
     * @param rec the associated Runnable object
     */
    public static void addRecord(Object target, Runnable rec) {
        PhantomReference ref = new PhantomReference<>(target, queue);
        records.put(ref, rec);
    }

    public void run() {
        while (true) {
            try {
                Object obj = queue.remove();
                ((Reference)obj).clear();
                Runnable rec = (Runnable)records.remove(obj);
                rec.run();
            } catch (Exception e) {
                System.out.println("Exception while removing reference: " + e);
                e.printStackTrace();
            }
        }
    }
}
