package pl.damian.beautyglow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.damian.beautyglow.entity.Treatment;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.entity.UsersTreatments;
import pl.damian.beautyglow.service.EmailService;
import pl.damian.beautyglow.service.TreatmentService;
import pl.damian.beautyglow.service.UsersTreatmentsService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/treatments")
public class TreatmentsController {
    @Autowired
    TreatmentService treatmentService;
    @Autowired
    UsersTreatmentsService usersTreatmentsService;
    @Autowired
    EmailService emailService;

    @GetMapping("/list")
    public String showAllTreatments(Model theModel) {
        List<Treatment> treatmentList = treatmentService.getTreatments();
        theModel.addAttribute("treatmentsList", treatmentList);
        return "treatments-list";
    }

    @GetMapping("/add")
    public String addTreatment(Model theModel) {
        Treatment treatment = new Treatment();
        theModel.addAttribute("treatment", treatment);
        return "treatment-form";
    }

    @PostMapping("/edit")
    public String editTreatment(@RequestParam("treatmentId") int id, Model theModel) {
        Treatment treatment = treatmentService.getTreatment(id);
        theModel.addAttribute("treatment", treatment);
        return "treatment-form";
    }

    @PostMapping("/save")
    public String saveTreatment(@Valid @ModelAttribute("treatment") Treatment treatment, BindingResult theBindingResult, Model theModel) {
        if (theBindingResult.hasErrors()) {
            theModel.addAttribute("treatmentError", "Nieprawidłowe dane");
            return "treatment-form";
        }
        if (treatment.getDuration() % 15 != 0) {
            theModel.addAttribute("treatmentError", "Czas trwania zabiegu musi być podzielny przez 15");
            return "treatment-form";
        }
        treatmentService.saveTreatment(treatment);
        return "redirect:/treatments/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("treatmentId") int id) {
        List<UsersTreatments> usersTreatmentsList = usersTreatmentsService.getUsersTreatments();
        for (UsersTreatments usersTreatments : usersTreatmentsList) {
            if (usersTreatments.getTreatment().getId() == id) {
                int tempId = usersTreatments.getId();
                emailService.sendSimpleMessage(usersTreatments.getUser().getEmail(), "Anulowanie wizyty",
                        emailService.textCancelVisitMessage(usersTreatments.getUser().getFirstName(),
                                usersTreatments.getUser().getEmail(), usersTreatments));
                usersTreatmentsService.deleteUsersTreatments(tempId);
            }
        }
        treatmentService.deleteTreatment(id);
        return "redirect:/treatments/list";

    }

}










