package com.tracker.corona.coronatracker.controller;

import com.tracker.corona.coronatracker.model.LocationStatistics;
import com.tracker.corona.coronatracker.service.CoronaTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author mkarki
 */
@Controller
public class CoronaTrackerController {

    private final CoronaTrackerService coronaTrackerService;

    @Autowired
    public CoronaTrackerController(CoronaTrackerService coronaTrackerService) {
        this.coronaTrackerService = coronaTrackerService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStatistics> locationStatistics = coronaTrackerService.getLocationStatistics();
        int reportedCases = locationStatistics.stream()
                .mapToInt(stat -> stat.getLatestTotalCases())
                .sum();
        int newCases = locationStatistics.stream()
                .mapToInt(stat -> stat.getDifferenceFromPrevDay())
                .sum();
        model.addAttribute("locationStats", locationStatistics);
        model.addAttribute("reportedCases", reportedCases);
        model.addAttribute("newCases", newCases);

        return "home";
    }

}
