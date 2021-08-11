package club.novaclient.gatohost;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class GatoHostAPI {

//    private final String key;
    private final String token;
    private final String baseUrl;

    private String userPageContent = "";

    private OkHttpClient httpClient;
    private Gson gson;

    public GatoHostAPI(String key) throws IOException {
        this("https://gato.host", key);
    }
    public GatoHostAPI(String baseUrl, String key) throws IOException {
        this.token = key;
        this.baseUrl = baseUrl;
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
        update();
    }

    private String getUserPageContent() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/user")
                .get()
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        String out = execute.body().string();
        out = out.replaceAll("\n", "").replaceAll(" ", "");
        return out;
    }
    private static String getTextBetween(String input, String before, String after) {
        return input.substring(input.indexOf(before) + before.length(), input.indexOf(after));
    }

    ////////// Properties //////////
    public String getUserName() {
        return getTextBetween(userPageContent, "<a><b>Name</b></a><br>", "</div><divclass=\"mb-1\"><a><b>Id</b></a><br>");
    }
    public String getId() {
        return getTextBetween(userPageContent, "</div><divclass=\"mb-1\"><a><b>Id</b></a><br>", "</div><divclass=\"mb-1\"><a><b>Banned</b></a><br>");
    }
    public boolean isBanned() {
        return getTextBetween(userPageContent, "</div><divclass=\"mb-1\"><a><b>Banned</b></a><br>", "</a></div><divclass=\"mb-1\"><a><b>Staff</b></a><br>").contains("Yes");
    }
    public boolean isStaff() {
        return getTextBetween(userPageContent, "/a></div><divclass=\"mb-1\"><a><b>Staff</b></a><br>", "</a></div><divclass=\"mb-1\"><a><b>RegistrationDate</b></a><br><a>").contains("Yes");
    }
    public String getRegistrationDate() {
        return getTextBetween(userPageContent, "<a><b>RegistrationDate</b></a><br><a>", "</a></div></div></div><divclass=\"containercolorformp-3mt-4\"");
    }
    public String getKey() {
        return getTextBetween(userPageContent, "<divclass=\"text-center\"><h5><b>Key</b></h5><a>", "</a><br><aclass=\"resett\"href=\"/api/user/resetkey\">resetkey");
    }
    public String getMessage() {
        return getTextBetween(userPageContent, "\"margin-top:15px;\"class=\"form-controlembedmessage\"value=\"", "\"><inputtype=\"text\"placeholder=\"AuthorMessage\"style=\"margin-top:35px;\"class=\"form-controlauthormessage\"value=\"");
    }
    public void setMessage(String message) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("authormessage", getAuthorMessage())
                .addFormDataPart("colorcode", Integer.toHexString(getColorCode()).replaceAll("0x", "#"))
                .addFormDataPart("domain", getDomain().getId())
                .addFormDataPart("domainprefix", getSubDomain())
                .addFormDataPart("embedmessage", message)
                .addFormDataPart("embedtitle", getEmbedTitle())
                .addFormDataPart("fakeurl", getFakeUrl())
                .addFormDataPart("iscustomembed", "" + isCustomEmbed())
                .addFormDataPart("sitename", getEmbedSiteName())
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/api/user/saveimagechanges")
                .post(requestBody)
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
    }
    public String getAuthorMessage() {
        return getTextBetween(userPageContent, "\"><inputtype=\"text\"placeholder=\"AuthorMessage\"style=\"margin-top:35px;\"class=\"form-controlauthormessage\"value=\"", "\"><pstyle=\"color:gray;\">Ifyouleavethisfieldblank,itwillbedisabled</p><inputtype=\"text\"placeholder=\"EmbedSiteName\"style=\"margin-top:35px;\"class=\"form-controlsitename\"value=\"");
    }
    public void setAuthorMessage(String message) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("authormessage", message)
                .addFormDataPart("colorcode", Integer.toHexString(getColorCode()).replaceAll("0x", "#"))
                .addFormDataPart("domain", getDomain().getId())
                .addFormDataPart("domainprefix", getSubDomain())
                .addFormDataPart("embedmessage", getMessage())
                .addFormDataPart("embedtitle", getEmbedTitle())
                .addFormDataPart("fakeurl", getFakeUrl())
                .addFormDataPart("iscustomembed", "" + isCustomEmbed())
                .addFormDataPart("sitename", getEmbedSiteName())
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/api/user/saveimagechanges")
                .post(requestBody)
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
    }
    public String getEmbedSiteName() {
        return getTextBetween(userPageContent, "\"><pstyle=\"color:gray;\">Ifyouleavethisfieldblank,itwillbedisabled</p><inputtype=\"text\"placeholder=\"EmbedSiteName\"style=\"margin-top:35px;\"class=\"form-controlsitename\"value=\"",
                "\"><pstyle=\"color:gray;\">Ifyouleavethisfieldblank,itwillbedisabled</p><inputtype=\"text\"placeholder=\"EmbedTitle\"style=\"margin-top:35px;\"class=\"form-controlembedtitle\"value=\"");
    }
    public void setEmbedSiteName(String embedSiteName) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("authormessage", getAuthorMessage())
                .addFormDataPart("colorcode", Integer.toHexString(getColorCode()).replaceAll("0x", "#"))
                .addFormDataPart("domain", getDomain().getId())
                .addFormDataPart("domainprefix", getSubDomain())
                .addFormDataPart("embedmessage", getMessage())
                .addFormDataPart("embedtitle", getEmbedTitle())
                .addFormDataPart("fakeurl", getFakeUrl())
                .addFormDataPart("iscustomembed", "" + isCustomEmbed())
                .addFormDataPart("sitename", embedSiteName)
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/api/user/saveimagechanges")
                .post(requestBody)
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
    }
    public String getEmbedTitle() {
        return getTextBetween(userPageContent, "\"><pstyle=\"color:gray;\">Ifyouleavethisfieldblank,itwillbedisabled</p><inputtype=\"text\"placeholder=\"EmbedTitle\"style=\"margin-top:35px;\"class=\"form-controlembedtitle\"value=\"",
                "\"><pstyle=\"color:gray;\">Ifyouleavethisfieldblank,itwillusetheimage'sfilename</p><inputtype=\"text\"placeholder=\"FakeUrl\"style=\"margin-top:35px;\"class=\"form-controlfakeurl\"value=\"");
    }
    public void setEmbedTitle(String title) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("authormessage", getAuthorMessage())
                .addFormDataPart("colorcode", Integer.toHexString(getColorCode()).replaceAll("0x", "#"))
                .addFormDataPart("domain", getDomain().getId())
                .addFormDataPart("domainprefix", getSubDomain())
                .addFormDataPart("embedmessage", getMessage())
                .addFormDataPart("embedtitle", title)
                .addFormDataPart("fakeurl", getFakeUrl())
                .addFormDataPart("iscustomembed", "" + isCustomEmbed())
                .addFormDataPart("sitename", getEmbedSiteName())
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/api/user/saveimagechanges")
                .post(requestBody)
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
    }
    public String getFakeUrl() {
        return getTextBetween(userPageContent, "\"><pstyle=\"color:gray;\">Ifyouleavethisfieldblank,itwillusetheimage'sfilename</p><inputtype=\"text\"placeholder=\"FakeUrl\"style=\"margin-top:35px;\"class=\"form-controlfakeurl\"value=\"",
                "\"><pstyle=\"color:gray;\">Ifyouleavethisfieldblank,itwillbedisabled</p><divclass=\"mb-1\"style=\"margin-top:10px;\"><aonclick=\"saveimagesettings()\"class=\"btnbtn-roundedmy-3cccolloor2pl-3pr-3\">SAVE</a></div></div></div></div></div><divclass=\"containercolorformp-3mt-4");
    }
    public void setFakeUrl(String fakeUrl) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("authormessage", getAuthorMessage())
                .addFormDataPart("colorcode", Integer.toHexString(getColorCode()).replaceAll("0x", "#"))
                .addFormDataPart("domain", getDomain().getId())
                .addFormDataPart("domainprefix", getSubDomain())
                .addFormDataPart("embedmessage", getMessage())
                .addFormDataPart("embedtitle", getEmbedTitle())
                .addFormDataPart("fakeurl", fakeUrl)
                .addFormDataPart("iscustomembed", "" + isCustomEmbed())
                .addFormDataPart("sitename", getEmbedSiteName())
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/api/user/saveimagechanges")
                .post(requestBody)
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
    }
    public boolean isCustomEmbed() {
        String text = "text";
        return userPageContent.contains("inputtype=\"checkbox\"class=\"test\"checked");
    }
    public void setCustomEmbed(boolean customEmbed) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("authormessage", getAuthorMessage())
                .addFormDataPart("colorcode", Integer.toHexString(getColorCode()).replaceAll("0x", "#"))
                .addFormDataPart("domain", getDomain().getId())
                .addFormDataPart("domainprefix", getSubDomain())
                .addFormDataPart("embedmessage", getMessage())
                .addFormDataPart("embedtitle", getEmbedTitle())
                .addFormDataPart("fakeurl", getFakeUrl())
                .addFormDataPart("iscustomembed", "" + customEmbed)
                .addFormDataPart("sitename", getEmbedSiteName())
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/api/user/saveimagechanges")
                .post(requestBody)
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
    }
    public int getColorCode() {
        String code = getTextBetween(userPageContent,
                "p><h5><b>ColorCode</b></h5><p><inputdata-jscolor=\"{}\"value=\"",
                ">;\"class=\"form-controlcolorcode\"></p><h5><b>CustomDiscordEmbed</b></h5>");
        return Integer.decode("0x" + code);
    }
    public void setColorCode(int code) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("authormessage", getAuthorMessage())
                .addFormDataPart("colorcode", Integer.toHexString(code).replaceAll("0x", "#"))
                .addFormDataPart("domain", getDomain().getId())
                .addFormDataPart("domainprefix", getSubDomain())
                .addFormDataPart("embedmessage", getMessage())
                .addFormDataPart("embedtitle", getEmbedTitle())
                .addFormDataPart("fakeurl", getFakeUrl())
                .addFormDataPart("iscustomembed", "" + isCustomEmbed())
                .addFormDataPart("sitename", getEmbedSiteName())
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/api/user/saveimagechanges")
                .post(requestBody)
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
    }
    public String getSubDomain() {
        return getTextBetween(userPageContent, "text\"name=\"subdomain\"id=\"subdomain\"class=\"form-controlcolmr-2domainnprefix\"value=\"", "\"><labelfor=\"exampleFormControlSelect1\"class=\"sr-only\">Domain</label><selectrequiredplaceholder=\"Domain\"");
    }
    public void setSubDomain(String subDomain) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("authormessage", getAuthorMessage())
                .addFormDataPart("colorcode", Integer.toHexString(getColorCode()).replaceAll("0x", "#"))
                .addFormDataPart("domain", getDomain().getId())
                .addFormDataPart("domainprefix", subDomain)
                .addFormDataPart("embedmessage", getMessage())
                .addFormDataPart("embedtitle", getEmbedTitle())
                .addFormDataPart("fakeurl", getFakeUrl())
                .addFormDataPart("iscustomembed", "" + isCustomEmbed())
                .addFormDataPart("sitename", getEmbedSiteName())
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/api/user/saveimagechanges")
                .post(requestBody)
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
    }
    public enum Domain {
        ASTOLFOSEXDOLL_TECH("astolfosexdoll.tech", "60645ff42d52fde3e6bf1503"),
        BEHIND_YOU_NINJA("behind-you.ninja", "6064a643b775b2fffe7cfeab"),
        DEEZNUTS_SCHOOL("deeznuts.school", "60eb4ffb2a9575bdb4f51414"),
        FLOPPAFRIDAY_GAMES("floppafriday.games", "60b36a1a992db09af0ffc9d6"),
        FLUXSENCE_CLUB("fluxsence.club", "60d871f436273991cca04f9c"),
        FLUXSENCE_SHOP("fluxsence.shop", "60d871d136273991cca04f9b"),
        GATO_HOST_IS_COOL("gato-host-is.cool", "60d8685b36273991cca04f98"),
        GATO_IS_POG_XYZ("gato-is-pog.xyz", "60e35aa88bd22d95f4c369b8"),
        IGMACLIENT_INFO("igmaclient.info", "60e4616d847934b3084d4ad9"),
        LIKES_EATING_ROCKS("likes-eating.rocks", "60649d112d52fde3e6bf1504"),
        MAKE_MY_PP_HARD_XYZ("make-my-pp-hard.xyz", "6113a708530403938f4578a9"),
        MAKE_YOUR_PC_PP_HARD_XYZ("make-your-pc-pp-hard.xyz", "60fd5e32607e88c80a9f3a6d"),
        MONEROMINER_TECH("monerominer.tech", "60636d5d4de4df9e20ccc8e2"),
        MVNCENTRAL_IS_IN_THE_JSON_CLUB("mvncentral-in-the-json.club", "60de05c5644a28812841d561"),
        PLEASE_DONT_BUY_A_XYZ("please-dont-buy-a.xyz", "6113a71e530403938f4578aa"),
        SIGMACLIENT_SHOP("sigmaclient.shop", "60d871ad36273991cca04f9a"),
        STONETOSS_CLUB("stonetoss.club", "60e2cb718bd22d95f4c369b7"),
        THEREALLO_GAY("thereallo.gay", "60d8699436273991cca04f99"),
        UPLOADS_SYSTEMS("uploads.systems", "60ec84cc2a9575bdb4f51415"),
        WEEBCLIENT_ROCKS("weebclient.rocks", "60db2d8bd4c9ef4140c77557"),
        ;
        String id;
        String name;
        Domain(String domainName, String id) {
            this.id = id;
            this.name = domainName;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public static Domain getDomain(String id) {
            for (Domain value : values()) {
                if (value.getId().equalsIgnoreCase(id)) {
                    return value;
                }
            }
            return null;
        }
        public static Domain getDomainByName(String name) {
            for (Domain value : values()) {
                if (value.getName().equalsIgnoreCase(name)) {
                    return value;
                }
            }
            return null;
        }
    }
    public Domain getDomain() {
        int startIndex = userPageContent.indexOf("selected>") + "selected>".length();
        int endIndex = userPageContent.indexOf("</option>", startIndex);
        return Domain.getDomainByName(userPageContent.substring(startIndex, endIndex));
    }
    public void setDomain(Domain domain) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("authormessage", getAuthorMessage())
                .addFormDataPart("colorcode", Integer.toHexString(getColorCode()).replaceAll("0x", "#"))
                .addFormDataPart("domain", domain.getId())
                .addFormDataPart("domainprefix", getSubDomain())
                .addFormDataPart("embedmessage", getMessage())
                .addFormDataPart("embedtitle", getEmbedTitle())
                .addFormDataPart("fakeurl", getFakeUrl())
                .addFormDataPart("iscustomembed", "" + isCustomEmbed())
                .addFormDataPart("sitename", getEmbedSiteName())
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/api/user/saveimagechanges")
                .post(requestBody)
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
    }
    /////////////////////////////

    ////////// Actions //////////
    public String resetKey() throws IOException {
        String url = baseUrl + "/api/user/resetkey";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("cookie", "token=" + token)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        update();
        return getKey();
    }
    public URL uploadAndGetUrl(File imageFile) throws IOException {
        if (!imageFile.exists() || !imageFile.isFile()) {
            throw new IOException("File could not be found");
        }
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", getKey())
                .addFormDataPart("img", imageFile.getName(), RequestBody.create(imageFile, MediaType.get(Files.probeContentType(imageFile.toPath()))))
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + "/img/upload/")
                .post(requestBody)
                .build();
        Call call = httpClient.newCall(request);
        Response execute = call.execute();
        String out = execute.body().string();

        try {
            JsonObject object = gson.fromJson(out, JsonObject.class);
            String url = object.get("url").getAsString();
            return new URL(url);
        } catch (Exception e) {
            try {
                JsonObject object = gson.fromJson(out, JsonObject.class);
                String url = object.get("url").getAsString().replaceAll(getFakeUrl(), "");
                return new URL(url.substring(url.indexOf("http")));
            } catch (Exception ex) {
                throw new IOException("Could not parse data. Is key valid?", e);
            }
        }
    }
    public void update() throws IOException{
        userPageContent = getUserPageContent();
    }
    /////////////////////////////
}
