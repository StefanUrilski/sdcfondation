package bg.sdcf.service;

import bg.sdcf.domain.model.UserRegisterForm;
import bg.sdcf.domain.model.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

   void registerUser(UserRegisterForm registerForm);

   List<UserViewModel> getAllRegisteredUsers();

   void changeUserStatus(String id);

}
