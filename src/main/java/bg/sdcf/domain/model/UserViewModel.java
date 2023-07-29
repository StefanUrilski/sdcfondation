package bg.sdcf.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserViewModel {

   private String id;
   private String email;
   private String registeredOn;
   private boolean userActive;
   private boolean canBeDeactivated;

}
