package bg.sdcf.domain.entities;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

   private String email;
   private String password;
   private LocalDateTime createdDate;

   private boolean isAccountNonLocked;
   private boolean isAccountNonExpired;
   private boolean isCredentialsNonExpired;
   private boolean isEnabled;
   private Set<UserRole> authorities;

   public User() {
      this.isEnabled = true;
      this.authorities = new HashSet<>();
   }

   @Override
   @Column(name = "email", nullable = false, unique = true, updatable = false)
   public String getUsername() {
      return this.email;
   }

   public void setUsername(String email) {
      this.email = email;
   }

   @Override
   @Column(name = "password", nullable = false)
   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @Column(name = "created_date")
   public LocalDateTime getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(LocalDateTime createdDate) {
      this.createdDate = createdDate;
   }

   @Override
   @Transient
   @Column(name = "is_account_non_locked")
   public boolean isAccountNonLocked() {
      return true;
   }

   public void setAccountNonLocked(boolean accountNonLocked) {
      isAccountNonLocked = accountNonLocked;
   }

   @Override
   @Transient
   @Column(name = "is_account_non_expired")
   public boolean isAccountNonExpired() {
      return true;
   }

   public void setAccountNonExpired(boolean accountNonExpired) {
      isAccountNonExpired = accountNonExpired;
   }

   @Override
   @Transient @Column(name = "is_credentials_non_expired")
   public boolean isCredentialsNonExpired() {
      return true;
   }

   public void setCredentialsNonExpired(boolean credentialsNonExpired) {
      isCredentialsNonExpired = credentialsNonExpired;
   }

   @Override
   @Column(name = "is_enabled")
   public boolean isEnabled() {
      return isEnabled;
   }

   public void setEnabled(boolean enabled) {
      isEnabled = enabled;
   }

   @Override
   @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
   @JoinTable(name = "users_roles",
         joinColumns = @JoinColumn(name = "user_id"),
         inverseJoinColumns = @JoinColumn(name = "role_id"))
   public Set<UserRole> getAuthorities() {
      return this.authorities;
   }

   public void setAuthorities(Set<UserRole> authorities) {
      this.authorities = authorities;
   }

}
