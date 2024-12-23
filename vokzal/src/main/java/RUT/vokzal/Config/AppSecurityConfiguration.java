package RUT.vokzal.Config;

import RUT.vokzal.Model.enums.UserRoles;
import RUT.vokzal.Repository.UserRepository;
import RUT.vokzal.Service.Impl.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class AppSecurityConfiguration {
    private UserRepository userRepository;
    private final CauthenticationSuccessHandler cauthenticationSuccessHandler;

    public AppSecurityConfiguration(CauthenticationSuccessHandler cauthenticationSuccessHandler) {
        this.cauthenticationSuccessHandler = cauthenticationSuccessHandler;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
        http
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests.
                                        requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll()
                                        .requestMatchers("/vok.ico", "/users/login", "/users/register", "/users/login-error", "/trips", "/users/success").permitAll().
                                        requestMatchers("/users/profile", "/users/logout").authenticated().
                                        requestMatchers("/admin").hasAnyRole(UserRoles.MODERATOR.name(), UserRoles.ADMIN.name()).
                                        requestMatchers("/employees", "/trains", "/trips", "/routes", "/platforms", "/vokzals").hasAnyRole(UserRoles.MODERATOR.name(), UserRoles.ADMIN.name()).
                                        requestMatchers("/employees/**", "/trains/**", "/trips/**",
                                                "/routes/**", "/platforms/**", "/vokzals/**").hasRole(UserRoles.ADMIN.name()).
                                        requestMatchers("/appeals/*/edit", "/appeals/admin-list").hasAnyRole(UserRoles.MODERATOR.name(), UserRoles.ADMIN.name()).
                                        requestMatchers("/appeals/create", "/appeals/my-list").hasRole(UserRoles.USER.name()).
                                        anyRequest().authenticated()
                )
                .formLogin(
                        (formLogin) ->
                                formLogin.
                                        loginPage("/users/login").
                                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                                        successHandler(cauthenticationSuccessHandler).
                                        failureForwardUrl("/users/login-error")
                )
                .logout((logout) ->
                        logout.logoutUrl("/users/logout").
                                logoutSuccessUrl("/users/login").
                                invalidateHttpSession(true)

                ).exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403")
                ).securityContext(
                        securityContext -> securityContext.
                                securityContextRepository(securityContextRepository)
                );

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() { return new AppUserDetailsService(userRepository); }
}