package p42.projetabc.author.retrofit;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import p42.projetabc.author.authorCreation.AuthorCreateResponseBody;
import p42.projetabc.book.retrofit.BookResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorRepository {

    private AuthorApi authorApi;
    public AuthorRepository(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        authorApi = retrofit.create(AuthorApi.class);
    }

    public void getAllAuthors(MutableLiveData<List<AuthorResponseBody>> authorsResponses){
        Call<List<AuthorResponseBody>> call = authorApi.getAuthors();
        call.enqueue(new Callback<List<AuthorResponseBody>>() {
            @Override
            public void onResponse(Call<List<AuthorResponseBody>> call, Response<List<AuthorResponseBody>> response) {
                if(response.isSuccessful()){
                    List<AuthorResponseBody> authorResponseBody = response.body();
                    if(authorResponseBody != null){
                        authorsResponses.setValue(authorResponseBody);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AuthorResponseBody>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getBookByAuthorId(MutableLiveData<List<BookResponseBody>> bookResponses, int authorId){
        Call<List<BookResponseBody>> call = authorApi.getBooksByAuthor(authorId);
        call.enqueue(new Callback<List<BookResponseBody>>() {
            @Override
            public void onResponse(Call<List<BookResponseBody>> call, Response<List<BookResponseBody>> response) {
                if(response.isSuccessful()){
                    List<BookResponseBody> bookResponseBody = response.body();
                    if(bookResponseBody !=null){
                        bookResponses.setValue(bookResponseBody);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookResponseBody>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getAuthorByLastname(MutableLiveData<List<AuthorResponseBody>> authorResponse, String query){
        Call<List<AuthorResponseBody>> call = authorApi.getAuthorsWithName(query);
        call.enqueue(new Callback<List<AuthorResponseBody>>() {
            @Override
            public void onResponse(Call<List<AuthorResponseBody>> call, Response<List<AuthorResponseBody>> response) {
                if(response.isSuccessful()){
                    List<AuthorResponseBody> authorRep = response.body();
                    if(authorRep!=null){
                        authorResponse.setValue(authorRep);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AuthorResponseBody>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void postCreateAuthorCaller(MutableLiveData<AuthorCreateResponseBody> authorCreateRepositoryMutableLiveData, AuthorCreateResponseBody authorCreateResponseBody){
        Call<AuthorCreateResponseBody> call = authorApi.postAuthorCreation(authorCreateResponseBody);
        call.enqueue(new Callback<AuthorCreateResponseBody>() {
            @Override
            public void onResponse(Call<AuthorCreateResponseBody> call, Response<AuthorCreateResponseBody> response) {
                if(response.isSuccessful()){
                    AuthorCreateResponseBody authorRep = response.body();
                    if(authorRep!=null){
                        authorCreateRepositoryMutableLiveData.setValue(authorRep);
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthorCreateResponseBody> call, Throwable t) {

            }
        });
    }

    public void deleteAuthor(int authorId){
        Call<Void> call = authorApi.deleteAuthor(authorId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("AuthorCaller", "Auteur bien supprimé");
                }else{
                    Log.d("AuthorCaller", "Erreur lors de la suppression de l'auteur");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
