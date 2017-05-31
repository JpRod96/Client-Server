package bo.ucbcba.taller.proyecto.proyecto.controllers;

import bo.ucbcba.taller.proyecto.proyecto.entities.Step;
import bo.ucbcba.taller.proyecto.proyecto.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CORE i7 on 27/05/2017.
 */
@Controller
public class StepSearchController {
    @Autowired
    private StepService stepService;


    @RequestMapping(value = "/admin/searchResults", method = RequestMethod.GET)
    public String listResults(@ModelAttribute Step step,Model model/*,@PathVariable("dateStart") Date dateStart,@PathVariable("dateEnd") Date dateEnd*/){


       String dateIni ="20170526";
        String dateFin ="20170528";/*al configurar dsde view string-int +1 de ahi int string*/


        model.addAttribute("steps", stepService.listAllSearchedSteps(dateIni,dateFin));

       /* model.addAttribute("dateStart", dateStart);
        model.addAttribute("dateStart", dateEnd);*/


        return "steps";
    }
}
