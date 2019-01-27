import org.junit.Test;
import org.sss.json.JsonReader;
import org.sss.json.test.Widget;

import java.io.StringReader;

import static org.junit.Assert.*;

public class TestJsonReader {

    @Test
    public void testReadObject() throws Exception{
        String text = "{\"a\": 100, \"b\": 50, \"c\": \"Hello\"}";
        StringReader reader = new StringReader(text);
        JsonReader json = new JsonReader();

        Pair p = (Pair)json.readObject(reader, "Pair");
        assertNotNull(p);
        assertEquals(100, p.getA());
        assertEquals(50, p.getB());
    }


    @Test
    public void testReadBigObject() throws Exception{
        String text = "{\n" +
                "    \"debug\": \"on\",\n" +
                "    \"window\": {\n" +
                "        \"title\": \"Sample Konfabulator org.sss.json.test.Widget\",\n" +
                "        \"name\": \"main_window\",\n" +
                "        \"width\": 500,\n" +
                "        \"height\": 500\n" +
                "    },\n" +
                "    \"image\": { \n" +
                "        \"src\": \"Images/Sun.png\",\n" +
                "        \"name\": \"sun1\",\n" +
                "        \"hOffset\": 250,\n" +
                "        \"vOffset\": 250,\n" +
                "        \"alignment\": \"center\"\n" +
                "    },\n" +
                "    \"text\": {\n" +
                "        \"data\": \"Click Here\",\n" +
                "        \"size\": 36,\n" +
                "        \"style\": \"bold\",\n" +
                "        \"name\": \"text1\",\n" +
                "        \"hOffset\": 250,\n" +
                "        \"vOffset\": 100,\n" +
                "        \"alignment\": \"center\",\n" +
                "        \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"\n" +
                "    }}";
        StringReader reader = new StringReader(text);
        JsonReader json = new JsonReader();
        json.addSearchPackage("org.sss.json.test");

        Widget p = (Widget)json.readObject(reader, "org.sss.json.test.Widget");
        assertNotNull(p);
    }




}
