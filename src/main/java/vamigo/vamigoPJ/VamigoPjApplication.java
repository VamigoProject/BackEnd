package vamigo.vamigoPJ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import vamigo.vamigoPJ.util.ImageToBase64;

//EnalbelJpaAuditing : 날짜 자동 입력을 위한 어노테이션


@SpringBootApplication
@EnableJpaAuditing
public class VamigoPjApplication {

	public static void main(String[] args) {


		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		SpringApplication.run(VamigoPjApplication.class, args);

	}

}
