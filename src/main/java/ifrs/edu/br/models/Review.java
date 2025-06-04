package ifrs.edu.br.models;

import java.time.LocalDate;
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
import javax.persistence.ManyToOne;

/**
 * Review
 */
@Entity(name = "reviews")
public class Review {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column(length = 100, nullable = false)
        private String title;
        @Column(length = 600, nullable = false)
        private String text;
        @Column(name = "read_start_date", nullable = false)
        private LocalDate readStartDate;
        @Column(name = "read_end_date", nullable = true)
        private LocalDate readEndDate;

        @ManyToOne(cascade = CascadeType.REMOVE)
        @JoinColumn(name = "book_id")
        private Book book;

        @ManyToOne(cascade = CascadeType.REMOVE)
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToMany
        @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "review_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private List<User> likes;

        public Review(int id, String title, String text, LocalDate readStartDate, LocalDate readEndDate, Book book,
                        User user) {
                this.id = id;
                this.title = title;
                this.text = text;
                this.readStartDate = readStartDate;
                this.readEndDate = readEndDate;
                this.book = book;
                this.user = user;
        }

        public Review(String title, String text, LocalDate readStartDate, LocalDate readEndDate, Book book, User user) {
                this.title = title;
                this.text = text;
                this.readStartDate = readStartDate;
                this.readEndDate = readEndDate;
                this.book = book;
                this.user = user;
        }

        public Review() {
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public LocalDate getReadStartDate() {
                return readStartDate;
        }

        public void setReadStartDate(LocalDate readStartDate) {
                this.readStartDate = readStartDate;
        }

        public LocalDate getReadEndDate() {
                return readEndDate;
        }

        public void setReadEndDate(LocalDate readEndDate) {
                this.readEndDate = readEndDate;
        }

        public Book getBook() {
                return book;
        }

        public void setBook(Book book) {
                this.book = book;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public boolean addLike(User user) {
                if (likes.contains(user)) {
                        return false;
                }

                likes.add(user);
                return true;
        }

        @Override
        public String toString() {
                long time = this.readEndDate.toEpochDay() - this.readStartDate.toEpochDay();

                return "Review: \n" +
                                "id: " + this.id + "\n" +
                                "user: " + this.user.getName() + "\n" +
                                "book: " + this.book.getTitle() + "\n" +
                                "text: " + this.text + "\n" +
                                "likes: " + this.likes.size() + "\n" +
                                "Read Time: " + time + " day(s)";
        }
}
