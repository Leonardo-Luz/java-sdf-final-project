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

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                if (title.isBlank())
                        throw new RuntimeException("Title can't be blank.");

                if (title.length() < 3)
                        throw new RuntimeException("Title size can't be lower than 3.");

                this.title = title;
        }

        public int getPages() {
                return pages;
        }

        public void setPages(int pages) {
                if (pages < 0)
                        throw new RuntimeException("Pages can't be negative");

                if (pages == 0)
                        throw new RuntimeException("Pages can't be ZERO");

                this.pages = pages;
        }

        public String getSynopsis() {
                return synopsis;
        }

        public void setSynopsis(String synopsis) {
                if (synopsis.isBlank())
                        throw new RuntimeException("Synopsis can't be blank.");

                if (synopsis.length() < 3)
                        throw new RuntimeException("Synopsis size can't be lower than 3.");

                this.synopsis = synopsis;
        }

        @Override
        public String toString() {
                String croppedSynopsis = this.synopsis.length() > 40 ? (this.synopsis.substring(0, 40) + "...")
                                : this.synopsis;

                return "Book: \n" +
                                "\tid: " + this.id + "\n" +
                                "\ttitle: " + this.title + "\n" +
                                "\tpages: " + this.pages + "\n" +
                                "\tsynopsis: " + croppedSynopsis + "\n" +
                                "\treviews: " + reviews.size();
        }
}
