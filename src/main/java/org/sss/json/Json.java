/*
Copyright (c) 2014 William Cherry (wcherry69@gmail.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package org.sss.json;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Json provides a quick and convenient way of searlizing and desearlizing
 * objects to and from JSON text .
 * The texts produced strictly conform to JSON syntax rules. No whitespace is
 * added, so the results are ready for transmission or storage. Each instance of
 * JSONWriter can produce one JSON text.
 * <p>
 * A JSONWriter instance provides a <code>value</code> method for appending
 * values to the
 * text, and a <code>key</code>
 * method for adding keys before values in objects. There are <code>array</code>
 * and <code>endArray</code> methods that make and bound array values, and
 * <code>object</code> and <code>endObject</code> methods which make and bound
 * object values. All of these methods return the JSONWriter instance,
 * permitting a cascade style. For example, <pre>
 * Writer writer = new Json().searlize(new Foo("Bar", 7));
 * System.out.println(writer);  // {"Foo": {"bar": "Bar", "num": 7}}</pre>
 * <p>
 * This can sometimes be easier than using a JSONObject to build a string.
 * @author wcherry
 * @version 2014-03-05
 */
public class Json {
    protected boolean useFields = false;
    protected int maxDepth = 1;

    public Writer searlize(Object o) throws JsonException {
        StringWriter sw = new StringWriter();
        JsonWriter writer = new JsonWriter();
        String s = writer.writeObject(o, useFields);
        sw.append(s);
        sw.flush();
        return sw;
    }

    public Object desearlize(Reader r, Class c) throws JsonException{
        JsonReader reader = new JsonReader();
        Object o = reader.readObject(r, c.getName());
        return o;
    }

    public Json setUseFields(boolean useFields) {
        this.useFields = useFields;
        return this;
    }

    public Json setMaxDepth(int depth){
        maxDepth = depth;
        return this;
    }

    public boolean isUseFields(){
        return useFields;
    }

    public int getMaxDepth(){
        return maxDepth;
    }
}
