package auth.java.authentication_system;

import auth.java.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(value = UserService.class)
@ComponentScan(basePackages = "auth.java")
class AuthenticationSystemApplicationTests {

	@Test
	void contextLoads() {
	}

}
