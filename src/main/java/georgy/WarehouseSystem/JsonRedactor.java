package georgy.WarehouseSystem;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JsonRedactor {
    static public List<String> getDBLogin(){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("json/login_bd.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            LinkedList<String> result = new LinkedList<>();

            result.add((String) jsonObject.get("LOGIN"));
            result.add((String) jsonObject.get("Password"));
            return result;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
