package ru;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Component
public class FileUpload {
    public String upload(MultipartFile multipartFile) {
        try {
            multipartFile.transferTo(Paths.get((("C:\\Users\\andre\\site\\covers\\1.docx"))));
           return   downoladFile(postToServer());

        } catch (IOException e) {
            System.out.println("Ошибка");
            e.printStackTrace();
        }
        return String.valueOf(new NullPointerException());
    }

    private String postToServer() {
        String bodyString = "{\n" +
                "    \"async\" : false,\n" +
                "    \"key\": \"Khrz6zTPdfd7\",\n" +
                "     \"filetype\": \"docx\",\n" +
                "     \"outputtype\": \"pdf\",\n" +
                "     \"title\": \"Example Document Title.docx\",\n" +
                "    \"url\": \"http://localhost:8080/covers/1.docx\"\n" +
                "}";
        System.out.println("Body string = " + bodyString);
        URL url = null;
        java.net.HttpURLConnection connection = null;
        InputStream response = null;
        String jsonString = null;
        byte[] bodyByte = bodyString.getBytes(StandardCharsets.UTF_8);
        try {
            url = new URL("http://localhost:0080/ConvertService.ashx");
            connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setFixedLengthStreamingMode(bodyByte.length);
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(120000);
            connection.connect();
            try (OutputStream os = connection.getOutputStream()) {
                os.write(bodyByte);
                os.flush();
            }
            //response.transferTo(new FileOutputStream("C:\\Users\\andre\\site\\covers\\2.pdf"));
            System.out.println("Response code " + connection.getResponseCode());
            response = connection.getInputStream();
            jsonString = convertStreamToString(response);
            System.out.println("JSON String = " + jsonString);
        } finally {
            connection.disconnect();
            return jsonString;
        }
    }

    public String downoladFile(String jsonString) {
        JSONObject jsonpObject = null;
        String url = "";
        try {
            jsonpObject = new JSONObject(jsonString);
             url = jsonpObject.getString("fileUrl");
            URL website = new URL((String) jsonpObject.get("fileUrl"));
             ReadableByteChannel rbc = Channels.newChannel(website.openStream());
             FileOutputStream fos = new FileOutputStream("C:\\Users\\andre\\site\\covers\\2.pdf");
             fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            if (jsonpObject == null) throw new NullPointerException();
            return url;

    }

    public String convertStreamToString(InputStream stream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();

        while (line != null) {
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }

        String result = stringBuilder.toString();

        return result;
    }
}