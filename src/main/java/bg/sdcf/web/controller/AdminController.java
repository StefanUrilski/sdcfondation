package bg.sdcf.web.controller;

import bg.sdcf.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController extends BaseController {

   private final UserService userService;

   public AdminController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("/all")
   public ModelAndView allProfiles() {
      return view("users/all-profiles-user", "profiles",
            this.userService.getAllRegisteredUsers());
   }

   @GetMapping("/status/{id}")
   public ModelAndView changeStatus(@PathVariable(name = "id") String id) {
      this.userService.changeUserStatus(id);

      return redirect("/users/all");
   }

}
