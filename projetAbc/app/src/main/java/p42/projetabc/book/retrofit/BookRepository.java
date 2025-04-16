package p42.projetabc.book.retrofit;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import okhttp3.ResponseBody;
import p42.projetabc.book.BookAdapter;
import p42.projetabc.book.bookCreation.BookCreateResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {

    private BookAPI bookApi;
    private List<BookResponseBody> theReponse;

    private BookAdapter adapterParent;

    public BookRepository()
    {
        //adapterParent = parent;
        //Pour auguste


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bookApi = retrofit.create(BookAPI.class);
        /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bookApi = retrofit.create(BookAPI.class);
        */
        //Pour le générique
    }

    public void getAllBooks(MutableLiveData<List<BookResponseBody>> booksResponses)
    {
        System.out.println("Appel");
        Call<List<BookResponseBody>> call = bookApi.getBooks();
        call.enqueue(new Callback<List<BookResponseBody>>() {
            @Override
            public void onResponse(Call<List<BookResponseBody>> call, Response<List<BookResponseBody>> response) {
                if (response.isSuccessful()) {
                    List<BookResponseBody> bookResponseBody = response.body();
                    if (bookResponseBody != null) {
                        theReponse= bookResponseBody;
                        //adapterParent.setBookData(theReponse);
                        booksResponses.setValue(theReponse);
                    }
                }
            }


            @Override
            public void onFailure(Call<List<BookResponseBody>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void getAllBooksWithName(String name, MutableLiveData<List<BookResponseBody>> booksResponses)
    {
        System.out.println("getAllBooksWithName appelé");
        Call<List<BookResponseBody>> call = bookApi.getBooksWithName(name);
        call.enqueue(new Callback<List<BookResponseBody>>() {
            @Override
            public void onResponse(Call<List<BookResponseBody>> call, Response<List<BookResponseBody>> response) {
                if (response.isSuccessful()) {
                    List<BookResponseBody> bookResponseBody = response.body();
                    if (bookResponseBody != null) {
                        System.out.println("getAllBooksWithName recu");
                        theReponse= bookResponseBody;
                        //adapterParent.setBookData(theReponse);
                        booksResponses.setValue(theReponse);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<BookResponseBody>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getTagsFromBook(int bookId, MutableLiveData<List<TagsResponseBody>> tagsResponse)
    {
        System.out.println("getAllBooksWithName appelé avec l'id "+bookId);
        Call<List<TagsResponseBody>> call = bookApi.getTagsFromBook(bookId);
        call.enqueue(new Callback<List<TagsResponseBody>>() {
            @Override
            public void onResponse(Call<List<TagsResponseBody>> call, Response<List<TagsResponseBody>> response) {
                if (response.isSuccessful()) {
                    List<TagsResponseBody> tagResponse = response.body();
                    if (tagResponse != null) {

                        //adapterParent.setBookData(theReponse);
                        tagsResponse.setValue(tagResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TagsResponseBody>> call, Throwable t) {
                t.printStackTrace(); // Affiche la stack trace de l'erreur
                System.out.println("Request failed: " + t.getMessage());
            }
        });
    }
    public void deleteBook(int bookId)
    {
        System.out.println("deleteBooks appelé avec l'id "+bookId);
        Call<ResponseBody> call = bookApi.deleteBook(bookId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                        ResponseBody tagResponse = response.body();
                    }
                }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace(); // Affiche la stack trace de l'erreur
                System.out.println("Request failed: " + t.getMessage());
            }
        });
    }
    public void getAllTags(MutableLiveData<List<TagListResponseBody>> tagListResponse)
    {
        Call<List<TagListResponseBody>> call = bookApi.getTags();
        call.enqueue(new Callback<List<TagListResponseBody>>() {
            @Override
            public void onResponse(Call<List<TagListResponseBody>> call, Response<List<TagListResponseBody>> response) {
                if (response.isSuccessful()) {
                    List<TagListResponseBody> tagList = response.body();
                    if (tagList != null) {
                        //adapterParent.setBookData(theReponse);
                        tagListResponse.setValue(tagList);
                    }
                }
            }


            @Override
            public void onFailure(Call<List<TagListResponseBody>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void getAllBooksWithTag(int tagId, MutableLiveData<List<BookResponseBody>> booksResponses)
    {
        System.out.println("getAllBooksWithName appelé");
        Call<List<BookResponseBody>> call = bookApi.getBooksWithTag(tagId);
        call.enqueue(new Callback<List<BookResponseBody>>() {
            @Override
            public void onResponse(Call<List<BookResponseBody>> call, Response<List<BookResponseBody>> response) {
                if (response.isSuccessful()) {
                    List<BookResponseBody> bookResponseBody = response.body();
                    if (bookResponseBody != null) {
                        System.out.println("getAllBooksWithTags recu");
                        theReponse= bookResponseBody;
                        //adapterParent.setBookData(theReponse);
                        booksResponses.setValue(theReponse);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<BookResponseBody>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void postCreateBookCaller(int authorId, BookCreateResponseBody bookCreateResponseBody){
        System.out.println("La methode a été appelé");
        Call<BookResponseBody> call = bookApi.postBookCreation(authorId, bookCreateResponseBody);
        call.enqueue(new Callback<BookResponseBody>() {
            @Override
            public void onResponse(Call<BookResponseBody> call, Response<BookResponseBody> response) {
                if(response.isSuccessful()){
                    System.out.println("Réponse recu");
                    response.body();
                }
            }

            @Override
            public void onFailure(Call<BookResponseBody> call, Throwable t) {

            }
        });
    }
}