import club.novaclient.gatohost.GatoHostAPI;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TestUploadImage {

    public static void main(String[] args) throws IOException {
        GatoHostAPI api = new GatoHostAPI("KEY_IS_IN_YOUR_COOKIE");
        System.out.println("===== User Info =====");
        System.out.println("UserName: " + api.getUserName());
        System.out.println("UID: " + api.getId());
        System.out.println("Key: " + api.getKey());
        System.out.println("Resetting Key...");
        api.resetKey();
        System.out.println("New Key: " + api.getKey());
        System.out.println("Registration Date: " + api.getRegistrationDate());
        System.out.println("Staff: " + api.isStaff());
        System.out.println("Banned: " + api.isBanned());
        System.out.println("");
        System.out.println("Discord Embed Information Test:");
        System.out.println("Domain: " + api.getDomain().getName());
        System.out.println("SubDomain: " + api.getSubDomain());
        String subDomain = new Random().nextInt(100000) + "";
        api.setSubDomain(subDomain);
        System.out.println("New SubDomain: " + api.getSubDomain());
        System.out.println("Color Code(int): 0x" + api.getColorCode());
        api.setColorCode(new Random().nextInt(10000));
        System.out.println("New Color Code: 0x" + api.getColorCode());
        System.out.println("Message: " + api.getMessage());
        api.setMessage("" + new Random().nextInt(100000));
        System.out.println("New Message: " + api.getMessage());
        System.out.println("Author Message: " + api.getAuthorMessage());
        api.setAuthorMessage("" + new Random().nextInt(100000));
        System.out.println("New Author Message: " + api.getAuthorMessage());
        System.out.println("Embed Site Text: " + api.getEmbedSiteName());
        api.setEmbedSiteName("" + new Random().nextInt(100000));
        System.out.println("New Embed Site Text: " + api.getEmbedSiteName());
        System.out.println("Embed Title Text: " + api.getEmbedTitle());
        api.setEmbedTitle("" + new Random().nextInt(100000));
        System.out.println("New Embed Title Text: " + api.getEmbedTitle());
        System.out.println("Fake Url: " + api.getFakeUrl());
        api.setFakeUrl("" + new Random().nextInt(100000));
        System.out.println("New Fake Url: " + api.getFakeUrl());
        System.out.println("");
        System.out.println("Trying to upload image.jpeg...");
        System.out.println("Uploaded! URL: " + api.uploadAndGetUrl(new File("image.jpeg")));
    }

}
