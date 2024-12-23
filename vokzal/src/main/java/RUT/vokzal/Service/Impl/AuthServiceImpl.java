package RUT.vokzal.Service.Impl;

import RUT.vokzal.Exception.EmailIsPresentException;
import RUT.vokzal.Exception.PasswordIncorrectException;
import RUT.vokzal.Exception.UserNameIsPresentException;
import RUT.vokzal.Model.entity.User;
import RUT.vokzal.Model.enums.UserRoles;
import RUT.vokzal.Repository.UserRepository;
import RUT.vokzal.Repository.UserRoleRepository;
import RUT.vokzal.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    private UserRoleRepository userRoleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String username, String fullName, String email, int age, String password, String confirmPassword) {
        Optional<User> byUserName = this.userRepository.findByUsername(username);

        if (byUserName.isPresent()) {
            throw new UserNameIsPresentException(username);
        }

        Optional<User> byEmail = this.userRepository.findByEmail(email);

        if (byEmail.isPresent()) {
            throw new EmailIsPresentException(email);
        }

        if (!password.equals(confirmPassword)) {
            throw new PasswordIncorrectException();
        }


        var userRole = userRoleRepository.
                findRoleByName(UserRoles.USER).orElseThrow();

        User user = new User(
                username,
                passwordEncoder.encode(password),
                email,
                fullName,
                age
        );

        user.setRoles(List.of(userRole));

        this.userRepository.create(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " не найден!"));
    }
}