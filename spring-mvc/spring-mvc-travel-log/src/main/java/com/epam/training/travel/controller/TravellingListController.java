package com.epam.training.travel.controller;

import java.util.List;

import javax.validation.Valid;

import com.epam.training.travel.domain.Travelling;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epam.training.travel.converter.TravellingConverter;
import com.epam.training.travel.data.TravellingRepository;
import com.epam.training.travel.model.TravellingModel;

@Controller
public class TravellingListController {
    private final TravellingRepository travellingRepository;
    private final TravellingConverter travellingConverter;

    public TravellingListController(TravellingRepository travellingRepository,
            TravellingConverter travellingConverter) {
        this.travellingRepository = travellingRepository;
        this.travellingConverter = travellingConverter;
    }

    @GetMapping("/travelling-list")
    public String getTravellingList() {
        return "travelling-list";
    }

    @PostMapping("/create-travelling")
    public ModelAndView createTravelling(@Valid @ModelAttribute("newTravelling") TravellingModel travelling,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("travelling-list");
        }

        redirectAttributes.addFlashAttribute("successMessage", "Travelling has been created successfully.");

        travellingRepository.create(travellingConverter.convert(travelling));

        return new ModelAndView("redirect:/travelling-list");
    }

    @ModelAttribute("travellingList")
    public List<TravellingModel> travellingList() {
        return travellingConverter.convert(travellingRepository.findAll());
    }

    @ModelAttribute("newTravelling")
    public TravellingModel newTravelling() {
        return new TravellingModel();
    }
}
