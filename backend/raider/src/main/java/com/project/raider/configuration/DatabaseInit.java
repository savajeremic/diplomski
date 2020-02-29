/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.configuration;

import com.project.raider.dao.GameFlag;
import com.project.raider.dao.User;
import com.project.raider.dao.UserRole;
import com.project.raider.repository.GameFlagRepository;
import com.project.raider.repository.GameReviewRepository;
import com.project.raider.repository.UserRepository;
import com.project.raider.repository.UserRoleRepository;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sava
 */
@Service
public class DatabaseInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private GameFlagRepository gameFlagRepository;
    //note sebi
    //@Lazy loadujemo BCrypt zrno zato sto se ova klasa izvrsava pre klase
    //(SecurityConfig) koja upravo kreira ovo zrno(anotacijom @Bean)
    //u suprotnom injektujemo zrno(@Autowired) koje tehnicki ni ne postoji jos
    @Lazy
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserRole userRole, adminRole;
    private User admin, user;
    private GameFlag cart, wishlist, owned;
    private List<User> userList;
    private List<UserRole> userRoleList;
    private List<GameFlag> gameFlagList;

    private final String adminRoleName = Constants.ROLE_ADMIN,
            userRoleName = Constants.ROLE_USER,
            cartFlagName = Constants.FLAG_CART,
            wishlistFlagName = Constants.FLAG_WISHLIST,
            ownedFlagName = Constants.FLAG_OWNED;

    private final boolean isActive = true;
    
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    public DatabaseInit() {
    }

    public void addUserRoles() {
        userRole = new UserRole((short) 1, userRoleName);
        adminRole = new UserRole((short) 2, adminRoleName);
        userRoleList = Arrays.asList(userRole, adminRole);//addall ili samo =
        userRoleRepository.saveAll(userRoleList);
    }

    public void addUsers() {
        admin = new User("admin", passwordEncoder.encode("admin"),
                "admin@adminson.com", "Admin Adminson",
                Date.from(new GregorianCalendar(1970, 1, 1).toInstant()),
                "Adminton", isActive, findUserRole(adminRoleName));
        user = new User("user", passwordEncoder.encode("user"),
                "user@userson.com", "User Userson",
                Date.from(new GregorianCalendar(1971, 2, 2).toInstant()),
                "Userton", isActive, findUserRole(userRoleName));
        userList = Arrays.asList(admin, user);
        userRepository.saveAll(userList);
    }

    public void addGameFlags() {
        cart = new GameFlag((short) 1, "cart");
        wishlist = new GameFlag((short) 2, "wishlist");
        owned = new GameFlag((short) 3, "owned");
        gameFlagList = Arrays.asList(cart, wishlist, owned);
        gameFlagRepository.saveAll(gameFlagList);
    }

    public UserRole findUserRole(String role) {
        return userRoleRepository.findByName(role);
    }

    public Date setDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);//Calendar.JANUARY
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();
        return date;
    }

    private boolean gameFlagExistsByName(String gameFlagName) {
        return this.gameFlagRepository.existsByName(gameFlagName);
    }

    private boolean userRoleExistsByName(String userRoleName) {
        return this.userRoleRepository.existsByName(userRoleName);
    }

    private boolean userExistsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public void run(String... args) throws Exception {

        if (!userRoleExistsByName(adminRoleName) || !userRoleExistsByName(userRoleName)) {
            addUserRoles();
        }
        if (!userExistsByEmail("admin@adminson.com") || !userExistsByEmail("user@userson.com")) {
            addUsers();
        }
        if (!gameFlagExistsByName(cartFlagName) || !gameFlagExistsByName(wishlistFlagName) || !gameFlagExistsByName(ownedFlagName)) {
            addGameFlags();
        }
        System.out.println("Database initialized. ");
    }

}
