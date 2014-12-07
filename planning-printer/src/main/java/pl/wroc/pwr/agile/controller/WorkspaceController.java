package pl.wroc.pwr.agile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkspaceController {

    @RequestMapping("/workspace")
    public String showWorkspace() {
        return "user-workspace";
    }
    
}
