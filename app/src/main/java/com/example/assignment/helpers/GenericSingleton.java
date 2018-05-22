package com.example.assignment.helpers;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a generic singleton class whose instance() method ensures
 * only one object of type Class<T> is created.  We need this class
 * since Java Generic don't support proper singletons (a la the
 * "Gang-of-Four" book).  More information about this approach appears
 * at http://neutrofoton.com/generic-singleton-pattern-in-java.
 *
 * (c) https://github.com/douglascraigschmidt/POSA-15
 */
public class GenericSingleton {
    /**
     * Debugging tag used by the Android logger.
     */
    protected final static String TAG =
        GenericSingleton.class.getCanonicalName();

    /**
     * The singleton field.
     */
    private static final GenericSingleton sInstance =
        new GenericSingleton();

    /**
     * This HashMap ensures only one object of type Class<T> is
     * created.
     */
    @SuppressWarnings("rawtypes")
    private Map<Class, Object> mMap =
        new HashMap<>();

    /**
     * Return the one and only instance of Class<T>, which is created
     * on-demand if it doesn't exist.
     */
    @SuppressWarnings("unchecked")
    public static <T> Object instance(Class<T> classOf, @Nullable Object object) {
        // Ensure thread-safety.
        synchronized (sInstance) {
            // Try to get the one and only instance of Class<T> that's
            // stored in the map.
            Object t = (T) sInstance.mMap.get(classOf);

            // Check to see if this is the first time a request for an
            // instance of Class<T> has been occurred.
            if (t != null && object == null) {
                return t;
            }else if (t == null && object == null) {
                return null;
            } else {
                // Store the new instance of Class<T> in the map so
                // it'll be available next time instance() is called.
                try {
                    sInstance.mMap.put(classOf, object);
                } catch (Exception e) {
                    LogProxy.e(TAG,e);
                }
                return object;
            }
        }
    }

    /**
     * If @a classOf is in the singleton map then set it to null so
     * it's cleaned up properly by the garbage collector.
     *
     * @return True if @a classOf is found/removed, else false.
     */
    public static <T> boolean remove(Class<T> classOf) {
        synchronized (sInstance) {
            // Try to get the one and only instance of Class<T> that's
            // stored in the map and if it's found, set it to null so
            // it will be garbage collected.
            if (sInstance.mMap.get(classOf) != null) {
                sInstance.mMap.put(classOf,
                    null);
                return true;
            } else
                return false;
        }
    }

    /**
     * If @a classOf is in the singleton map then set it to null so
     * it's cleaned up properly by the garbage collector.
     *
     * @return void
     */
    public static void removeAll() {
        synchronized (sInstance) {
            for (Map.Entry<Class, Object> entry : sInstance.mMap.entrySet()){
                if (sInstance.mMap.get(entry.getKey()) != null) {
                    sInstance.mMap.put(entry.getKey(),null);
                }
            }
        }
    }

    /**
     * Disallow instantiation.
     */
    private GenericSingleton() {
    }

    /**
     * Disallow cloning.
     */
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
