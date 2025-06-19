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
                this.setId(id);
                this.setTitle(title);
                this.setText(text);
                this.setReadStartDate(readStartDate);
                this.setReadEndDate(readEndDate);
                this.setBook(book);
                this.setUser(user);
        }

        public Review(String title, String text, LocalDate readStartDate, LocalDate readEndDate, Book book, User user) {
                this.setTitle(title);
                this.setText(text);
                this.setReadStartDate(readStartDate);
                this.setReadEndDate(readEndDate);
                this.setBook(book);
                this.setUser(user);
        }

        public Review() {
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                if (id < 0)
                        throw new RuntimeException("Id can't be negative!");

                this.id = id;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                if (text.isBlank())
                        throw new RuntimeException("Text can't be blank");

                if (text.length() < 6)
                        throw new RuntimeException("Text length can't be lower than 6");

                this.text = text;
        }

        public LocalDate getReadStartDate() {
                return readStartDate;
        }

        public void setReadStartDate(LocalDate readStartDate) {
                if(readStartDate.toEpochDay() > LocalDate.now().toEpochDay())
                        throw new RuntimeException("Read start date can't be in the future!");

                if(readEndDate != null && readStartDate.toEpochDay() > readEndDate.toEpochDay())
                        throw new RuntimeException("Read start date can't be after read end date!");

                this.readStartDate = readStartDate;
        }

        public LocalDate getReadEndDate() {
                return readEndDate;
        }

        public void setReadEndDate(LocalDate readEndDate) {
                if(readEndDate.toEpochDay() > LocalDate.now().toEpochDay())
                        throw new RuntimeException("Read end date can't be in the future!");

                if(readStartDate != null && readEndDate.toEpochDay() < readStartDate.toEpochDay())
                        throw new RuntimeException("Read end date can't be before read start date!");

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
                if (title.isBlank())
                        throw new RuntimeException("Title can't be blank");

                if (title.length() < 6)
                        throw new RuntimeException("Title length can't be lower than 6");

                this.title = title;
        }

        public void addLike(User user) {
                if (likes.contains(user))
                        throw new RuntimeException("User already liked this review");

                likes.add(user);
        }

        @Override
        public String toString() {
                long time = this.readEndDate.toEpochDay() - this.readStartDate.toEpochDay();

                return "Review: \n" +
                                "\tid: " + this.id + "\n" +
                                "\tuser: " + this.user.getName() + "\n" +
                                "\tbook: " + this.book.getTitle() + "\n" +
                                "\ttitle: " + this.title + "\n" +
                                "\ttext: " + this.text + "\n" +
                                "\tlikes: " + this.likes.size() + "\n" +
                                "\tRead Time: " + time + " day(s)";
        }
}
