package bo.ucbcba.taller.proyecto.proyecto.controllers;

import bo.ucbcba.taller.proyecto.proyecto.entities.Band;
import bo.ucbcba.taller.proyecto.proyecto.entities.Step;
import bo.ucbcba.taller.proyecto.proyecto.entities.User;
import bo.ucbcba.taller.proyecto.proyecto.services.BandService;
import bo.ucbcba.taller.proyecto.proyecto.services.SecurityService;
import bo.ucbcba.taller.proyecto.proyecto.services.StepService;
import bo.ucbcba.taller.proyecto.proyecto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Juan on 11/05/2017.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BandService bandService;

    @Autowired
    private StepService stepServices;

    @Autowired
    private SecurityService securityService;




    //@Autowired
    //private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationInit(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        ///userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(user);
        securityService.autologin(user.getUsername(), user.getPasswordConfirm());
        model.addAttribute("usuario", user);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
    @RequestMapping(value = {"/admin/"}, method = RequestMethod.GET)
    public String admin(Model model) {
        return "welcome";
    }
    @RequestMapping(value = {"/bienvenidos"}, method = RequestMethod.GET)
    public String welcome2(Model model) {
        return "welcome";
    }


    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userService.listAllUsers());
        return "users";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveUser(User user) {
        userService.save(user);
        return "redirect:/myProfile";
    }



    @RequestMapping(value="/myProfile", method = RequestMethod.GET)
    public ModelAndView viewProfile(Model model){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        modelAndView.addObject("userName",user.getUsername());
        modelAndView.addObject("weigth",user.getWeigth());
        modelAndView.addObject("heigth",user.getHeigth());
        String sexo;
        if (user.getGender()){
            sexo = "Male";
        }else{
            sexo = "Female";
        }
        modelAndView.addObject("gender",sexo);
        modelAndView.addObject("age",user.getAge());
        modelAndView.setViewName("userShow");
        return modelAndView;
    }

    @RequestMapping(value="/mySteps", method = RequestMethod.GET)
    public ModelAndView viewStats(Model model){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String idUser= String.valueOf(user.getId());
        Band band = bandService.getBandByUserId(idUser);
        String idBand = String.valueOf(band.getId());
        List<Step> sumaStepsUser=stepServices.listAllStepsOfAnBand(idBand);
        Integer aditionResult=0;
        for (int i =0 ; i<sumaStepsUser.size();i++)
        {
            aditionResult=aditionResult+sumaStepsUser.get(i).getQuantity();
        }
        modelAndView.addObject("cantSteps",aditionResult);
        modelAndView.setViewName("stepUserShow");
        return modelAndView;
    }

}
