package RUT.vokzal.Service;

import RUT.vokzal.Model.entity.User;

public interface AuthService {
    void register(String username, String fullName, String email, int age, String password, String confirmPassword);
    User getUser(String username);
}
