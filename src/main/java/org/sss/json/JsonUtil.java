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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by wcherry on 3/4/14.
 */
public class JsonUtil {
    public static String join(List<String> strings, String sep){
        Iterator<String> it = strings.iterator();
        if(!it.hasNext()) return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append(it.next());
        while(it.hasNext()){
            buffer.append(sep).append(it.next());
        }
        return buffer.toString();
    }

    public static Method[] findGetters(Method[] methods){
        List<Method> getters = new ArrayList<Method>();
        for(Method m : methods){
            if((m.getName().startsWith("get") || m.getName().startsWith("is"))&& m.getParameterTypes().length==0 && Modifier.isPublic(m.getModifiers())){
                getters.add(m);
            }
        }
        return getters.toArray(new Method[0]);
    }

    public static String getNameFromMethod(Method method){
        String name = method.getName();
        return name.startsWith("is")?name.substring(2,3).toLowerCase()+name.substring(3):name.substring(3,4).toLowerCase()+name.substring(4);
    }

    public static List asList(byte[] a){
        ArrayList<Byte> list = new ArrayList<Byte>(a.length);
        for(byte i : a){ list.add(i); }
        return list;
    }

    public static List asList(short[] a){
        ArrayList<Short> list = new ArrayList<Short>(a.length);
        for(short i : a){ list.add(i); }
        return list;
    }
    public static List asList(int[] a){
        ArrayList<Integer> list = new ArrayList<Integer>(a.length);
        for(int i : a){ list.add(i); }
        return list;
    }
    public static List asList(long[] a){
        ArrayList<Long> list = new ArrayList<Long>(a.length);
        for(long i : a){ list.add(i); }
        return list;
    }
    public static List asList(float[] a){
        ArrayList<Float> list = new ArrayList<Float>(a.length);
        for(float i : a){ list.add(i); }
        return list;
    }
    public static List asList(double[] a){
        ArrayList<Double> list = new ArrayList<Double>(a.length);
        for(double i : a){ list.add(i); }
        return list;
    }
    public static List asList(boolean[] a){
        ArrayList<Boolean> list = new ArrayList<Boolean>(a.length);
        for(boolean i : a){ list.add(i); }
        return list;
    }
    public static List asList(char[] a){
        ArrayList<Character> list = new ArrayList<Character>(a.length);
        for(char i : a){ list.add(i); }
        return list;
    }
}
