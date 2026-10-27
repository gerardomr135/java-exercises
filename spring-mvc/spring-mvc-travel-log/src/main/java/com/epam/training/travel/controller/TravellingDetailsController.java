package com.epam.training.travel.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epam.training.travel.converter.TravellingConverter;
import com.epam.training.travel.data.TravellingRepository;
import com.epam.training.travel.model.TravellingModel;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/travelling-details")
public class TravellingDetailsController {
    private final TravellingRepository travellingRepository;
    private final TravellingConverter travellingConverter;

    public TravellingDetailsController(TravellingRepository travellingRepository,
            TravellingConverter travellingConverter) {
        this.travellingRepository = travellingRepository;
        this.travellingConverter = travellingConverter;
    }

    @GetMapping
    public String getTravellingDetails() {
        return "travelling-details";
    }

    @PostMapping
    public String addReview(@RequestParam("travellingId") long travellingId,
            @Valid @ModelAttribute("travelling") TravellingModel travelling,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "travelling-details";
        }

        travellingRepository.addReview(travellingId, travelling.getReview());
        redirectAttributes.addFlashAttribute("successMessage", "Review has been updated successfully.");
        return "redirect:/travelling-details?travellingId=" + travellingId;
    }

    @ModelAttribute("travelling")
    public TravellingModel travelling(@RequestParam("travellingId") long travellingId) {
        return travellingConverter.convert(travellingRepository.getById(travellingId).get());
    }
}
