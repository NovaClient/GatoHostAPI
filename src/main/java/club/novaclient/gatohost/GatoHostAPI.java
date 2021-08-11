package club.novaclient.gatohost;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class GatoHostAPI {

    private final String key;
    private final String uploadUrl;

    private OkHttpClient httpClient;
    private Gson gson;


    public GatoHostAPI(String key) {
        this("https://gato.host/img/upload/", key);
    }

    public GatoHostAPI(String uploadUrl, String key) {
        this.key = key;
        this.uploadUrl = uploadUrl;
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public URL uploadAndGetUrl(File imageFile) throws IOException {
        if (!imageFile.exists() || !imageFile.isFile()) {
            throw new IOException("File could not be found");
        }
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", key)
                .addFormDataPart("img", imageFile.getName(), RequestBody.create(imageFile, MediaType.get(Files.probeContentType(imageFile.toPath()))))
                .build();
        Request request = new Request.Builder()
                .url(uploadUrl)
                .post(requestBody)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        String out = execute.body().string();
        System.out.println(out);
        try {
            JsonObject object = gson.fromJson(out, JsonObject.class);
            String url = object.get("url").getAsString();
            return new URL(url);
        } catch (Exception e) {
            throw new IOException("Could not parse data. Is key valid?", e);
        }
    }

}
