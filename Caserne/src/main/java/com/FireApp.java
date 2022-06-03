package src.main.java.com;


@SpringBootApplication
public class FireApp {
	public static void main(String[] args) {
		System.getProperties().put("server.port",3080);
		SpringApplication.run(FireApp.class,args);
	}
}
