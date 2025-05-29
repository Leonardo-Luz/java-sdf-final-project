package ifrs.edu.br.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Book
 */
@Entity(name = "books")
public class Book {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column(length = 200, nullable = false, unique = true)
        private String title;
        @Column(nullable = false)
        private int pages;
        @Column(length = 400, nullable = true)
        private String synopsis;

        @OneToMany(mappedBy = "book")
        private List<Review> reviews;

        public Book(int id, String title, int pages, String synopsis) {
                this.id = id;
                this.title = title;
                this.pages = pages;
                this.synopsis = synopsis;
        }

        public Book(String title, int pages, String synopsis) {
                this.title = title;
                this.pages = pages;
                this.synopsis = synopsis;
        }

        public Book() {
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String gettitle() {
                return title;
        }

        public void settitle(String title) {
                this.title = title;
        }

        public int getPages() {
                return pages;
        }

        public void setPages(int pages) {
                this.pages = pages;
        }

        public String getSynopsis() {
                return synopsis;
        }

        public void setSynopsis(String synopsis) {
                this.synopsis = synopsis;
        }

        @Override
        public String toString() {
                String croppedSynopsis = this.synopsis.length() > 40 ? ( this.synopsis.substring(0, 40) + "..." ) : this.synopsis;

                return "Book: \n" +
                                "\tid: " + this.id + "\n" +
                                "\ttitle: " + this.title + "\n" +
                                "\tpages: " + this.pages + "\n" +
                                "\tsynopsis: " + croppedSynopsis;
        }
}
