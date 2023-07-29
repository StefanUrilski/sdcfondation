package bg.sdcf.web.controller;

import bg.sdcf.domain.model.UserRegisterForm;
import bg.sdcf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@PreAuthorize("isAnonymous()")
@RequestMapping("/users")
public class UserController extends BaseController {

   private final UserService userService;

   @Autowired
   public UserController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping(value = "/login")
   public ModelAndView login() {
      return view("users/login-user");
   }


   @GetMapping("/register")
   public ModelAndView register(@ModelAttribute("model") UserRegisterForm userRegisterForm) {
      return view("users/register-user", "model", userRegisterForm);
   }

   @PostMapping("/register")
   public ModelAndView registerConfirm(@Valid @ModelAttribute("model") UserRegisterForm registerForm,
         BindingResult bindingResult) {
      if (bindingResult.hasErrors()
            || !(registerForm.getPassword().equals(registerForm.getConfirmPassword()))) {
         registerForm.setPassword(null);
         registerForm.setConfirmPassword(null);
         return view("users/register-user", "model", registerForm);
      }
      this.userService.registerUser(registerForm);
      return redirect("/users/success");
   }

   @GetMapping("/success")
   public ModelAndView registerSuccessPage() {
      return view("users/register-user-success");
   }

}