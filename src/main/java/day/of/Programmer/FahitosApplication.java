package day.of.Programmer;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class FahitosApplication {

    public static void main(String[] args) {
        String url = "http://ya.praktikum.fvds.ru:8080/dev-day/task/2";

        String encodedText = "BUPY U JYLZYWN JBJ WIXCHA XUS";
        int offset = 20;

        String decodedText = decode(encodedText, offset);

        String json = "{\"decoded\": \"" + decodedText + "\"}";

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            // Добавляем заголовок AUTH_TOKEN
            httpPost.setHeader("AUTH_TOKEN", "f538ec47-514d-4051-8be9-0e91bf174b6e");

            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            System.out.println("Ответ сервера:");
            System.out.println(responseBody);

            response.close();
            httpClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String decode(String encodedText, int offset) {
        StringBuilder decodedText = new StringBuilder();
        for (char c : encodedText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                decodedText.append((char) (((c - base - offset + 26) % 26) + base));
            } else {
                decodedText.append(c);
            }
        }
        return decodedText.toString();
    }
}
