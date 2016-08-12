package com.xafero.bodega;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class RetentionMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    private final Properties locals;

    public RetentionMap() {
        this.locals = new Properties();
    }

    @Override
    public V get(Object objKey) {
        String key = toString(objKey);
        String val = locals.getProperty(key);
        return fromString(val);
    }

    @Override
    public V put(K objKey, V objVal) {
        String key = toString(objKey);
        String val = toString(objVal);
        String old = (String) locals.setProperty(key, val);
        return fromString(old);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String toString(Object key) {
        throw new UnsupportedOperationException(key.getClass() + "!");
    }

    private V fromString(String val) {
        throw new UnsupportedOperationException(val.getClass() + "!");
    }
}
