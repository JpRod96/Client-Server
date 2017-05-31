package bo.ucbcba.taller.proyecto.proyecto.controllers;

import bo.ucbcba.taller.proyecto.proyecto.services.BandService;
import bo.ucbcba.taller.proyecto.proyecto.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Date;

/**
 * Created by CORE i7 on 24/05/2017.
 */
@Controller
public class StepController {
    private StepService stepService;


    @Autowired
    public void setStepService(StepService stepService) {
        this.stepService = stepService;
    }


    @RequestMapping(value = "/admin/steps", method = RequestMethod.GET)
    public String list(Model model
            /*, @PathVariable("dateStart") Date dateStart, @PathVariable("dateEnd") Date dateEnd*/) {

        model.addAttribute("steps", stepService.listAllSteps());
        /*model.addAttribute("dateStart", dateStart);
        model.addAttribute("dateFin", dateEnd);*/
        return "steps";
    }


}
