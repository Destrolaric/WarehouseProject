package georgy.WarehouseSystem;

import com.mysql.cj.xdevapi.JsonParser;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        JsonParser jsonParser = new JsonParser();
        JSONObject jsonObject = (JSONObject) JsonParser.parse(String.valueOf(new FileReader("/home/georgy/data/WarehouseProject/json/login_bd.json")));
        String db_user= jsonObject.getJSONObject("cred").getString("LOGIN");
        String db_password = jsonObject.getJSONObject("cred").getString("Password");
        SQlConnector sQlConnector = new SQlConnector(db_user, db_password);
        if (args.length != 0 && Arrays.asList(args).contains("-c")) {
            System.out.println("Please Enter your login:");
            Scanner scanner = new Scanner(System.in);
            String login = scanner.next();
            System.out.println("Please Enter your password");
            String password = scanner.next();
        }
        else{
            //Work in progress
        }

    }
}
