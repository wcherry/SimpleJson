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

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wcherry
 * Date: 2/14/13
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class JsonReader {
    HashMap<String, String> shortNameToLongName = new HashMap<String, String>();
    ArrayList<String> searchPackages = new ArrayList<>();

    public JsonReader(){
        searchPackages.add("");
    }

    private Object getObject(String name) throws JsonException {
        try {
            name = createClassName(name);

            String objectName = shortNameToLongName.get(name);
            if (objectName == null) objectName = name;

            Module module = this.getClass().getModule();
            for (String packageName : searchPackages) {
                String className = String.join("", new String[]{packageName, objectName});
                Class clazz = Class.forName(module, className);

                if (clazz != null) return clazz.getDeclaredConstructor().newInstance();
            }

            throw new ClassNotFoundException();
        } catch(Exception ex){
            throw new JsonException("Unable to get an instance of class named '"+name+"'", ex);
        }
    }

    public Object readObject(Reader text, String objectName) throws JsonException {
        Object obj = null;
        try{
            obj = parseObject(text, objectName);
        } catch(Exception ex){
            throw new JsonException("Error parsing object '"+objectName+"'", ex);
        }
        return obj;
    }

    private Object convertValue(Object obj, Class cl) throws JsonException {
        if(cl==Integer.class||cl==int.class){
            obj = Integer.parseInt((String)obj);
        } else if(cl ==String.class){
            // nothing
        } else {
            //throw new JsonException("Unaassginable type "+cl.getName(),null);
        }
        return obj;
    }

    private void setValue(Object o, String name, Object value) throws InvocationTargetException, IllegalAccessException, JsonException {
        ArrayList<Method> methods = new ArrayList<Method>();
        Method[] allMethods = o.getClass().getMethods();
        String setter = "SET"+name.toUpperCase();
        for(Method m: allMethods){
            if(m.getName().toUpperCase().equals(setter))
            {
                methods.add(m);
            }
        }

        // FORNOW: Cheat here and use the first method found.
        if(methods.size()>0){
            Method m = methods.get(0);
            Object[] args = new Object[1];
            args[0] = convertValue(value, m.getParameterTypes()[0]);
            m.invoke(o, args);
        }

    }

    Object readValue(Reader text, String name) throws IOException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException, JsonException {
        char c = peek(text);
        if(c=='{') return parseObject(text, name);
        if(c=='"') return readString(text);
        if(c=='[') return readArrary(text);
        return readLiteral(text);
    }

    private Object readArrary(Reader text) throws IOException {
        ArrayList list = new ArrayList();
        char c = read(text);
        while(c != ']'){

        }
        return list;
    }

    private Object readLiteral(Reader text) throws IOException {
        int ch = text.read();
        while(ch==' '||ch=='\t') ch = text.read();

        StringBuffer buffer = new StringBuffer();
        while(ch>0){
            buffer.append((char)ch);
            ch = text.read();
            if(Character.isWhitespace(ch)||ch=='}'||ch==',') break;
        }
        skipWhiteSpace(text);
        System.out.println("literal: "+buffer.toString());
        return buffer.toString();

    }

    private Object parseObject(Reader text, String objectName) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, JsonException {
        Object o = getObject(objectName);
        int ch = 0;
        read(text);
        while(true){
            //if(ch == '}') return o;
            String name = readString(text);
            char c = read(text);
            skipWhiteSpace(text);
            if(c!=':') throw new IllegalStateException("Expected ':' and got '"+c+"'");
            skipWhiteSpace(text);
            Object value = readValue(text,name);
            System.out.println("Value: "+value+" for "+name);
            setValue(o, name, value);
            c = read(text);
            if(c=='}') return o;
            if(c==',') {

            } else
                text.skip(-1);
        }
        //return o;
    }

    private char peek(Reader text) throws IOException {
        long count = -1;
        int ch = text.read();
        while(ch==' '||ch=='\t') {count--; ch = text.read();}
        text.skip(count);
        return (char)ch;
    }

    private void skipWhiteSpace(Reader text) throws IOException {
        int ch = text.read();
        while(ch==' '||ch=='\t') {ch = text.read();}
        text.skip(-1);
    }

    private char read(Reader text) throws IOException {
        int ch = text.read();
        while(Character.isWhitespace(ch)) ch = text.read();
        return (char)ch;
    }

    private String readString(Reader text) throws IOException {
        char ch = (char)read(text);
        if(ch!='"') throw new IllegalStateException("Expected '\"' and got '"+ch+"'");
        StringBuffer buffer = new StringBuffer();
        int c =0;
        while((c=text.read())!='"'){
            buffer.append((char)c);
        }
        return buffer.toString();
    }

    String createClassName(String name){
        String[] parts = name.split("\\.");
        int classIndex = parts.length-1;
        String className = parts[classIndex];
        className = className.substring(0,1).toUpperCase()+className.substring(1).toLowerCase();
        parts[classIndex] = className;
        return String.join(".", parts);
    }

    public void addSearchPackage(String packageName){
        searchPackages.add(packageName+".");
    }
}
