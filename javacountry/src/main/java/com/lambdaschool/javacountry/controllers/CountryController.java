package com.lambdaschool.javacountry.controllers;

import com.lambdaschool.javacountry.models.Country;
import com.lambdaschool.javacountry.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    // filter method and interface found in CheckCountry.java
    private List<Country> findCountries(List<Country> myList, CheckCountry tester)
        {
            List<Country> tempList = new ArrayList<>();

            for (Country c : myList)
            {
               if (tester.test(c))
               {
                   tempList.add(c);
               }
            }

            return tempList;
        }

    @Autowired
    CountryRepository countryrepos;
//  http://localhost:2021/names/all
    @GetMapping(value ="/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries()
        {
            List<Country> myList = new ArrayList<>();
            countryrepos.findAll().iterator().forEachRemaining(myList::add);
            return new ResponseEntity<>(myList, HttpStatus.OK);
        }
//  http://localhost:2021/names/start/u
    @GetMapping(value = "names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> ListCountryName(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        List<Country> rtnList = findCountries(myList, e-> e.getName().charAt(0) == letter);

        for (Country c : rtnList)
        {
          System.out.println(c);
        }

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }


//     http://localhost:2021/population/total

    @GetMapping(value ="/population/total", produces = {"application/json"})
    public ResponseEntity<?> listPopulationTotal()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        // adds all of the integers in the given field getPopulation() and returns total
        long total = 0;
        for (Country c : myList)
        {
            total += c.getPopulation();
        }

        // Tells server to send total 
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

//     http://localhost:2021/population/min

    @GetMapping(value ="/population/min", produces = {"application/json"})
    public ResponseEntity<?> listPopulationMin()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        // Sorts low to high
        myList.sort((e1, e2) -> (int)(e1.getPopulation()- (e2.getPopulation())))

        //MyList.get(0) returns first object based on sort (low)
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }
//     http://localhost:2021/population/max

    @GetMapping(value ="/population/max", produces = {"application/json"})
    public ResponseEntity<?> listPopulationMax()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        // Sorts High to Low
        myList.sort((e1, e2) -> (int)(e2.getPopulation()- (e1.getPopulation())));

        //MyList.get(0) returns first item in list sorted High to Low (so High)
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }
}








