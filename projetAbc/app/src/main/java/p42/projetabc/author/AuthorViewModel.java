package p42.projetabc.author;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import p42.projetabc.author.retrofit.AuthorRepository;
import p42.projetabc.author.retrofit.AuthorResponseBody;

public class AuthorViewModel extends ViewModel {


    private MutableLiveData<List<AuthorResponseBody>> authorResponses;

    private final AuthorRepository authorRepository;

    public AuthorViewModel() {
        authorRepository = new AuthorRepository();
        authorResponses = new MutableLiveData<>();

        authorRepository.getAllAuthors(authorResponses);
    }

    public void loadAuthors(){
        authorRepository.getAllAuthors(authorResponses);
    }

    public LiveData<List<AuthorResponseBody>> getAuthorResponses() {
        return authorResponses;
    }

    public LiveData<List<AuthorResponseBody>> getAuthorFilteredByLastname(String query){
        authorRepository.getAuthorByLastname(authorResponses,query);
        return authorResponses;
    }


    public void deleteAuthor(int authorId) {
        authorRepository.deleteAuthor(authorId);
    }

}