package p42.projetabc.book.bookCreation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import p42.projetabc.R;
import p42.projetabc.author.AuthorViewModel;
import p42.projetabc.author.retrofit.AuthorResponseBody;
import p42.projetabc.book.retrofit.BookRepository;

public class BookCreationFragment extends Fragment {
    public BookCreationFragment() {
        // Required empty public constructor
    }

    public static BookCreationFragment newInstance(String param1, String param2) {
        BookCreationFragment fragment = new BookCreationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_creation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AuthorViewModel authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
        authorViewModel.getAuthorResponses().observe(getViewLifecycleOwner(), authorResponses -> {
            if (authorResponses != null) {
                // Liste pour afficher Prénom + Nom
                List<String> authorNames = new ArrayList<>();
                // HashMap pour stocker les IDs des auteurs
                Map<String, Integer> authorIdMap = new HashMap<>();

                for (AuthorResponseBody author : authorResponses) {
                    String displayName = author.firstname + " " + author.lastname;
                    authorNames.add(displayName);
                    authorIdMap.put(displayName, author.id);
                }

                // Adapter pour le Spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_item, authorNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner spinner = requireView().findViewById(R.id.spinnerAuthor);
                spinner.setAdapter(adapter);

                // Gérer la sélection d'un auteur
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedName = parent.getItemAtPosition(position).toString();
                        int authorId = authorIdMap.get(selectedName);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Rien à faire ici
                    }
                });
                ImageView goBack = view.findViewById(R.id.btnBack);
                goBack.setOnClickListener(v -> {
                    requireActivity().getSupportFragmentManager().popBackStack();
                });

                Button btnCreate = view.findViewById(R.id.btnValider);
                btnCreate.setOnClickListener(v -> {
                    EditText bookName = view.findViewById(R.id.editTextNom);
                    EditText releaseDate = view.findViewById(R.id.editTextDate);
                    String theBookName = bookName.getText().toString();
                    int creationDate=-1;
                    if(!releaseDate.getText().toString().isEmpty()) {
                        creationDate = Integer.parseInt(releaseDate.getText().toString());
                    }
                    System.out.println("creationDate = "+creationDate);
                    //Recuperer l'id dans le hashmap
                    Spinner theSpinner = view.findViewById(R.id.spinnerAuthor);
                    String selectedName = theSpinner.getSelectedItem().toString();
                    int authorId=-1;
                    if (authorIdMap.containsKey(selectedName)) {
                        authorId = authorIdMap.get(selectedName);
                    } else {
                    }
                    if (!theBookName.isEmpty() || authorId!=-1) {
                        //Faire l'observeur
                        BookCreateResponseBody bkRepo = new BookCreateResponseBody();
                        bkRepo.name=theBookName;
                        if(creationDate!=-1)bkRepo.publicationYear=creationDate;
                        BookRepository bkCaller = new BookRepository();
                        bkCaller.postCreateBookCaller(authorId, bkRepo);
                        Bundle result = new Bundle();
                        result.putBoolean("bookCreated", true);
                        requireActivity().getSupportFragmentManager().setFragmentResult("bookCreated", result);
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        });
    }
}