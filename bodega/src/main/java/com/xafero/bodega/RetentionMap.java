package com.xafero.bodega;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class RetentionMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    private final Properties locals;
    private final File localsFile;

    public RetentionMap() {
        this(new File("local-storage.cfg"));
    }

    public RetentionMap(File localsFile) {
        this.locals = new Properties();
        this.localsFile = localsFile;
        load(locals, localsFile);
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
        store(locals, localsFile);
        return fromString(old);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new LinkedHashSet<>();
        for (Entry<Object, Object> e : locals.entrySet()) {
            K key = fromString((String) e.getKey());
            V val = fromString((String) e.getValue());
            set.add(new SimpleImmutableEntry<>(key, val));
        }
        return set;
    }

    private String toString(Object raw) {
        if (raw instanceof Boolean) {
            return "/B/" + raw;
        }
        if (raw instanceof Byte) {
            return "/b/" + raw;
        }
        if (raw instanceof Short) {
            return "/s/" + raw;
        }
        if (raw instanceof Character) {
            return "/c/" + raw;
        }
        if (raw instanceof Integer) {
            return "/i/" + raw;
        }
        if (raw instanceof Long) {
            return "/l/" + raw;
        }
        if (raw instanceof Float) {
            return "/f/" + raw;
        }
        if (raw instanceof Double) {
            return "/d/" + raw;
        }
        if (raw instanceof String) {
            return "/S/" + raw;
        }
        if (raw instanceof BigDecimal) {
            return "/D/" + raw;
        }
        if (raw instanceof BigInteger) {
            return "/I/" + raw;
        }
        if (raw instanceof Enum) {
            return "/e/" + ((Enum) raw).getDeclaringClass().getName() + "|" + raw;
        }
        if (raw instanceof Color) {
            return "/C/" + ((Color) raw).getRGB();
        }
        if (raw instanceof URI) {
            return "/u/" + raw;
        }
        if (raw instanceof URL) {
            return "/U/" + raw;
        }
        if (raw instanceof Date) {
            return "/t/" + ((Date) raw).getTime();
        }
        if (raw == null) {
            return "/n/" + raw;
        }
        throw new UnsupportedOperationException(raw.getClass() + "!");
    }

    private <T> T fromString(String str) {
        if (str == null) {
            return null;
        }
        String type = "";
        String val = str;
        if (str.startsWith("/")) {
            String[] pt = str.substring(1).split("/", 2);
            type = pt[0];
            val = pt[1];
            switch (type) {
                case "B":
                    return cast(Boolean.parseBoolean(val));
                case "i":
                    return cast(Integer.parseInt(val));
                case "D":
                    return cast(new BigDecimal(val));
                case "S":
                    return cast(val);
                case "C":
                    return cast(new Color(Integer.parseInt(val)));
                case "s":
                    return cast(Short.parseShort(val));
                case "e":
                    String[] tmp = val.split("\\|");
                    return cast(Enum.valueOf(getClass(tmp[0]), tmp[1]));
                case "n":
                    return null;
                case "u":
                    return cast(URI.create(val));
                case "b":
                    return cast(Byte.parseByte(val));
                case "t":
                    return cast(new Date(Long.parseLong(val)));
                case "U":
                    return cast(getURL(val));
                case "c":
                    return cast(val.charAt(0));
                case "l":
                    return cast(Long.parseLong(val));
                case "f":
                    return cast(Float.parseFloat(val));
                case "I":
                    return cast(new BigInteger(val));
                case "d":
                    return cast(Double.parseDouble(val));
                default:
                    break;
            }
        }
        throw new UnsupportedOperationException(type + "=" + val);
    }

    private <T> T cast(Object raw) {
        return (T) raw;
    }

    private Class getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private URL getURL(String txt) {
        try {
            return new URL(txt);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private void load(Properties props, File file) {
        if (!file.exists()) {
            return;
        }
        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
        } catch (IOException ex) {
            throw new UnsupportedOperationException(file + "", ex);
        }
    }

    private void store(Properties props, File file) {
        try (FileOutputStream out = new FileOutputStream(file)) {
            props.store(out, "");
        } catch (IOException ex) {
            throw new UnsupportedOperationException(file + "", ex);
        }
    }
}
