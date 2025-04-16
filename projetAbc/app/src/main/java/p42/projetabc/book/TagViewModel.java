package p42.projetabc.book;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import p42.projetabc.book.retrofit.BookRepository;
import p42.projetabc.book.retrofit.TagListResponseBody;

public class TagViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<TagListResponseBody>> tagListResponse;
    private final BookRepository bookRepository;

    public TagViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
        bookRepository = new BookRepository();
        tagListResponse = new MutableLiveData<>();

        bookRepository.getAllTags(tagListResponse);
    }
    public LiveData<List<TagListResponseBody>> getTheListOfTags(){
        return tagListResponse;
    }
    public LiveData<String> getText() {
        return mText;
    }
}