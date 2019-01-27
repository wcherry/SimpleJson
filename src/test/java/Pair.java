/**
 * Created with IntelliJ IDEA.
 * User: wcherry
 * Date: 2/14/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pair {
    int a;
    int b;
    String c;

    public Pair(){}
    public Pair(int x, String s){a=x; c=s;}

    public void setA(int t){a=t;}
    public void setB(int t){b=t;}
    public void setC(String t){c=t;}

    public int getA() {
        return a;
    }
    public int getB() {
        return b;
    }
    public String getD() {
        return c;
    }
}
