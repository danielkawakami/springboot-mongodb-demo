package com.example.springbootmongodbdemo.controller;

import com.example.springbootmongodbdemo.model.Hotel;
import com.example.springbootmongodbdemo.model.QHotel;
import com.example.springbootmongodbdemo.repository.HotelRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    private HotelRepository repository;

    public HotelController(HotelRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Hotel> getAllHotels() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Hotel> getHotel(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping
    public Hotel insertHotel(@RequestBody Hotel hotel) {
        Hotel newHotel = repository.insert(hotel);
        return newHotel;
    }

    @PutMapping
    public void updateHotel(@RequestBody Hotel hotel) {
        repository.save(hotel);
    }

    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable("id") String id) {
        repository.deleteById(id);
    }

    @GetMapping("/price/{maxPrice}")
    public List<Hotel> getByPricePerNight(@PathVariable("maxPrice") int maxPrice) {
        return repository.findByPricePerNightLessThan(maxPrice);
    }

    @GetMapping("/address/{city}")
    public List<Hotel> getByCity(@PathVariable("city")String city) {
        List<Hotel> hotels = repository.findByCity(city);
        return hotels;
    }

    @GetMapping("/country/{country}")
    public List<Hotel> getByCountry(@PathVariable("country") String country) {
        // create a query class (QHotel)
        QHotel qhotel = new QHotel("hotel");

        BooleanExpression filterByCountry = qhotel.address.country.eq(country);

        List<Hotel> hotels = (List<Hotel>)this.repository.findAll(filterByCountry);
        return hotels;
    }

    @GetMapping("/recommended")
    public List<Hotel> getRecommended() {
        final int maxPrice = 100;
        final int minRating = 7;

        QHotel qHotel = new QHotel("hotel");

        BooleanExpression filterByMaxPrice = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByMinRating = qHotel.reviews.any().rating.gt(minRating);

        List<Hotel> hotels = (List<Hotel>) repository.findAll(filterByMaxPrice.and(filterByMinRating));
        return hotels;
    }
}
