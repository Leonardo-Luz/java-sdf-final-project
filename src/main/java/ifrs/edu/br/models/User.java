package ifrs.edu.br.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * User
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column(length = 20, nullable = false, unique = true)
    private String email;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 20, nullable = false)
    private String password;
    @Column(nullable = true)
    private LocalDate birthday;

    public User(int id, String email, String name, String password, LocalDate birthday){
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthday = birthday;
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

    @Override
    public String toString() {
        return "User: \n" +
        "\tid: " + this.id + "\n" +
        "\temail: " + this.email + "\n" +
        "\tname: " + this.name + "\n" +
        "\tpassword: " + this.password + "\n" +
        "\tbirthday: " + this.birthday + "\n";
    }
}
