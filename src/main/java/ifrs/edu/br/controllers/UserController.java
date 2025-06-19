package ifrs.edu.br.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    public User findByEmailHandler(String email) {
        try {
            Validation.emailValidation(email);

            User user = this.userDAO.findByEmail(email);

            if (user == null)
                throw new RuntimeException("User not found!");

            return user;
        } catch (RuntimeException err) {
            System.out.println(err);
            return null;
        }
    }

    public User loginHandler(String email, String password) {
        try {
            if (this.fileManager.exists())
                throw new RuntimeException("Already logged in.");

            Validation.emailValidation(email);

            String hashedPassword = passwordEncoder.encode(password);
            User user = userDAO.login(email, hashedPassword);

            if (user == null)
                throw new RuntimeException("Error: Password or Email invalid");

            ArrayList<String> fileData = new ArrayList<>();
            fileData.add(user.getEmail());
            fileData.add(password);

            fileManager.create(fileData);

            return user;
        } catch (RuntimeException err) {
            System.out.println(err);
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
            System.out.println(err);
        }
    }

    public void logout() {
        try {
            this.fileManager.delete();
        } catch (RuntimeException err) {
            System.out.println(err);
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
            System.out.println(err);
            return null;
        } catch (FileNotFoundException err) {
            System.out.println(err);
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
            System.out.println(err);
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
            System.out.println(err);
            return null;
        }
    }

    @Override
    public void updateHandler(User object) {
        try {
            userDAO.update(object);
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }

    @Override
    public void deleteHandler(int id) {
        try {
            if (id < 0)
                throw new RuntimeException("Id can't be negative");

            userDAO.delete(id);
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }
}
