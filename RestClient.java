package org.yLobanov;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestClient {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8081/measurements/chart";
        String response = restTemplate.getForObject(url, String.class);

        List<Double> temperatures = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        Pattern pattern = Pattern.compile("-?\\d\\d?.\\d");
        Matcher matcher = pattern.matcher(Objects.requireNonNull(response));
        int counter = 1;
        while (matcher.find() && counter < 500) {
            temperatures.add(Double.parseDouble(matcher.group()));
            System.out.println((counter - 1) + " check is successful " + temperatures.get(counter - 1));
            indexes.add(counter++);
        }

        XYChart chart = QuickChart.getChart("1000 Temperatures", "", "Temperature", "Sensor",
                indexes, temperatures);

        SwingWrapper<XYChart> wrapper = new SwingWrapper<>(chart);
        wrapper.displayChart();
    }
}