package ifrs.edu.br.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ifrs.edu.br.utils.Validation;

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

        @Transient
        private int age;

        @ManyToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
        private List<Badge> badges;

        @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
        private List<Review> reviews;

        @ManyToMany(mappedBy = "likes", cascade = CascadeType.REMOVE)
        private List<Review> likes;

        public User(int id, String email, String name, String password, LocalDate birthday) {
                this.setId(id);
                this.setEmail(email);
                this.setName(name);
                this.setPassword(password);
                this.setBirthday(birthday);
                this.setAge();
                this.setRole("READER");
        }

        public User(String email, String name, String password, LocalDate birthday) {
                this.setEmail(email);
                this.setName(name);
                this.setPassword(password);
                this.setBirthday(birthday);
                this.setAge();
                this.setRole("READER");
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
                if (name.isBlank())
                        throw new RuntimeException("Name can't be blank");

                if (name.length() < 4)
                        throw new RuntimeException("Name length can't be lower than 4");

                this.name = name;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                if (password.isBlank())
                        throw new RuntimeException("Password can't be blank");

                if (password.length() < 4)
                        throw new RuntimeException("Password length can't be lower than 4");

                this.password = password;
        }

        public LocalDate getBirthday() {
                return birthday;
        }

        public void setBirthday(LocalDate birthday) {
                if (birthday.toEpochDay() >= LocalDate.now().toEpochDay())
                        throw new RuntimeException("Birthday can't be in the future");

                this.birthday = birthday;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                if (!Validation.emailValidation(email))
                        throw new RuntimeException("Invalid email format.");

                this.email = email;
        }

        public String getRole() {
                return role;
        }

        public void setRole(String role) {
                if (!(role.equals("ADMIN") || role.equals("READER")))
                        throw new RuntimeException("Invalid Role");

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

        public int getAge() {
                return age;
        }

        public void setAge() {
                this.age = (int) ((LocalDate.now().toEpochDay() - birthday.toEpochDay()) / 365);
        }

        public String getFormatedBadges() {
                return (this.badges != null && this.badges.size() > 0) ? String.join("", this.badges
                                .stream()
                                .map(badge -> "\n\t" + badge.toString())
                                .toArray(String[]::new))
                                : "NONE";
        }

        @Override
        public String toString() {

                return "User: \n" +
                                "\tid: " + this.id + "\n" +
                                "\temail: " + this.email + "\n" +
                                "\tname: " + this.name + "\n" +
                                "\tpassword: " + ("*").repeat(this.password.length() / 3) + "\n" +
                                "\trole: " + this.role + "\n" +
                                "\tage: " + this.age + " years old\n" +
                                "\tbadges: " + getFormatedBadges() + "\n";
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null || getClass() != obj.getClass())
                        return false;

                User user = (User) obj;
                return id == user.id &&
                                age == user.age &&
                                email.equals(user.email) &&
                                name.equals(user.name) &&
                                password.equals(user.password) &&
                                role == user.role;
        }
}
