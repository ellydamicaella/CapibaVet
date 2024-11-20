package br.com.projeto.meupet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.start.meupet.MeupetApplication;

@SpringBootTest(classes = MeupetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MeupetApplicationTests {

	@Test
	void contextLoads() {
	}

}
