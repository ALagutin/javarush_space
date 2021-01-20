package com.space.service;

import com.space.model.Ship;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Validator {
    public static boolean checkShip(Ship ship) {
        return checkRequered(ship) && validName(ship) && validPlanet(ship) && validProdDate(ship) && validSpeed(ship) && validCrewSize(ship);
    }

    private static boolean checkRequered(Ship ship) {
        return !(ship.getName() == null || ship.getPlanet() == null || ship.getShipType() == null || ship.getProdDate() == null || ship.getSpeed() == null || ship.getCrewSize() == null);

    }

    private static boolean validName(Ship ship) {
        String name = ship.getName();
        return !(name.isEmpty() || name.length() > 50);
    }

    private static boolean validPlanet(Ship ship) {
        String planet = ship.getPlanet();
        return !(planet.isEmpty() || planet.length() > 50);
    }

    private static boolean validProdDate(Ship ship) {
        Date prodDate = ship.getProdDate();

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(ship.getProdDate());

        int year = calendar.get(Calendar.YEAR);

        return !(prodDate.getTime() < 0 || year < 2800 || year > 3019);
    }

    private static boolean validSpeed(Ship ship) {
        double speed = ((double)Math.round(ship.getSpeed()*100))/100;
        return !(speed < 0.01 || speed > 0.99);
    }

    private static boolean validCrewSize(Ship ship) {
        int crewSize = ship.getCrewSize();
        return !(crewSize < 1 || crewSize > 9999);
    }
}
