package ifrs.edu.br.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Book
 */
@Entity
public class Book {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column(length = 200, nullable = false, unique = true)
        private String name;
        @Column(nullable = false)
        private int pages;
        @Column(length = 400, nullable = true)
        private String synopsis;

        public Book(int id, String name, int pages, String synopsis) {
                this.id = id;
                this.name = name;
                this.pages = pages;
                this.synopsis = synopsis;
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
                return "Book: \n" +
                                "\tid: " + this.id + "\n" +
                                "\tname: " + this.name + "\n" +
                                "\tpages: " + this.pages + "\n" +
                                "\tsynopsis: " + this.synopsis;
        }
}
