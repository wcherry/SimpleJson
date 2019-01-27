/**
 * Created with IntelliJ IDEA.
 * User: wcherry
 * Date: 2/14/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test;
import static org.junit.Assert.*;

import org.sss.json.JsonReader;
import org.sss.json.JsonUtil;
import org.sss.json.JsonWriter;

import java.io.StringReader;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

public class TestJson {

    /*    @Test
        public void testBasic() throws Exception{
            JsonReader reader = new JsonReader();
            StringReader r = new StringReader("{\"Pair\":{\"a\":\"1\",\"b\":\"2\"}}");
            Object obj = reader.readObject(r, "Pair");
            assertEquals("Not right", Pair.class.getName(), obj.getClass().getName());
            Pair pair = (Pair)obj;
            assertEquals("Wrong Value", 1, pair.getA());
        }
      */
    @Test
    public void testWriter() throws Exception {
        JsonWriter writer = new JsonWriter();
        String s = writer.writeObject(new Pair(7, "Hello"), false);

        assertEquals("JSON not equal", "{\"Pair\": {\"a\": 7, \"b\": 0, \"d\": \"Hello\"}}", s);

    }

    @Test
    public void testWriterObjectWithNull() throws Exception {
        JsonWriter writer = new JsonWriter();
        String s = writer.writeObject(new Pair(7, null), false);

        assertEquals("JSON not equal", "{\"Pair\": {\"a\": 7, \"b\": 0, \"d\": null}}", s);

    }

    @Test
    public void testWriterA() throws Exception {
        JsonWriter writer = new JsonWriter();
        String s = writer.writeObject(new SimpleObject(), false);

        assertEquals("JSON not using toJson method", "{\"A\": \"AAAA\"}", s);

    }

    @Test
    public void testWriterArrayInObject() throws Exception {
        JsonWriter writer = new JsonWriter();
        String s = writer.writeObject(new Array(), false);

        assertEquals("JSON not supporting arrays", "{\"Array\": {\"stuff\": [\"BAR\", \"COOL\"]}}", s);

    }

    @Test
    public void testWriterList() throws Exception {
        JsonWriter writer = new JsonWriter();
        List<String> list = new ArrayList<String>();
        list.add("Hello");
        list.add("World");

        String s = writer.writeArray(list);

        assertEquals("JSON not supporting list", "[\"Hello\", \"World\"]", s);
    }

    @Test
    public void testWriterArray() throws Exception {
        JsonWriter writer = new JsonWriter();
        List<String> list = new ArrayList<String>();
        list.add("Hello");
        list.add("World");

        String[] strs = list.toArray(new String[0]);

        String s = writer.writeArray(strs);

        assertEquals("JSON not supporting arrays", "[\"Hello\", \"World\"]", s);
    }

    @Test
    public void testWriterArrayPrimitive() throws Exception {
        JsonWriter writer = new JsonWriter();
        int[] ints = {1, 2, 3};

        String s = writer.writeArray(ints);

        assertEquals("JSON not supporting arrays", "[1, 2, 3]", s);
    }

    @Test
    public void testWriterArrayPrimitive2() throws Exception {
        JsonWriter writer = new JsonWriter();
        boolean[] bools = {true, false, true};

        String s = writer.writeArray(bools);

        assertEquals("JSON not supporting arrays", "[true, false, true]", s);
    }

//    @Test
//    public void testWriterListOfObjects() throws Exception {
//        JsonWriter writer = new JsonWriter();
//        List<Object> list = new ArrayList<Object>();
//        list.add(new Pair(7, "Hello"));
//        list.add(new Pair(3, "World"));
//
//        String s = writer.writeArray(list);
//
//        assertEquals("JSON not working for Arrays of Objects", "{["+
//                "{\"Pair\": {\"a\": 7, \"b\": 0, \"d\": \"Hello\"}}, " +
//                "{\"Pair\": {\"a\": 3, \"b\": 0, \"d\": \"World\"}}" +
//                "]}", s);
//    }

    @Test
    public void testWriteArray() throws Exception {
        JsonWriter json = new JsonWriter();
        String result;
        result = json.writeArray(new byte[]{1, 2, 3, 4, 5, 6});
        assertNotNull(result);
        assertEquals("[1, 2, 3, 4, 5, 6]", result);


    }
}