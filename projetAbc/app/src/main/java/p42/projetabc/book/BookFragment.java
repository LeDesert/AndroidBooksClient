package p42.projetabc.book;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import p42.projetabc.R;
import p42.projetabc.databinding.FragmentBookBinding;
import p42.projetabc.book.bookCreation.BookCreationFragment;

public class BookFragment extends Fragment {

    private BookAdapter bookAdapter;
    private BookViewModel bookViewModel;
    private TagViewModel tagsViewModel;
    private FragmentBookBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BookViewModel bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fabAddAuthor = view.findViewById(R.id.fabAddBook);

        fabAddAuthor.setOnClickListener(v -> {
            BookCreationFragment bookCreationFragment = new BookCreationFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, bookCreationFragment)
                    .addToBackStack(null)
                    .commit();
        });
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        //Mise en place de tous les tags
        tagsViewModel = new ViewModelProvider(this).get(TagViewModel.class);

        tagsViewModel.getTheListOfTags().observe(getViewLifecycleOwner(), listOfTags -> {
            LinearLayout layoutTagsContainer = view.findViewById(R.id.linearLayoutScrollViewContainer);
            layoutTagsContainer.removeAllViews();

            for (int i = -1; i < listOfTags.size(); i++) {
                Button tagButton = new Button(requireContext());
                if (i == -1) {
                    tagButton.setText("Tout");
                    tagButton.setOnClickListener(v -> {
                        System.out.println("Tout est appelé");
                        bookViewModel.getAllBooks();
                    });
                } else {
                    tagButton.setText(listOfTags.get(i).name);
                    int finalI = i;
                    tagButton.setOnClickListener(v -> {
                        System.out.println(listOfTags.get(finalI).name + " est appelé");
                        bookViewModel.getBookHavingTag(listOfTags.get(finalI).id);
                    });
                }
                tagButton.setTextSize(18);
                tagButton.setPadding(20, 5, 20, 5);
                tagButton.setBackgroundResource(R.drawable.border2);
                tagButton.setTypeface(getResources().getFont(R.font.comic_shanns));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 13, 0);
                tagButton.setLayoutParams(params);
                layoutTagsContainer.addView(tagButton);
            }
        });

        //Fin mise en place tags


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBook);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        if (bookAdapter == null) {
            bookAdapter = new BookAdapter();
        }

        bookViewModel.getBooksResponses().observe(getViewLifecycleOwner(), booksResponses -> {
            bookAdapter.setBookData(booksResponses);
        });

        recyclerView.setAdapter(bookAdapter);
        ImageView magnifyingGlass = view.findViewById(R.id.magnifyingGlass);
        EditText searchBar = view.findViewById(R.id.searchBar);
        magnifyingGlass.setOnClickListener(v -> {
            String query = searchBar.getText().toString();
            if (bookAdapter != null) {
                bookViewModel.getBookContaining(query).observe(getViewLifecycleOwner(), booksResponses -> {
                    bookAdapter.setBookData(booksResponses);
                });
            }
        });

        requireActivity().getSupportFragmentManager().setFragmentResultListener("deleteBookRequest", this, (requestKey, bundle) -> {
            boolean isBookDeleted = bundle.getBoolean("bookDeleted", false);
            if (isBookDeleted) {
                Log.d("BooksFragment", "Un livre a été supprimé. Rafraîchissement de la liste.");
                Toast.makeText(getContext(), "Le livre a bien été supprimé de la base", Toast.LENGTH_LONG).show();
                bookViewModel.getAllBooks();
                // Recharge la liste des livres dans ton ViewModel pour refléter la suppression
                bookViewModel.getBooksResponses().observe(getViewLifecycleOwner(), booksResponses -> {
                    bookAdapter.setBookData(booksResponses);  // Rafraîchit l'affichage
                    bookAdapter.notifyDataSetChanged();
                });
            }
        });

        requireActivity().getSupportFragmentManager().setFragmentResultListener("bookCreated", this, (requestKey, bundle) -> {
            boolean isBookCreated = bundle.getBoolean("bookCreated", false);
            if (isBookCreated) {
                Log.d("BooksFragment", "Un livre a été créé. Rafraîchissement de la liste.");
                Toast.makeText(getContext(), "Le livre a bien été ajouté dans la base", Toast.LENGTH_LONG).show();
                bookViewModel.getAllBooks();
            }
        });

        bookViewModel.getBooksResponses().observe(getViewLifecycleOwner(), booksResponses -> {
            bookAdapter.setBookData(booksResponses);  // Rafraîchit l'affichage
            bookAdapter.notifyDataSetChanged();
        });


        //DELETION REACTION
/*        requireActivity().getSupportFragmentManager().setFragmentResultListener("deleteBookRequest", this, (requestKey, bundle) -> {
            boolean bookDeleted = bundle.getBoolean("bookDeleted", false);
            if (bookDeleted) {
                System.out.println("Book deleter has been called!");
                String query = searchBar.getText().toString();
                if (bookAdapter != null) {
                    try {
                        Thread.sleep(100); // Pause de 100 ms pour laisser le temps a la requete retrofit de bien supprimer
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bookViewModel.getBookContaining(query).observe(getViewLifecycleOwner(), booksResponses -> {
                        bookAdapter.setBookData(booksResponses);
                    });
                }
            }
        });*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume est appelé");
        bookViewModel.getBooksResponses();
    }

    public BookAdapter getBookAdapter() {
        return bookAdapter;
    }
}
