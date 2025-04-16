package p42.projetabc.book.retrofit;

import java.util.List;

public class TagsResponseBody {
        public List<Tag> Tags;
        public static class Tag{
                public int id;
                public String name;
        }
}