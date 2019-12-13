package com.example.springbootmongodbdemo.controller;

import com.example.springbootmongodbdemo.model.Hotel;
import com.example.springbootmongodbdemo.repository.HotelRepository;
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
}
