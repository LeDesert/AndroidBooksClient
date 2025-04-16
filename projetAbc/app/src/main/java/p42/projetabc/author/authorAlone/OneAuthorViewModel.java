package p42.projetabc.author.authorAlone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import p42.projetabc.author.AuthorViewModel;
import p42.projetabc.author.retrofit.AuthorRepository;
import p42.projetabc.author.retrofit.AuthorResponseBody;
import p42.projetabc.book.retrofit.BookResponseBody;

public class OneAuthorViewModel extends ViewModel {
    private final MutableLiveData<List<BookResponseBody>> booksResponses;
    private final AuthorRepository authorRepository;
    private final MutableLiveData<List<AuthorResponseBody>> authorResponse;
    private final AuthorViewModel authorViewModel;

    private final int authorID;
    public OneAuthorViewModel(int authorId) {
        booksResponses = new MutableLiveData<>();
        authorRepository = new AuthorRepository();
        this.authorResponse = new MutableLiveData<>();
        this.authorID=authorId;
        authorRepository.getBookByAuthorId(booksResponses,authorId);
        authorViewModel = new AuthorViewModel();
    }

    public LiveData<List<BookResponseBody>> getBooksResponses(){
        return booksResponses;
    }

    public void getAuthorFiltered(String query) {
        authorRepository.getAuthorByLastname(authorResponse,query);
    }

    public void deleteAuthor(int authorId) {
        authorViewModel.deleteAuthor(authorId);
    }
}
