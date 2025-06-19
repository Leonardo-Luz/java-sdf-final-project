package ifrs.edu.br.cli;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ifrs.edu.br.controllers.UserController;
import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.utils.FileManager;

/**
 * Logout
 */
public class Logout {
    public static void command(EntityManager entityManager) {
        FileManager fileManager = new FileManager();
        UserDAO userDAO = new UserDAO(entityManager);
        UserController userController = new UserController(userDAO, fileManager, new BCryptPasswordEncoder());

        userController.logout();
        System.out.println("Logout Successfully!");
    }
}
