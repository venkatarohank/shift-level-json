import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Main {

static void removefromchild(JSONObject parent,JSONObject child,String ch)
{
    if(!parent.containsKey(ch)){
        parent.put(ch, child.get(ch));
        child.remove(ch);
        return ;
    }
    JSONArray arr=new JSONArray();

    arr.add(parent.get(ch));
    arr.add(child.get(ch));
    parent.put(ch,arr);
}

static void solve(JSONObject curr,JSONObject parent,JSONObject gparent) {
    if (curr == null) return;

    JSONObject temp= (JSONObject) curr.clone();
    Set<String> keys = temp.keySet();

    for (String i : keys) {

        if (String.valueOf(curr.get(i).getClass()).equals("class org.json.simple.JSONObject")) {
            System.out.println(i);
            JSONObject obj = (JSONObject) curr.get(i);
            solve(obj, curr, parent);
        }
    }

    Set<String> childkeys = temp.keySet();
    if (parent != null) {


        Set<String> parentkeys = parent.keySet();

        for (String ch : childkeys) {
            for (String k : parentkeys) {
                if (k.indexOf(ch) != -1) {
                    removefromchild(parent,curr,ch);
                }
            }
        }
    }
    if (gparent != null) {


        Set<String> parentkeys = gparent.keySet();

        for (String ch : childkeys) {
            for (String k : parentkeys) {
                if (k.indexOf(ch) != -1) {
                    removefromchild(gparent,curr,ch);
                }
            }
        }
    }
}


    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
//        JSONArray arr=new JSONArray();
        JSONObject arr=new JSONObject();
        Object obj = null;

        try {
            obj = parser.parse(new FileReader("simple.json"));
            arr=(JSONObject) obj ;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            obj=new Object();
            System.out.println("Empty");
        }


        solve(arr,null,null);
        System.out.println(arr);
        System.out.println("Hello world!");
    }
}