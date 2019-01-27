import org.junit.Test;
import org.sss.json.JsonUtil;

import java.util.List;

import static org.junit.Assert.*;


public class TestJsonUtil {

    @Test
    public void testAsListMethods(){
        List results;

        results = JsonUtil.asList(new byte[] {1,2,3,4,5,6});
        assertNotNull(results);
        assertEquals(6, results.size());
        assertEquals((byte)1, results.get(0));
        assertEquals((byte)6, results.get(5));
        assertTrue(results.get(0) instanceof Byte);

        results = JsonUtil.asList(new short[] {1,2,3,4,5,6});
        assertNotNull(results);
        assertEquals(6, results.size());
        assertEquals((short)1, results.get(0));
        assertEquals((short)6, results.get(5));
        assertTrue(results.get(0) instanceof Short);

        results = JsonUtil.asList(new int[] {1,2,3,4,5,6});
        assertNotNull(results);
        assertEquals(6, results.size());
        assertEquals(1, results.get(0));
        assertEquals(6, results.get(5));
        assertTrue(results.get(0) instanceof Integer);

        results = JsonUtil.asList(new long[] {1,2,3,4,5,6});
        assertNotNull(results);
        assertEquals(6, results.size());
        assertEquals((long)1, results.get(0));
        assertEquals((long)6, results.get(5));
        assertTrue(results.get(0) instanceof Long);

        results = JsonUtil.asList(new float[] {1.0f,2.0f,3.0f,4.0f,5.0f,6.0f});
        assertNotNull(results);
        assertEquals(6, results.size());
        assertEquals(1.0f, results.get(0));
        assertEquals(6.0f, results.get(5));
        assertTrue(results.get(0) instanceof Float);

        results = JsonUtil.asList(new double[] {1.0,2.0,3.0,4.0,5.0,6.0});
        assertNotNull(results);
        assertEquals(6, results.size());
        assertEquals(1.0, results.get(0));
        assertEquals(6.0, results.get(5));
        assertTrue(results.get(0) instanceof Double);

        results = JsonUtil.asList(new boolean[] {false, true});
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(false, results.get(0));
        assertEquals(true, results.get(1));
        assertTrue(results.get(0) instanceof Boolean);

        results = JsonUtil.asList(new char[] {'a','b','c','d','e','f'});
        assertNotNull(results);
        assertEquals(6, results.size());
        assertEquals('a', results.get(0));
        assertEquals('f', results.get(5));
        assertTrue(results.get(0) instanceof Character);
    }
}
