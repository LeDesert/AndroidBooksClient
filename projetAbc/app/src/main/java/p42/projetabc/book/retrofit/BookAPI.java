package p42.projetabc.book.retrofit;

import java.util.List;

import okhttp3.ResponseBody;
import p42.projetabc.book.bookCreation.BookCreateResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookAPI {
    @GET("/books")
    Call<List<BookResponseBody>> getBooks();
    @GET("/books")
    Call<List<BookResponseBody>> getBooksWithName(
            @Query("name") String name
            );
    @GET("/books/{bookId}/tags")
    Call<List<TagsResponseBody>> getTagsFromBook(@Path("bookId") int bookId);

    @DELETE("/books/{bookId}")
    Call<ResponseBody> deleteBook(@Path("bookId") int bookId);

    @GET("/tags")
    Call<List<TagListResponseBody>> getTags();

    @GET("/books/tags/{tagId}")
    Call<List<BookResponseBody>> getBooksWithTag(@Path("tagId") int tagId);

    @POST("/authors/{authorId}/books")
    Call<BookResponseBody> postBookCreation(
            @Path("authorId") int authorId,
            @Body BookCreateResponseBody bookCreateResponseBody
    );
}
