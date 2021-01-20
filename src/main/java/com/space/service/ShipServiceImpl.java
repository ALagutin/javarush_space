package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hibernate.criterion.Example.create;


@Service
public class ShipServiceImpl implements ShipService {
    @Autowired
    private ShipRepository shipRepository;

    @Override
    public List<Ship> getShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                               Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {

        Stream<Ship> stream = shipRepository.findAll().stream();

        List<Ship> filteredList = stream.filter(x -> name == null || x.getName().contains(name))
                .filter(x -> planet == null || x.getPlanet().contains(planet))
                .filter(x -> shipType == null || x.getShipType() == shipType)
                .filter(x -> after == null || x.getProdDate().getTime() >= after)
                .filter(x -> before == null || x.getProdDate().getTime() <= before)
                .filter(x -> isUsed == null || x.getUsed() == isUsed)
                .filter(x -> minSpeed == null || x.getSpeed() >= minSpeed)
                .filter(x -> maxSpeed == null || x.getSpeed() <= maxSpeed)
                .filter(x -> minCrewSize == null || x.getCrewSize() >= minCrewSize)
                .filter(x -> maxCrewSize == null || x.getCrewSize() <= maxCrewSize)
                .filter(x -> minRating == null || x.getRating() >= minRating)
                .filter(x -> maxRating == null || x.getRating() <= maxRating)
                .collect(Collectors.toList());

        return filteredList;
    }
//
//        @Override
//    public Integer shipsCount() {
//        return 2;
//    }

    @Override
    public Ship createShip(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public Ship getShip(Long id) {
        Optional<Ship> optional = shipRepository.findById(id);
        if (optional.isPresent())
            return optional.get();
        else
            return null;
    }

    @Override
    public Ship updateShip(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }

    public List<Ship> getShipsByPage(List<Ship> allShips, ShipOrder order, Integer pageNumber, Integer pageSize) {

        if (pageNumber == null) pageNumber = 0;
        if (pageSize == null) pageSize = 3;
        if (order == null) order = ShipOrder.ID;

        List<Ship> list = allShips.stream().sorted(getComparator(order)).skip(pageNumber * pageSize).limit(pageSize).collect(Collectors.toList());
        return list;
    }

    private Comparator<Ship> getComparator(ShipOrder order) {

        switch (order.getFieldName()) {
            case "speed":
                return Comparator.comparing(Ship::getSpeed);
            case "prodDate":
                return Comparator.comparing(Ship::getProdDate);
            case "rating":
                return Comparator.comparing(Ship::getRating);
            default:
                return Comparator.comparing(Ship::getId);
        }
    }

}
