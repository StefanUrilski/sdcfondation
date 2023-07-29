package bg.sdcf.service;

import bg.sdcf.repository.RoleRepository;
import bg.sdcf.domain.entities.User;
import bg.sdcf.domain.entities.UserRole;
import bg.sdcf.domain.model.UserRegisterForm;
import bg.sdcf.domain.model.UserViewModel;
import bg.sdcf.errors.UserRegisterFailureException;
import bg.sdcf.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

   private final ModelMapper modelMapper;
   private final UserRepository userRepository;
   private final RoleRepository roleRepository;
   private final BCryptPasswordEncoder bCryptPasswordEncoder;

   private static final String NON_EXISTING_ROLE = "Role does not exist.";
   private static final String WRONG_NON_EXISTENT_EMAIL = "Wrong or non-existent email.";
   private static final String USER_REGISTER_EXCEPTION = "An error occurred during user registration.";

   @Autowired
   public UserServiceImpl(ModelMapper modelMapper,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
      this.modelMapper = modelMapper;
      this.userRepository = userRepository;
      this.roleRepository = roleRepository;
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      UserDetails userDetails = this.userRepository.findByUsername(username).orElse(null);
      if (userDetails == null) {
         throw new UsernameNotFoundException(WRONG_NON_EXISTENT_EMAIL);
      }
      return userDetails;
   }

   @Override
   public void registerUser(UserRegisterForm registerForm) {
      seedRolesInDb();

      User userEntity = new User();
      userEntity.setUsername(registerForm.getEmail());
      userEntity.setPassword(this.bCryptPasswordEncoder.encode(registerForm.getPassword()));
      boolean hasNoRegisteredUsers = this.userRepository.count() == 0;
      if (!hasNoRegisteredUsers) {
         // if user isn't the first registered, registration has to be approved
         userEntity.setEnabled(false);
      }
      userEntity.setCreatedDate(LocalDateTime.now());
      setUserRole(userEntity, hasNoRegisteredUsers);
      try {
         this.userRepository.save(userEntity);
      } catch (Exception ex) {
         throw new UserRegisterFailureException(USER_REGISTER_EXCEPTION);
      }
   }

   private void seedRolesInDb() {
      if (this.roleRepository.count() == 0) {
         this.roleRepository.save(new UserRole("ROLE_ADMIN"));
         this.roleRepository.save(new UserRole("ROLE_MODERATOR"));
      }
   }

   private void setUserRole(User userEntity, boolean hasNoUsers) {
      if (hasNoUsers) {
         userEntity.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
      } else {
         UserRole roleUser = this.roleRepository.findByAuthority("ROLE_MODERATOR").orElse(null);

         if (roleUser == null) {
            throw new IllegalArgumentException(NON_EXISTING_ROLE);
         }
         userEntity.setAuthorities(new HashSet<>());
         userEntity.getAuthorities().add(roleUser);
      }
   }

   @Override
   public List<UserViewModel> getAllRegisteredUsers() {
       return this.userRepository.findAll().stream()
               .map(user -> {
                  UserViewModel dto = new UserViewModel();
                  dto.setId(user.getId());
                  dto.setEmail(user.getUsername());
                  dto.setRegisteredOn(user.getCreatedDate().toString());
                  dto.setUserActive(user.isEnabled());
                  dto.setCanBeDeactivated(canBeDeactivated(user.getAuthorities()));

                  return dto;
               }).collect(Collectors.toList());
   }

   private boolean canBeDeactivated(Set<UserRole> authorities) {
      for (UserRole authority : authorities) {
         if (authority.getAuthority().equals("ROLE_ADMIN")) {
            return false;
         }
      }
      return true;
   }

   @Override
   public void changeUserStatus(String id) {
      Optional<User> user = this.userRepository.findById(id);

      if (user.isEmpty() || !canBeDeactivated(user.get().getAuthorities())) {
         return;
      }

      User userEntity = user.get();
      userEntity.setEnabled(!userEntity.isEnabled());

      this.userRepository.saveAndFlush(userEntity);
   }
}
