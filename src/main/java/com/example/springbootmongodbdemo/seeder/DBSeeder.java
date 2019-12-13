package com.example.springbootmongodbdemo.seeder;

import com.example.springbootmongodbdemo.model.Address;
import com.example.springbootmongodbdemo.model.Hotel;
import com.example.springbootmongodbdemo.model.Review;
import com.example.springbootmongodbdemo.repository.HotelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DBSeeder implements CommandLineRunner {
    private HotelRepository repository;

    public DBSeeder(HotelRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Hotel marriot = new Hotel(
                "Marriot",
                130,
                new Address("Paris", "France"),
                Arrays.asList(
                        new Review("John", 8, false),
                        new Review("Mary", 7, true)
                )
        );

        Hotel ibis = new Hotel(
                "Ibis",
                90,
                new Address("Bucharest", "Romania"),
                Arrays.asList(
                        new Review("Teddy", 9, true)
                )
        );

        Hotel sofitel = new Hotel(
                "Sofitel",
                200,
                new Address("Rome", "Italy"),
                new ArrayList<>()
        );

        // drop all hotels
        this.repository.deleteAll();

        List<Hotel> hotels = Arrays.asList(marriot, ibis, sofitel);
        this.repository.saveAll(hotels);
    }
}
