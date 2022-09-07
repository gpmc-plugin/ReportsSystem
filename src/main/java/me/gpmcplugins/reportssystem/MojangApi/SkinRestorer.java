package me.gpmcplugins.reportssystem.MojangApi;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SkinRestorer {
    private static final Map<String,String> SkinCashe = new HashMap<>();
    public static String getTextureHash(String uuid){
        if(SkinCashe.containsKey(uuid))
            return SkinCashe.get(uuid);
        URL url;
        try {
            url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/"+uuid);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            if(status!=200){
                SkinCashe.put(uuid,null);
                return null;
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            JSONObject json = new JSONObject(content.toString());
            JSONObject textures = json.getJSONArray("properties").getJSONObject(0);
            String textureJsonString = textures.getString("value");
            String texturesProperties = new String( Base64.getDecoder().decode(textureJsonString));
            JSONObject texturesJsonObject = new JSONObject(texturesProperties);
            String textureUrl = texturesJsonObject.getJSONObject("textures").getJSONObject("SKIN").getString("url");
            String textureHash = textureUrl.split("/")[4];
            SkinCashe.put(uuid,textureHash);
            return textureHash;

        } catch (Exception ignored) {
        }
        SkinCashe.put(uuid,null);
        return null;
    }
}
