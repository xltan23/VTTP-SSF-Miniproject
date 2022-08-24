package sg.edu.nus.iss.MiniProject1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/home")
public class IndexController {

    @PostMapping
    public String postUser(@RequestBody MultiValueMap<String,String> form, Model model) {
        String username = form.getFirst("name");
        model.addAttribute("displayName", username);
        return "home";
    } 
}
