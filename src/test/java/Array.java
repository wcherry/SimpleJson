import java.util.ArrayList;
import java.util.List;

/**
 * Created by wcherry on 3/4/14.
 */
public class Array {
    List<String> a = new ArrayList<String>();

    Array(){
        a.add("BAR");
        a.add("COOL");
    }

    public List<String> getStuff(){
        return a;
    }

    private String getMore(){
        return "";
    }
}
