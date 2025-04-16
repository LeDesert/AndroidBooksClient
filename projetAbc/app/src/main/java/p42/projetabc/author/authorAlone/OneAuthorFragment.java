package p42.projetabc.author.authorAlone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import p42.projetabc.R;
import p42.projetabc.author.retrofit.AuthorResponseBody;
import p42.projetabc.book.BookAdapter;

public class OneAuthorFragment extends Fragment {

    private AuthorResponseBody author;
    private BookAdapter bookAdapter;
    private OneAuthorViewModel oneAuthorViewModel;

    public OneAuthorFragment(AuthorResponseBody authorResponseBody) {
        this.author= authorResponseBody;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_author, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
        this.bookAdapter = new BookAdapter();
        this.oneAuthorViewModel = new OneAuthorViewModel(this.author.id);
        ImageView goBack = view.findViewById(R.id.btnBack);
        ImageView btnRedBin = view.findViewById(R.id.btnRedBin);
        btnRedBin.setOnClickListener(v -> {
            // Appeler la méthode de suppression de l'auteur
            oneAuthorViewModel.deleteAuthor(author.id);
            // Envoyer un résultat de suppression
            Bundle result = new Bundle();
            result.putBoolean("authorDeleted", true);
            requireActivity().getSupportFragmentManager().setFragmentResult("authorDeleted", result);
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        goBack.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });
        // TODO: Faire le bouton corbeille
        TextView authorFirstame = view.findViewById(R.id.authorFirstname);
        TextView authorLastname = view.findViewById(R.id.authorLastname);
        ImageView authorIcon = view.findViewById(R.id.authorIcon);
        authorFirstame.setText(author.firstname);
        authorLastname.setText(author.lastname);
        authorIcon.setImageResource(R.drawable.author_pp);

        //TODO: Faire l'observer pour le recycler view en utilisant les book déjà fait (book holder)

        RecyclerView recyclerViewBook = view.findViewById(R.id.recyclerViewBooks);
        recyclerViewBook.setLayoutManager(new GridLayoutManager(requireContext(),3));


        oneAuthorViewModel.getBooksResponses().observe(getViewLifecycleOwner(), booksResponses -> {
            bookAdapter.setBookData(booksResponses);
        });

        recyclerViewBook.setAdapter(bookAdapter);
    }
}