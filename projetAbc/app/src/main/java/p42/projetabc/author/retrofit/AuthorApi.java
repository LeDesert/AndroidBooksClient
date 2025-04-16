package p42.projetabc.author.retrofit;

import java.util.List;

import p42.projetabc.author.authorCreation.AuthorCreateResponseBody;
import p42.projetabc.book.retrofit.BookResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthorApi {
    @GET("/authors")
    Call<List<AuthorResponseBody>> getAuthors();

    @GET("/authors/{authorId}/books")
    Call<List<BookResponseBody>> getBooksByAuthor(@Path("authorId") int authorId);

    @GET("/authors")
    Call<List<AuthorResponseBody>> getAuthorsWithName(
            @Query("lastname") String lastname
    );

    @POST("/authors")
    Call<AuthorCreateResponseBody> postAuthorCreation(
            @Body AuthorCreateResponseBody authorCreateResponseBody
    );

    @DELETE("/authors/{authorId}")
    Call<Void> deleteAuthor(@Path("authorId") int authorId);
}
