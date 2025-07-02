package ifrs.edu.br.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * Badge
 */
@Entity(name = "badges")
public class Badge {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(length = 50, nullable = false, unique = true)
        private String name;

        @Column(length = 300, nullable = false)
        private String requirements;

        @ManyToMany(cascade = CascadeType.REMOVE)
        @JoinTable(name = "badge_user", joinColumns = @JoinColumn(name = "badge_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private List<User> users;

        public Badge(int id, String name, String requirements) {
                setId(id);
                setName(name);
                setRequirements(requirements);
                this.users = new ArrayList<>();
        }

        public Badge(String name, String requirements) {
                setName(name);
                setRequirements(requirements);
                this.users = new ArrayList<>();
        }

        public Badge() {
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                if (id < 0)
                        throw new RuntimeException("ID can't be negative.");

                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                if (name.isBlank())
                        throw new RuntimeException("Name can't be blank.");

                if (name.length() < 3)
                        throw new RuntimeException("Name size can't be lower than 3.");

                this.name = name;
        }

        public String getRequirements() {
                return requirements;
        }

        public void setRequirements(String requirements) {
                if (requirements.isBlank())
                        throw new RuntimeException("Requirements can't be blank.");

                if (requirements.length() < 3)
                        throw new RuntimeException("Requirements size can't be lower than 3.");

                this.requirements = requirements;
        }

        public List<User> getUsers() {
                return users;
        }

        public void setUsers(List<User> users) {
                this.users = users;
        }

        public void addUsers(User user) {
                if (user == null)
                        throw new RuntimeException("Null user can't receive an achievement.");

                if (this.users.contains(user))
                        throw new RuntimeException("User already has this badge");

                this.users.add(user);
        }

        @Override
        public String toString() {
                return "Badge: \n" +
                                "\tid: " + this.id + "\n" +
                                "\tname: " + this.name + "\n" +
                                "\trequirements: " + this.requirements;
        }
}
