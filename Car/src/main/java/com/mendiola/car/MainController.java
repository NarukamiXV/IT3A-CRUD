package com.mendiola.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller // This means that this class is a Controller
@RequestMapping(path="/cars") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CarRepository carRepository;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody String addNewCar (
            @RequestParam String make,
            @RequestParam String model,
            @RequestParam String body,
            @RequestParam String transmission ) {

        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Car c = new Car();

        c.setMake(make);
        c.setModel(model);
        c.setBody(body);
        c.setTransmission(transmission);

        String response;
        try {
            Car sc = carRepository.save(c);
            response = "Success adding car with id: " + sc.getId();
        } catch (Exception e) {
            response =  "Error " + e.getMessage();
        }
        return response;
    }

    @GetMapping
    public @ResponseBody Iterable<Car> getAllCars() {
        // This returns a JSON or XML with the users
        return carRepository.findAll();
    }

    @GetMapping(path="/{id}") // Map ONLY POST Requests
    public @ResponseBody Optional<Car> getCar (@PathVariable Integer id) {
        return carRepository.findById(id);
    }

    @DeleteMapping(path="/{id}") // Map ONLY POST Requests
    public @ResponseBody String delete (@PathVariable Integer id) {

        String response;
        try {
            carRepository.deleteById(id);
            response = "Deleted";
        } catch (Exception e) {
            response = "Error " + e.getMessage();
        }
        return response;
    }
}