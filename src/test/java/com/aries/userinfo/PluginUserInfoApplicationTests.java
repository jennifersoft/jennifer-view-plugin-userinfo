package com.aries.userinfo;

import aries.util.Assertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PluginUserInfoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void checkURL() throws IOException {
        URL obj = new URL("https://dev.jennifersoft.com/plugin/userinfo?token=zwRhzB97IAA&domain_id=7908");
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while((line = in.readLine()) != null) {
            sb.append(line);
        }

        System.out.println("--------------------------");
        System.out.println(sb);
        System.out.println("--------------------------");

        Assertion.check(sb.length() > 0, "JSON 문자열을 가져오는지 확인");
    }
}
