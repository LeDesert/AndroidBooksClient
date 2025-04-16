package p42.projetabc.book.bookAlone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import p42.projetabc.R;
import p42.projetabc.databinding.FragmentBookBinding;
import p42.projetabc.book.retrofit.BookRepository;
import p42.projetabc.book.retrofit.BookResponseBody;

public class OneBookFragment extends Fragment {

    private BookResponseBody theBook;
    private OneBookAdapter oneBookAdapter;
    private OneBookViewModel oneBookViewModel;
    private FragmentBookBinding binding;
    public OneBookFragment(BookResponseBody aBook) {
        theBook=aBook;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one_book, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
        if(theBook!=null)
        {
            TextView bookName = view.findViewById(R.id.authorFirstname);
            TextView authorName = view.findViewById(R.id.authorLastname);
            TextView releaseDate = view.findViewById(R.id.DateSortie);
            ImageView bookIcon =view.findViewById(R.id.authorIcon);
            bookName.setText(theBook.name);
            authorName.setText(theBook.author.lastname);
            bookIcon.setImageResource(R.drawable.imagelivre2);
            if (theBook.publicationYear != 0) {
                releaseDate.setText(String.valueOf(theBook.publicationYear));
            } else {
                releaseDate.setText("Unknown");
            }
            ImageView goBack = view.findViewById(R.id.btnBack);
            goBack.setOnClickListener(v -> {
                getParentFragmentManager().beginTransaction().remove(this).commit();
            });
            //GESTION DE LA DELETION
            ImageView deleteBin = view.findViewById(R.id.btnRedBin);
            deleteBin.setOnClickListener(v -> {
                BookRepository bkCaller = new BookRepository();
                bkCaller.deleteBook(theBook.id);
                Bundle result = new Bundle();
                result.putBoolean("bookDeleted", true);
                System.out.println("Book deleter true");
                requireActivity().getSupportFragmentManager().setFragmentResult("deleteBookRequest", result);
                requireActivity().getSupportFragmentManager().popBackStack();
            });


            //APPELLE RETROFIT SUR LES TAGS
            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBooks);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            oneBookViewModel = new ViewModelProvider(this).get(OneBookViewModel.class);
            oneBookViewModel.setBookId(theBook.id);
            if (oneBookAdapter == null) {
                oneBookAdapter = new OneBookAdapter();
            }
            recyclerView.setAdapter(oneBookAdapter);

            oneBookViewModel.getTagsResponses().observe(getViewLifecycleOwner(), tagsResponse -> {
                if(tagsResponse!=null)oneBookAdapter.setTagsData(tagsResponse);
            });

        }
    }
}