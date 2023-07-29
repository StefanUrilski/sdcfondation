package bg.sdcf.domain.model;

import lombok.Data;

@Data
public class UserRegisterForm {

   private String email;
   private String password;
   private String confirmPassword;

}
