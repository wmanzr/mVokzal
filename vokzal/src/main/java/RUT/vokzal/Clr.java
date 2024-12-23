package RUT.vokzal;

import RUT.vokzal.Model.entity.*;
import RUT.vokzal.Model.enums.StatusPlatform;
import RUT.vokzal.Model.enums.StatusTrain;
import RUT.vokzal.Model.enums.StatusTrip;
import RUT.vokzal.Model.enums.UserRoles;
import com.github.javafaker.Faker;
import RUT.vokzal.Repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Component
public class Clr implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final TrainRepository trainRepository;
    private final PlatformRepository platformRepository;
    private final RouteRepository routeRepository;
    private final TripRepository tripRepository;
    private final VokzalRepository vokzalRepository;
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    private final Faker faker = new Faker(new Locale("ru"));
    private final Random random = new Random();

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public Clr(EmployeeRepository employeeRepository, TrainRepository trainRepository, 
               PlatformRepository platformRepository, RouteRepository routeRepository,
               TripRepository tripRepository, VokzalRepository vokzalRepository,
               UserRepository userRepository, UserRoleRepository userRoleRepository,
               PasswordEncoder passwordEncoder, @Value("${app.default.password}") String defaultPassword) {
        this.employeeRepository = employeeRepository;
        this.trainRepository = trainRepository;
        this.platformRepository = platformRepository;
        this.routeRepository = routeRepository;
        this.tripRepository = tripRepository;
        this.vokzalRepository = vokzalRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        int count = 40;

//        clearDatabase();

        initRoles();
        initUsers();

//         generateTrains(count);
//         generateVokzals();
//         generatePlatforms();
//         generateEmployees(count);
//         generateRoutes(count);
//         generateTrips(count);
    }

    private void clearDatabase() {
        entityManager.createNativeQuery("TRUNCATE TABLE trip RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE route RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE employee RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE train RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE platform RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE vokzal RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE appeal RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE users RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE users_roles RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE roles RESTART IDENTITY CASCADE").executeUpdate();
    }

    private void initRoles() {
        if (userRoleRepository.findAll().isEmpty()) {
            var moderatorRole = new Role(UserRoles.MODERATOR);
            var adminRole = new Role(UserRoles.ADMIN);
            var normalUserRole = new Role(UserRoles.USER);
            userRoleRepository.create(moderatorRole);
            userRoleRepository.create(adminRole);
            userRoleRepository.create(normalUserRole);
        }
    }

    private void initUsers() {
        if (userRepository.findAll().isEmpty()) {
            initAdmin();
            initModerator();
            initNormalUser();
        }
    }

    private void initAdmin(){
        var adminRole = userRoleRepository.
                findRoleByName(UserRoles.ADMIN).orElseThrow();

        var adminUser = new User("admin", passwordEncoder.encode(defaultPassword), "admin@example.com", "Admin Adminovich", 30);
        adminUser.setRoles(List.of(adminRole));

        userRepository.create(adminUser);
    }

    private void initModerator(){

        var moderatorRole = userRoleRepository.
                findRoleByName(UserRoles.MODERATOR).orElseThrow();

        var moderatorUser = new User("moderator", passwordEncoder.encode(defaultPassword), "moderator@example.com", "Moder Moderovich", 24);
        moderatorUser.setRoles(List.of(moderatorRole));

        userRepository.create(moderatorUser);
    }

    private void initNormalUser(){
        var userRole = userRoleRepository.
                findRoleByName(UserRoles.USER).orElseThrow();

        var normalUser = new User("user", passwordEncoder.encode(defaultPassword), "user@example.com", "User Userovich", 22);
        normalUser.setRoles(List.of(userRole));

        userRepository.create(normalUser);

        var userRole0 = userRoleRepository.
                findRoleByName(UserRoles.USER).orElseThrow();

        var normalUser0 = new User("user0", passwordEncoder.encode(defaultPassword), "user0@example.com", "User0 Userovich0", 20);
        normalUser0.setRoles(List.of(userRole0));

        userRepository.create(normalUser0);
    }
    
    private void generateTrains(int count) {
        for (int i = 0; i < count; i++) {
            Train train = new Train();
            train.setNumber(faker.number().numberBetween(1000, 9999));
            train.setType(faker.lorem().word());
            train.setModel(faker.commerce().productName());
            train.setCapacity(faker.number().numberBetween(50, 300));
            train.setMaxSpeed(faker.number().numberBetween(80, 180));
            train.setStatusTrain(randomStatusTrain());
            trainRepository.create(train);
        }
    }

    private StatusTrain randomStatusTrain() {
        return StatusTrain.values()[random.nextInt(StatusTrain.values().length)];
    }

    private void generateVokzals() {
        for (int i = 0; i < 15; ) {
            Vokzal vokzal = new Vokzal();
            String name = faker.company().name();

            if (vokzalRepository.findByName(name) == null) {
                vokzal.setName(name);
                vokzal.setCity(faker.address().city());
                vokzal.setCapacity(faker.number().numberBetween(200, 500));
                vokzalRepository.create(vokzal);
                i++;
            }
            vokzal.setDel(false);
        }
    }

    private void generatePlatforms() {
            List<Vokzal> vokzals = vokzalRepository.findAll();
            for (Vokzal vokzal : vokzals) {

                for (int platformNumber = 1; platformNumber <= 12; platformNumber++) {
                Platform platform = new Platform();
                platform.setNumber(platformNumber);
                platform.setType(faker.lorem().word());
                platform.setVokzalId(vokzal);
                platform.setStatusPlatform(randomStatusPlatform());
                platformRepository.create(platform);
            }
        }
    }

    private StatusPlatform randomStatusPlatform() {
        return StatusPlatform.values()[random.nextInt(StatusPlatform.values().length)];
    }
    
    private void generateEmployees(int count) {
        for (int i = 0; i < count; i++) {
            Employee employee = new Employee();
            employee.setPost(faker.job().position());
            employee.setLogin(faker.name().fullName());
            employee.setExperience(faker.number().numberBetween(1, 20));
            employee.setTrainId(trainRepository.findById(i+1));
            employee.setDel(false);
            employeeRepository.create(employee);
        }
    }
    
    private void generateRoutes(int count) {
        for (int i = 0; i < count*6; i++) {
            Route route = new Route();
            route.setTimeDep(LocalTime.of(faker.number().numberBetween(0, 23), faker.number().numberBetween(0, 59)));
            route.setTimeArr(LocalTime.of(faker.number().numberBetween(0, 23), faker.number().numberBetween(0, 59)));
            route.setDepPlId(platformRepository.findAll().get(random.nextInt(15*12)));
            route.setArrPlId(platformRepository.findAll().get(random.nextInt(15*12)));
            route.setDel(false);
            routeRepository.create(route);
        }
    }

    private void generateTrips(int count) {
        for (int i = 0; i < 15*15; i++) {
            Trip trip = new Trip();
            trip.setDateDep(LocalDate.now().plusDays(faker.number().numberBetween(-10, 5)));
            trip.setDateArr(LocalDate.now().plusDays(faker.number().numberBetween(6, 20)));
            trip.setTrain(trainRepository.findAll().get(random.nextInt(count)));
            trip.setRoute(routeRepository.findAll().get(random.nextInt(count)));
            trip.setStatusTrip(randomStatusTrip());
            trip.setDelayed(random.nextBoolean());
            trip.setDelayTime(trip.isDelayed() ? LocalTime.of(faker.number().numberBetween(0, 0), faker.number().numberBetween(0, 59)) : null);
            tripRepository.create(trip);
        }
    }

    private StatusTrip randomStatusTrip() {
        return StatusTrip.values()[random.nextInt(StatusTrip.values().length)];
    }
}
