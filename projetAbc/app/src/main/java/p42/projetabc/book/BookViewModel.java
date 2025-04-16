package p42.projetabc.book;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import p42.projetabc.book.retrofit.BookRepository;
import p42.projetabc.book.retrofit.BookResponseBody;

public class BookViewModel extends ViewModel {

    private MutableLiveData<List<BookResponseBody>> booksResponses;
    private final BookRepository bookRepository;

    public BookViewModel() {
        bookRepository = new BookRepository();
        booksResponses = new MutableLiveData<>();

        bookRepository.getAllBooks(booksResponses);
    }

    public LiveData<List<BookResponseBody>> getBooksResponses(){
        return booksResponses;
    }

    public void getAllBooks()
    {
        bookRepository.getAllBooks(booksResponses);
    }

    //Rajouter l'appel au caller
    public LiveData<List<BookResponseBody>> getBookContaining(String query){
        bookRepository.getAllBooksWithName(query, booksResponses);
        return booksResponses;
    }

    public LiveData<List<BookResponseBody>> getBookHavingTag(int num){
        bookRepository.getAllBooksWithTag(num, booksResponses);
        return booksResponses;
    }
}