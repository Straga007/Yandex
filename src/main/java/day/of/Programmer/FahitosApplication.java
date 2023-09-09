package day.of.Programmer;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class FahitosApplication {

    public static void main(String[] args) {
        //SpringApplication.run(FahitosApplication.class, args);
        String url = "http://ya.praktikum.fvds.ru:8080/dev-day/register";

		String mainAnswer = "42";

		String json = "{\"name\": \"Group 6(Day of Programmer)\", \"gitHubUrl\":\"https://github.com/Straga007/Yandex.git\", \"participants\": [{\"email\": \"straga07@yandex.ru\", \"cohort\": \"java_25\", \"firstName\": \"Georgiy\", \"lastName\":\"Shukov\"}]}";

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpPost httpPost = new HttpPost(url);

			httpPost.setHeader("MAIN_ANSWER", mainAnswer);

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

}
