import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONReader {
    private ArrayList<String> types = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Long> ports = new ArrayList<>();
    private ArrayList<String>questions = new ArrayList<>();
    private ArrayList<ArrayList<String>>options = new ArrayList<ArrayList<String>>();
    private ArrayList<Integer>answers = new ArrayList<>();
    public void userRead(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("users.json"));
            JSONArray jsonArray = (JSONArray) obj;
            Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject jsonObject = (JSONObject)iterator.next();
                types.add((String)jsonObject.get("type"));
                ports.add((Long)jsonObject.get("port"));
                names.add((String)jsonObject.get("name"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void questionRead(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("questions.json"));
            JSONArray jsonArray = (JSONArray) obj;
            Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject jsonObject = (JSONObject)iterator.next();
                questions.add((String)jsonObject.get("question"));
                JSONArray options_array = (JSONArray)jsonObject.get("options");
                ArrayList<String>temp = new ArrayList<>();
                Iterator optIterate = options_array.iterator();
                while (optIterate.hasNext()){
                    temp.add((String) optIterate.next());
                }
                options.add(temp);
                answers.add(Math.toIntExact((Long) jsonObject.get("answer")));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Long> getPorts() {
        return ports;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public ArrayList<ArrayList<String>> getOptions() {
        return options;
    }

    public ArrayList<Integer> getAnswers() {
        return answers;
    }
}
