package p42.projetabc.author.authorCreation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import p42.projetabc.author.retrofit.AuthorRepository;

public class AuthorCreationViewModel extends ViewModel {
    private AuthorCreateResponseBody authorCreateResponseBody;
    private MutableLiveData<AuthorCreateResponseBody> authorCreateRepositoryMutableLiveData;
    private AuthorRepository authorRepository;
    public AuthorCreationViewModel(){
        authorCreateResponseBody = new AuthorCreateResponseBody();
        authorRepository = new AuthorRepository();
        authorCreateRepositoryMutableLiveData = new MutableLiveData<>();
    }

    public void setFirstname(String firstname){
        authorCreateResponseBody.firstname = firstname;
    }

    public void setLastname(String lastname){
        authorCreateResponseBody.lastname = lastname;
    }
    public LiveData<AuthorCreateResponseBody> postCreateAuthor(){
        authorRepository.postCreateAuthorCaller(authorCreateRepositoryMutableLiveData, authorCreateResponseBody);
        return authorCreateRepositoryMutableLiveData;
    }
}
