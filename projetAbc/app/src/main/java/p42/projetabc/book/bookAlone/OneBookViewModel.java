package p42.projetabc.book.bookAlone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import p42.projetabc.book.retrofit.BookRepository;
import p42.projetabc.book.retrofit.TagsResponseBody;

public class OneBookViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<TagsResponseBody>> tagsResponse;
    private final BookRepository bookRepository;

    public OneBookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
        bookRepository = new BookRepository();
        tagsResponse = new MutableLiveData<>();
    }
    public void setBookId(int number)
    {
        bookRepository.getTagsFromBook(number, tagsResponse);
    }
    public LiveData<List<TagsResponseBody>> getTagsResponses(){
        return tagsResponse;
    }




    public LiveData<String> getText() {
        return mText;
    }
}