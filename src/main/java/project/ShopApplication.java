package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {


        List<Integer> list3 = new ArrayList<>();
        list3.add(3);

        SpringApplication.run(ShopApplication.class, args);
    }

}
