package ifrs.edu.br.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.models.User;
import ifrs.edu.br.utils.FileManager;
import ifrs.edu.br.utils.Validation;

/**
 * UserController
 */
public class UserController implements Controller<User> {
    private BCryptPasswordEncoder passwordEncoder;
    private UserDAO userDAO;
    private FileManager fileManager;

    public UserController(UserDAO userDAO, FileManager fileManager, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.fileManager = fileManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UserController(EntityManager entityManager) {
        this.fileManager = new FileManager();
        this.userDAO = new UserDAO(entityManager);
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserController() {
        this.fileManager = new FileManager();
        this.userDAO = new UserDAO();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User findByEmailHandler(String email) {
        try {
            if (!Validation.emailValidation(email))
                throw new RuntimeException("Invalid email format.");

            User user = this.userDAO.findByEmail(email);

            if (user == null)
                throw new RuntimeException("User not found!");

            return user;
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    public User loginHandler(String email, String password) {
        try {
            if (this.fileManager.exists())
                throw new RuntimeException("Already logged in.");

            if (!Validation.emailValidation(email))
                throw new RuntimeException("Invalid email format.");

            User user = userDAO.findByEmail(email);

            if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Error: Password or Email invalid");
            }

            ArrayList<String> fileData = new ArrayList<>();
            fileData.add(user.getEmail());
            fileData.add(password);

            fileManager.create(fileData);

            System.out.println("Login successful");
            return user;
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    public void signup(User user) {
        try {
            if (this.fileManager.exists())
                throw new RuntimeException("Already logged in.");

            String password = user.getPassword();
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);

            userDAO.insert(user);

            ArrayList<String> fileData = new ArrayList<>();
            fileData.add(user.getEmail());
            fileData.add(password);

            this.fileManager.create(fileData);
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
    }

    public void logout() {
        try {
            this.fileManager.delete();
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
    }

    public User verify() {
        try {
            if (!this.fileManager.exists())
                return null;

            List<String> data = this.fileManager.get();

            String email = data.get(0);

            String password = data.get(1);

            User user = userDAO.findByEmail(email);

            if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
                logout();
                throw new RuntimeException("Verification failed, loggin out...");
            }

            return user;
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    @Override
    public void insertHandler(User object) {
        try {
            if (object == null)
                throw new RuntimeException("User can't be null");

            userDAO.insert(object);
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
    }

    @Override
    public User findHandler(int id) {
        try {
            if (id < 0)
                throw new RuntimeException("Id can't be negative");

            User user = userDAO.find(id);

            if (user == null)
                throw new RuntimeException("User not found!");

            return user;
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    @Override
    public void updateHandler(User object) {
        try {
            userDAO.update(object);
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
    }

    @Override
    public void deleteHandler(int id) {
        try {
            if (id < 0)
                throw new RuntimeException("Id can't be negative");

            userDAO.delete(id);
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
    }
}
