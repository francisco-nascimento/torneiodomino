package br.ifpe.web3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class DominojavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DominojavaApplication.class, args);
	}

}
