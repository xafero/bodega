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

import org.junit.*;

public class RetentionMapTest {

    @Before
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

    @Test
    public void testSecondMap() throws MalformedURLException {
        Map<Object, Object> map = new RetentionMap<>();
        assertEquals(false, map.get(true));
        assertEquals((byte) 2, map.get((byte) 1));
        assertEquals((short) 2, map.get((short) 1));
        assertEquals('b', map.get('a'));
        assertEquals(2, map.get(1));
        assertEquals(2L, map.get(1L));
        assertEquals(2f, map.get(1f));
        assertEquals(2.0, map.get(1.0));
        assertEquals("b", map.get("a"));
        assertEquals(BigDecimal.valueOf(1), map.get(BigDecimal.valueOf(42.123)));
        assertEquals(BigInteger.valueOf(1), map.get(BigInteger.valueOf(42123)));
        assertEquals(Day.FRIDAY, map.get(Day.MONDAY));
        assertEquals(Color.RED, map.get(Color.BLUE));
        assertEquals(URI.create("api://b"), map.get(URI.create("mem://a")));
        assertEquals(new URL("ftp://b"), map.get(new URL("http://a")));
        assertEquals(new Date(1L), map.get(new Date(0L)));
        assertEquals(null, map.get(null));
        assertEquals(17, map.size());
    }
}
