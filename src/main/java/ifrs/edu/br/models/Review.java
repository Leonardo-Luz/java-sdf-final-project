package ifrs.edu.br.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Review
 */
public class Review {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column(length = 600, nullable = false)
        private String text;
        @Column(name = "read_start_date", nullable = false)
        private LocalDate readStartDate;
        @Column(name = "read_end_date", nullable = true)
        private LocalDate readEndDate;

        private Book book;
        private User user;

        public Review(int id, String text, LocalDate readStartDate, LocalDate readEndDate, Book book, User user) {
                this.id = id;
                this.text = text;
                this.readStartDate = readStartDate;
                this.readEndDate = readEndDate;
                this.book = book;
                this.user = user;
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

        @Override
        public String toString() {
                return "Review: \n" +
                                "id: " + this.id + "\n" +
                                "text: " + this.text + "\n" +
                                "readStartDate: " + this.readStartDate + "\n" +
                                "readEndDate: " + this.readEndDate + "\n" +
                                "book: " + this.book + "\n" +
                                "user: " + this.user;
        }
}
