package day.of.Programmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FahitosApplication {

    public static void main(String[] args) {
        String apiUrl = "http://ya.praktikum.fvds.ru:8080/dev-day/task/2";
        String authToken = "f538ec47-514d-4051-8be9-0e91bf174b6e";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("AUTH_TOKEN", authToken);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder encoded = new StringBuilder();
                StringBuilder offset = new StringBuilder();
                StringBuilder numb = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    if (line.contains("encoded") && line.contains("offset")) {
                        encoded.append(line);
                        encoded = new StringBuilder(encoded.substring(57));
                        for (int i = 0; i < encoded.length(); i++) {
                            if (encoded.charAt(i) != '&') {
                                offset.append(encoded.charAt(i));
                            } else {
                                break;
                            }
                        }
                        for (int i = 0; i < encoded.length(); i++) {
                            char ch = encoded.charAt(i);
                            if (Character.isDigit(ch)) {
                                numb.append(ch);
                            }
                        }
                    }
                }
                Integer num = Integer.parseInt(numb.toString());
                reader.close();

                System.out.println(offset);
                System.out.println(num);

                String decodedText = decode(offset.toString(), num);

                sendPostRequest(apiUrl, authToken, decodedText);

            } else {
                System.out.println("Запрос не удался. Код ответа: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendPostRequest(String apiUrl, String authToken, String decodedText) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("AUTH_TOKEN", authToken);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            byte[] input = String.format("{\"decoded\": \"%s\"}", decodedText).getBytes("utf-8");
            os.write(input, 0, input.length);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                System.out.println("Ответ сервера:");
                System.out.println(response.toString());
            } else {
                System.out.println("POST-запрос не удался. Код ответа: " + responseCode);
            }

            os.close();
        } catch (IOException e) {
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
