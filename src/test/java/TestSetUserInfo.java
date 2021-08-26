import club.novaclient.gatohost.GatoHostAPI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class TestSetUserInfo {

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        File userInfo = new File("user-info.json");
        JsonObject config = gson.fromJson(new FileReader(userInfo), JsonObject.class);
        GatoHostAPI api = new GatoHostAPI(config.get("baseURL").getAsString(), config.get("key").getAsString());
        GatoHostAPI.SetUserInfoQuery query = new GatoHostAPI.SetUserInfoQuery.Builder(api)
                .authorText("WORKED OMG!")
                .build();
        GatoHostAPI.SetUserInfoResponse execute = api.execute(query);
    }

}
