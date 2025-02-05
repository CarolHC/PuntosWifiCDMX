package com.wifi.puntoswifi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class PuntosWifi
{

	public static void main(String[] args)
	{
		SpringApplication.run(PuntosWifi.class, args);
	}

	@Bean
	public OpenAPI springShopOpenAPI()
	{
		return new OpenAPI().info(new Info().title("Puntos WIFI API")
				.contact(new Contact().email("chernandezcansino@gmail.com").name("Carolina"))
				.description("Pipeline de análisis de datos utilizando los datos abiertos de la Ciudad de\n" + 
						"México\n" + 
						"correspondientes a las Puntos de acceso WiFi en la Ciudad de México para que pueda\n" + 
						"ser\n" + 
						"consultado mediante un API Rest.\n" + 
						"")
				.version("v1.0.3").license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0")))
				.externalDocs(new ExternalDocumentation().description("Readme")
						.url("https://github.com/CarolHC/PuntosWifiCDMX#readme"));
	}

}
