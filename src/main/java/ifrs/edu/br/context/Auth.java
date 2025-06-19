package ifrs.edu.br.context;

import ifrs.edu.br.models.User;
import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.utils.FileManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Auth
 */
public class Auth {
    // private static BCryptPasswordEncoder passwordEncoder = new
    // BCryptPasswordEncoder();
    //
    // public static void login(EntityManager entityManager, String email, String
    // password) {
    // if (FileManager.exists()) {
    // System.out.println("Already logged in.");
    // return;
    // }
    //
    // UserDAO userDAO = new UserDAO(entityManager);
    // User user = userDAO.findByEmail(email);
    //
    // if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
    // System.out.println("Error: Password or Email invalid");
    // return;
    // }
    //
    // ArrayList<String> fileData = new ArrayList<>();
    // fileData.add(user.getEmail());
    // fileData.add(password);
    //
    // try {
    // FileManager.create(fileData);
    // System.out.println("Signup successful.");
    // } catch (RuntimeException err) {
    // System.out.println(err);
    // }
    // }
    //
    // public static void signup(EntityManager entityManager, User user) {
    // if (FileManager.exists()) {
    // System.out.println("Already logged in.");
    // return;
    // }
    // String rawPassword = user.getPassword();
    //
    // UserDAO userDAO = new UserDAO(entityManager);
    // String hashedPassword = passwordEncoder.encode(user.getPassword());
    // user.setPassword(hashedPassword);
    // userDAO.insert(user);
    //
    // ArrayList<String> fileData = new ArrayList<>();
    // fileData.add(user.getEmail());
    // fileData.add(rawPassword);
    //
    // try {
    // FileManager.create(fileData);
    // System.out.println("Signup successful.");
    // } catch (RuntimeException err) {
    // System.out.println(err);
    // }
    // }
    //
    // public static void logout() {
    // try {
    // FileManager.delete();
    // } catch (Exception err) {
    // System.out.println(err);
    // }
    // }
    //
    // public static User verify(EntityManager entityManager) {
    // try {
    // if (!FileManager.exists())
    // return null;
    //
    // List<String> data = FileManager.get();
    //
    // String email = data.get(0);
    //
    // String password = data.get(1);
    //
    // UserDAO userDAO = new UserDAO(entityManager);
    // User user = userDAO.findByEmail(email);
    //
    // if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
    // System.out.println("Verification failed, loggin out...");
    // logout();
    // return null;
    // }
    //
    // return user;
    // } catch (IndexOutOfBoundsException err) {
    // System.out.println("Verification failed, loggin out...");
    // logout();
    // return null;
    // } catch (FileNotFoundException e) {
    // System.out.println("Login file not found!");
    // return null;
    // }
    // }
}
