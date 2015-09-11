package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(13,10), 2000).stream().forEach(u -> System.out.println(u.toString()));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        boolean exceeded;
        Map<LocalDate,Boolean> dates = new HashMap<>();

        List<UserMealWithExceed>userMealWithExceeds = new ArrayList<>();
        Set<LocalDate> localDates = new HashSet<>();

        for (UserMeal uM : mealList) {
            localDates.add(uM.getDateTime().toLocalDate());
        }

        for (LocalDate date : localDates) {
            int calories = 0;
            for (UserMeal uM : mealList) {
                if (uM.getDateTime().toLocalDate().equals(date)) {
                    calories += uM.getCalories();
                }
            }

            exceeded = calories > caloriesPerDay;

            dates.put(date,exceeded);
        }

        for (UserMeal uM : mealList) {
            if (uM.getDateTime().toLocalTime().isAfter(startTime) && uM.getDateTime().toLocalTime().isBefore(endTime)) {
                userMealWithExceeds.add(new UserMealWithExceed(uM.getDateTime(),
                        uM.getDescription(),
                        uM.getCalories(),
                        dates.get(uM.getDateTime().toLocalDate())));
            }
        }

        return userMealWithExceeds;
    }
}
