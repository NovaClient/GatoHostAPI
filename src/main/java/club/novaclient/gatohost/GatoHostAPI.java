package club.novaclient.gatohost;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class GatoHostAPI {

//    private final String key;
    private final String key;
    private final String baseUrl;

    private static final String apiVersion = "v2";

    private OkHttpClient httpClient;
    private Gson gson;

    public GatoHostAPI(String key) throws IOException {
        this("https://gato.host", key);
    }
    public GatoHostAPI(String baseUrl, String key) throws IOException {
        this.key = key;
        this.baseUrl = baseUrl;
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public <T extends GatoResponse> T execute(GatoQuery<T> query) {
        return query.execute(this);
    }



    /////////////////////////////
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public abstract static class GatoQuery<ReturnType extends GatoResponse> {
        private final Class<ReturnType> responseType;


        GatoQuery(Class<ReturnType> responseType) {
            this.responseType = responseType;
        }

        protected Request getHttpRequest(GatoHostAPI api) {
            return null;
        }

        private Response executeAndGetRawResponse(GatoHostAPI api) {
            Response response = null;
            try {
                response = api.httpClient.newCall(getHttpRequest(api)).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        private ReturnType execute(GatoHostAPI api){
            Response response = executeAndGetRawResponse(api);
            ReturnType parsedResponse = null;
            try {
                String string = response.body().string();
                System.out.println(string);
                parsedResponse = api.gson.fromJson(string, responseType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return parsedResponse;
        }
    }
    public abstract static class GatoResponse {
        GatoResponse() {

        }
    }

    public static class GetUserInfoQuery extends GatoQuery<GetUserInfoResponse> {

        public GetUserInfoQuery() {
            super(GetUserInfoResponse.class);
        }

        @Override
        protected Request getHttpRequest(GatoHostAPI api) {
            return new Request.Builder()
                    .get()
                    .url(api.baseUrl + "/api/" + apiVersion + "/getuserinfo")
                    .header("key", api.key)
                    .build();
        }

    }
    public static class GetUserInfoResponse extends GatoResponse {
        @SerializedName("isadmin")
        @Expose
        private Boolean admin;

        @SerializedName("isbanned")
        @Expose
        private Boolean banned;

        @SerializedName("banreason")
        @Expose
        private String banReason;

        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("displayname")
        @Expose
        private String displayName;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("date")
        @Expose
        private String date;

        @SerializedName("key")
        @Expose
        private String key;

        @SerializedName("discordid")
        @Expose
        private String discordId;

        @SerializedName("discordname")
        @Expose
        private String discordName;

        @SerializedName("discordpicture")
        @Expose
        private String discordPicture;

        @SerializedName("discordtag")
        @Expose
        private String discordTag;

        @SerializedName("Embedauthormessage")
        @Expose
        private String embedAuthorMessage;

        @SerializedName("Embedsitename")
        @Expose
        private String embedSiteName;

        @SerializedName("Embedtitle")
        @Expose
        private String embedTitle;

        @SerializedName("discordCustomEmbed")
        @Expose
        private Boolean discordCustomEmbed;

        @SerializedName("fakeurl")
        @Expose
        private String fakeUrl;

        @SerializedName("imageEmbedColor")
        @Expose
        private String imageEmbedColor;

        @SerializedName("imageEmbedMessage")
        @Expose
        private String imageEmbedMessage;

        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;

        @SerializedName("urlPrefix")
        @Expose
        private String urlPrefix;

        @SerializedName("uploadtotalsize")
        @Expose
        private Integer uploadTotalSize;

        /**
         * Returns true if user is an admin
         */
        @NotNull
        public Boolean isAdmin() {
            return admin;
        }

        /**
         * Returns true if user is banned
         */
        @NotNull
        public Boolean isBanned() {
            return banned;
        }

        /**
         * Returns the ban reason. Null if player is not banned
         */
        @Nullable
        public String getBanReason() {
            return banReason;
        }

        /**
         * Get the ID of the user. For GatoHost database data saving and data processing purpose only.
         */
        @NotNull
        public String getId() {
            return id;
        }

        /**
         * Get the name of the user
         */
        @NotNull
        public String getName() {
            return name;
        }

        /**
         * Get the display name of the user
         */
        @NotNull
        public String getDisplayName() {
            return displayName;
        }

        /**
         * Get the email of the user
         */
        @NotNull
        public String getEmail() {
            return email;
        }

        /**
         * Get the hashed version of password. You can't do anything with it.
         */
        @NotNull
        public String getPassword() {
            return password;
        }

        /**
         * Get the date of account being created
         */
        public String getDate() {
            return date;
        }

        /**
         * Get the key of the user. Same as the key you provided to API
         */
        public String getKey() {
            return key;
        }

        /**
         * Get the user's Discord ID
         */
        public String getDiscordId() {
            return discordId;
        }

        /**
         * Get the user's discord name
         */
        public String getDiscordName() {
            return discordName;
        }

        /**
         * Get user's discord avatar URL
         */
        public String getDiscordPicture() {
            return discordPicture;
        }

        /**
         * Get user's tag
         */
        public String getDiscordTag() {
            String newTag = discordTag;
            if (discordTag.length() < 4) {
                newTag = "";
                for (int i = 0; i < 4 - discordTag.length(); i++) {
                    newTag += "0";
                }
                newTag += discordTag;
            }
            return newTag;
        }

        /**
         * Get discord embed's author field text
         */
        public String getEmbedAuthorMessage() {
            return embedAuthorMessage;
        }

        /**
         * Get discord embed's site name field text
         */
        public String getEmbedSiteName() {
            return embedSiteName;
        }

        /**
         * Get discord embed's title field text
         */
        public String getEmbedTitle() {
            return embedTitle;
        }

        /**
         * Returns true if user enabled discord custom embed
         */
        public Boolean isDiscordEmbedEnabled() {
            return discordCustomEmbed;
        }

        /**
         * Get the fake URL (Discord Clientside Glitch that will hide real image url)
         */
        public String getFakeUrl() {
            return fakeUrl;
        }

        /**
         * Get image embed color. Should be hex
         */
        public String getEmbedColor() {
            return imageEmbedColor;
        }

        /**
         * Get discord embed's message field text
         */
        public String getEmbedMessage() {
            return imageEmbedMessage;
        }

        /**
         * Get the domain name
         */
        public String getDomain() {
            return imageUrl;
        }

        /**
         * Get the subdomain name
         */
        public String getSubDomain() {
            return urlPrefix;
        }

        /**
         * Get total size you can upload
         */
        public Integer getUploadTotalSize() {
            return uploadTotalSize;
        }

    }

    public static class SetUserInfoQuery extends GatoQuery<SetUserInfoResponse> {
        private static class SetUserInfoBody {
            @SerializedName("domainprefix")
            @Expose
            private String domainprefix;
            @SerializedName("domain")
            @Expose
            private String domain;
            @SerializedName("colorcode")
            @Expose
            private String colorcode;
            @SerializedName("iscustomembed")
            @Expose
            private Boolean iscustomembed;
            @SerializedName("embedmessage")
            @Expose
            private String embedmessage;
            @SerializedName("authormessage")
            @Expose
            private String authormessage;
            @SerializedName("sitename")
            @Expose
            private String sitename;
            @SerializedName("embedtitle")
            @Expose
            private String embedtitle;
            @SerializedName("fakeurl")
            @Expose
            private String fakeurl;

            /**
             * No args constructor for use in serialization
             *
             */
            public SetUserInfoBody() {

            }

            /**
             * Create new SetUserInfoBody
             * @param embedtitle Embed title
             * @param authormessage Author text field
             * @param domain Domain
             * @param iscustomembed True if custom embed enabled
             * @param domainprefix Subdomain
             * @param sitename Site name in discord's embed field
             * @param colorcode Color code
             * @param embedmessage Discord embed's Message
             * @param fakeurl Fake URL that will glitch discord client
             */
            public SetUserInfoBody(String domainprefix, String domain, String colorcode, Boolean iscustomembed, String embedmessage, String authormessage, String sitename, String embedtitle, String fakeurl) {
                super();
                this.domainprefix = domainprefix;
                this.domain = domain;
                this.colorcode = colorcode;
                this.iscustomembed = iscustomembed;
                this.embedmessage = embedmessage;
                this.authormessage = authormessage;
                this.sitename = sitename;
                this.embedtitle = embedtitle;
                this.fakeurl = fakeurl;
            }
        }

        private final SetUserInfoBody body;
        private SetUserInfoQuery(String domainprefix, String domain, String colorcode, Boolean iscustomembed, String embedmessage, String authormessage, String sitename, String embedtitle, String fakeurl) {
            super(SetUserInfoResponse.class);
            body = new SetUserInfoBody(domainprefix, domain, colorcode, iscustomembed, embedmessage, authormessage, sitename, embedtitle, fakeurl);
        }

        @Override
        protected Request getHttpRequest(GatoHostAPI api) {
            return new Request.Builder()
                    .url(api.baseUrl + "/api/" + apiVersion + "/saveimagechanges")
                    .header("key", api.key)
                    .post(RequestBody.create(api.gson.toJson(body), JSON))
                    .build();
        }

        public static class Builder {
            private String domainprefix;
            private String domain;
            private String colorcode;
            private Boolean iscustomembed;
            private String embedmessage;
            private String authormessage;
            private String sitename;
            private String embedtitle;
            private String fakeurl;

            private GetUserInfoResponse userInfo;

            /**
             * Constructor of builder class
             * @param api For getting user info
             */
            public Builder(GatoHostAPI api) {
                GetUserInfoQuery query = new GetUserInfoQuery();
                userInfo = api.execute(query);
                domainprefix = userInfo.getSubDomain();
                domain = userInfo.getDomain();
                colorcode = userInfo.getEmbedColor();
                iscustomembed = userInfo.isDiscordEmbedEnabled();
                embedmessage = userInfo.getEmbedMessage();
                authormessage = userInfo.getEmbedAuthorMessage();
                sitename = userInfo.getEmbedSiteName();
                embedtitle = userInfo.getEmbedTitle();
                fakeurl = userInfo.getFakeUrl();
            }


            public Builder subdomain(String subdomain) {
                this.domainprefix = subdomain;
                return this;
            }
            public Builder domain(String domain) {
                this.domain = domain;
                return this;
            }
            public Builder colorcode(String colorcode) {
                this.colorcode = colorcode;
                return this;
            }
            public Builder enableCustomEmbed(Boolean enabled) {
                this.iscustomembed = enabled;
                return this;
            }
            public Builder embedMessage(String message) {
                this.embedmessage = message;
                return this;
            }
            public Builder authorText(String authorText) {
                this.authormessage = authorText;
                return this;
            }
            public Builder siteName(String siteName) {
                this.sitename = siteName;
                return this;
            }
            public Builder embedTitle(String embedTitle) {
                this.embedtitle = embedTitle;
                return this;
            }
            public Builder fakeUrl(String fakeurl) {
                this.fakeurl = fakeurl;
                return this;
            }

            public SetUserInfoQuery build() {
                return new SetUserInfoQuery(domainprefix, domain, colorcode, iscustomembed, embedmessage, authormessage, sitename, embedtitle, fakeurl);
            }
        }
    }
    public static class SetUserInfoResponse extends GatoResponse {

    }

    public static class UploadImageQuery extends GatoQuery<UploadImageResponse> {

        private final File imageFile;
        public UploadImageQuery(File imageFile) {
            super(UploadImageResponse.class);
            this.imageFile = imageFile;
        }

        @Override
        public Request getHttpRequest(GatoHostAPI api) {
            if (!imageFile.exists() || !imageFile.isFile()) {
                try {
                    throw new IOException("File could not be found");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            RequestBody requestBody = null;
            try {
                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("key", api.key)
                        .addFormDataPart("img", imageFile.getName(), RequestBody.create(imageFile, MediaType.get(Files.probeContentType(imageFile.toPath()))))
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Request request = new Request.Builder()
                    .url(api.baseUrl + "/img/upload/")
                    .post(requestBody)
                    .build();
            return request;
        }
    }
    public static class UploadImageResponse extends GatoResponse {
        @SerializedName("url")
        @Expose
        private String url;

        public URL getUrl() {
            try {
                return new URL(url);
            } catch (Exception e) {
                try {
                    if (url.contains("|")) {
                        int i = url.lastIndexOf("https://");
                        if (i == -1) {
                            i = url.lastIndexOf("http://");
                        }
                        return new URL(url.substring(i));
                    }
                    throw e;
                } catch (Exception ex) {
                    try {
                        throw new IOException("Could not parse data. Is key valid?", e);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

}
