import club.novaclient.gatohost.GatoHostAPI;

import java.io.File;
import java.io.IOException;

public class TestUploadImage {

    public static void main(String[] args) throws IOException {
        GatoHostAPI api = new GatoHostAPI("ENTER_YOUR_API_KEY_HERE");
        System.out.println(api.uploadAndGetUrl(new File("image.jpeg")));
    }

}
