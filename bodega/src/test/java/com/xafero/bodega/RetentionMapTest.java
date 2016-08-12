package com.xafero.bodega;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;

public class RetentionMapTest {

    @Test
    public void testFirstMap() throws MalformedURLException {
        Map<Object, Object> map = new RetentionMap<>();
        map.put(true, false);
        map.put((byte) 1, (byte) 2);
        map.put((short) 1, (short) 2);
        map.put('a', 'b');
        map.put(1, 2);
        map.put(1L, 2L);
        map.put(1f, 2f);
        map.put(1.0, 2.0);
        map.put("a", "b");
        map.put(BigDecimal.valueOf(42.123), BigDecimal.ONE);
        map.put(BigInteger.valueOf(42123), BigInteger.ONE);
        map.put(Day.MONDAY, Day.FRIDAY);
        map.put(Color.BLUE, Color.RED);
        map.put(URI.create("mem://a"), URI.create("api://b"));
        map.put(new URL("http://a"), new URL("ftp://b"));
        map.put(new Date(0L), new Date(1L));
        map.put(null, null);
        assertEquals(17, map.size());
    }
}
