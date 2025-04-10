package com.ecommerce.platform.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.cloud.config.server.git.uri=https://github.com/ashishrajput35/ecommerce-config-repo.git",
		"spring.cloud.config.server.git.default-label=dev"
})
class EcommerceConfigServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
