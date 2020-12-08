package com.chryl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChrPwdApplicationTests {

    @Value("${spring.datasource.password}")
    private String url;

    @Test
    public void testJasypt() {
        System.out.println(url);
    }

    @Test
    void contextLoads() {
    }

}
