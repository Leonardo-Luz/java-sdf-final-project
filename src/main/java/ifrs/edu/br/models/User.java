package ifrs.edu.br.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * User
 */
@Entity(name = "users")
public class User {
        @Id
        @GeneratedValue
        private int id;
        @Column(length = 20, nullable = false, unique = true)
        private String email;
        @Column(length = 100, nullable = false)
        private String name;
        @Column(length = 200, nullable = false)
        private String password;
        @Column(length = 20, nullable = false)
        private String role;
        @Column(nullable = true)
        private LocalDate birthday;

        @ManyToMany(mappedBy = "users")
        private List<Badge> badges;

        @OneToMany(mappedBy = "user")
        private List<Review> reviews;

        @ManyToMany(mappedBy = "likes")
        private List<Review> likes;

        public User(int id, String email, String name, String password, LocalDate birthday) {
                this.id = id;
                this.email = email;
                this.name = name;
                this.password = password;
                this.birthday = birthday;
                this.role = "READER";
        }

        public User(String email, String name, String password, LocalDate birthday) {
                this.email = email;
                this.name = name;
                this.password = password;
                this.birthday = birthday;
                this.role = "READER";
        }

        public User() {
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

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public LocalDate getBirthday() {
                return birthday;
        }

        public void setBirthday(LocalDate birthday) {
                this.birthday = birthday;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getRole() {
                return role;
        }

        public void setRole(String role) {
                this.role = role;
        }

        public List<Badge> getBadges() {
                return badges;
        }

        public void setBadges(List<Badge> badges) {
                this.badges = badges;
        }

        public List<Review> getReviews() {
                return reviews;
        }

        public void setReviews(List<Review> reviews) {
                this.reviews = reviews;
        }

        public List<Review> getLikes() {
                return likes;
        }

        public void setLikes(List<Review> likes) {
                this.likes = likes;
        }

        @Override
        public String toString() {
                return "User: \n" +
                                "\tid: " + this.id + "\n" +
                                "\temail: " + this.email + "\n" +
                                "\tname: " + this.name + "\n" +
                                "\tpassword: " + this.password + "\n" +
                                "\trole: " + this.role + "\n" +
                                "\tbirthday: " + this.birthday + "\n";
        }
}
