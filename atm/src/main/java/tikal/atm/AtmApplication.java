package tikal.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.ortools.Loader;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AtmApplication {

	public static void main(String[] args) {
		Loader.loadNativeLibraries();
		SpringApplication.run(AtmApplication.class, args);
	}

}
