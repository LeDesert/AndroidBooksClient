package p42.projetabc.book.retrofit;


public class BookResponseBody {

    public int id;
    public String name;
    public int authorId;
    public int publicationYear;
    public Author author;

    public static class Author {
        public int id;
        public String lastname;
    }
}
