package ifrs.edu.br.models;

import java.util.List;

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

        @ManyToMany
        @JoinTable(name = "badge_user", joinColumns = @JoinColumn(name = "badge_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private List<User> users;

        public Badge(int id, String name, String requirements) {
                this.id = id;
                this.name = name;
                this.requirements = requirements;
        }

        public Badge() {
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getRequirements() {
                return requirements;
        }

        public void setRequirements(String requirements) {
                this.requirements = requirements;
        }

        @Override
        public String toString() {
                return "Badge: \n" +
                                "id: " + this.id + "\n" +
                                "name: " + this.name + "\n" +
                                "requirements: " + this.requirements;
        }
}
