package me.gpmcplugins.reportssystem.MojangApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BasicMojangAPI {
    private static final Map<String,String> UUIDCashe = new HashMap<>();
    public static String getUUIDFromName(String name){
        if(UUIDCashe.containsKey(name))
            return UUIDCashe.get(name);
        try {
            URL mojangUUIDApi = new URL("https://api.mojang.com/profiles/minecraft");
            HttpURLConnection con = (HttpURLConnection) mojangUUIDApi.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = String.format("[\"%s\"]",name);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                String sresponse = response.toString();
                sresponse = sresponse.split("\"id\":")[1];
                sresponse = sresponse.split("\"")[1];
                UUIDCashe.put(name,sresponse);
                return sresponse;
            }
        } catch (Exception ignored) {

        }
        UUIDCashe.put(name,null);
        return null;

    }
}
