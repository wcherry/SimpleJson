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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by wcherry on 2/24/14.
 */
public class JsonWriter {
    private static final String Q = "\"";

    protected boolean useFields = false;

    public String writeObject(Object obj, boolean useFields)  throws JsonException{
        this.useFields = useFields;
        try{
            Method m = obj.getClass().getDeclaredMethod("toJson", boolean.class);
            String s = (String)m.invoke(obj, useFields);
            return s;
        } catch(Exception ex){
            //TODO: Don't use exceptions to indicate if there is a toJson method
        }

        StringBuffer buf = new StringBuffer();
        buf.append("{\"").append(obj.getClass().getSimpleName()).append("\": {");
        buf.append(writeAllMembers(obj));
        buf.append("}}");
        return buf.toString();
    }
    public String writeArray(List list)  throws JsonException{
        StringBuffer buf = new StringBuffer();
//        buf.append("{");
        buf.append(writeArrayMember(list));
//        buf.append("}");
        return buf.toString();
    }

    public String writeArray(Object[] array)  throws JsonException{
        StringBuffer buf = new StringBuffer();
        //buf.append("{");
        buf.append(writeArrayMember(Arrays.asList(array)));
        //buf.append("}");
        return buf.toString();
    }

    public String writeArray(byte[] array)  throws JsonException{
        return writeArray(JsonUtil.asList(array));
    }

    public String writeArray(short[] array)  throws JsonException{
        return writeArray(JsonUtil.asList(array));
    }

    public String writeArray(int[] array)  throws JsonException{
        return writeArray(JsonUtil.asList(array));
    }

    public String writeArray(long[] array)  throws JsonException{
        return writeArray(JsonUtil.asList(array));
    }

    public String writeArray(float[] array)  throws JsonException{
        return writeArray(JsonUtil.asList(array));
    }

    public String writeArray(double[] array)  throws JsonException{
        return writeArray(JsonUtil.asList(array));
    }

    public String writeArray(boolean[] array)  throws JsonException{
        return writeArray(JsonUtil.asList(array));
    }

    public String writeArray(char[] array)  throws JsonException{
        return writeArray(JsonUtil.asList(array));
    }

    protected String writeAllMembers(Object obj) throws JsonException{
        List<String> values = new ArrayList<String>();
        if(useFields){
            Field[] fields = obj.getClass().getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            for(Field f : fields){
                values.add(writeMemberAsField(obj, f));
            }
        } else {
            Method[] methods = JsonUtil.findGetters(obj.getClass().getDeclaredMethods());
            for(Method m : methods){
                values.add(writeMemberAsGetter(obj, m));
            }
        }
        return JsonUtil.join(values, ", ");
    }

    protected String writeMemberAsGetter(Object obj, Method method) throws JsonException{
        String name = JsonUtil.getNameFromMethod(method);
        try{
            String value = writeValue(method.invoke(obj, (Object[])null));
            return Q+name+Q+": "+value;
        } catch (Exception ex){
            throw new JsonException("Invalid getter for "+name, ex);
        }
    }

    protected String writeMemberAsField(Object obj, Field field) throws JsonException{
        String name = field.getName();
        try{
            String value = writeValue(field.get(obj));
            return Q+name+Q+": "+value;
        } catch (Exception ex){
            throw new JsonException("Invalid getter for "+name, ex);
        }
    }


    protected String writeArrayMember(Object array) throws JsonException{
        List<String> a = new ArrayList<String>();
        Iterator i = ((Iterable) array).iterator();
        while(i.hasNext()){
            a.add(writeValue(i.next()));
        }

        return "["+JsonUtil.join(a, ", ")+"]";
    }

    protected String writeValue(Object o) throws JsonException{
        if(o == null) return "null";
        if(o instanceof String){
            return Q+((String)o)+Q;
        }

        if(o instanceof Number){
            return o.toString();
        }

        if(o instanceof Boolean){
            return o.toString();
        }

        if(o instanceof List || o instanceof Iterable){
            return writeArrayMember(o);
        }

        return writeObject(o, useFields);
    }

}
