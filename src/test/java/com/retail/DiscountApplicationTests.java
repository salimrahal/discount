package com.retail;

import static org.springframework.test.util.AssertionErrors.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscountApplicationTests {

	@Test
	public void contextLoads() {
		DiscountApplication.main(new String[] {});
        assertTrue("Application started successfully", true);
	}

}
