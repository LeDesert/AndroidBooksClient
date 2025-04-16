package p42.projetabc.author;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import p42.projetabc.R;
import p42.projetabc.databinding.FragmentAuthorBinding;
import p42.projetabc.author.authorCreation.AuthorCreationFragment;

public class AuthorFragment extends Fragment {

    private FragmentAuthorBinding binding;
    private AuthorViewModel authorViewModel;
    private AuthorAdapter authorAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAuthorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fabAddAuthor = view.findViewById(R.id.fabAddAuthor);

        fabAddAuthor.setOnClickListener(v -> {
            AuthorCreationFragment authorCreationFragment = new AuthorCreationFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, authorCreationFragment)
                    .addToBackStack(null)
                    .commit();
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAuthor);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));


        authorAdapter = new AuthorAdapter();
        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);

        authorViewModel.getAuthorResponses().observe(getViewLifecycleOwner(), authorResponses -> {
            authorAdapter.setAuthorsData(authorResponses);
        });

        recyclerView.setAdapter(authorAdapter);

        ImageView boutonSearch = view.findViewById(R.id.magnifyingGlass);
        boutonSearch.setOnClickListener(v -> {
            EditText searchTextObject = view.findViewById(R.id.searchBar);
            String searchText = searchTextObject.getText().toString();
            authorViewModel.getAuthorFilteredByLastname(searchText)
                    .observe(getViewLifecycleOwner(),data ->{
                        authorAdapter.setAuthorsData(data);
                    });
        });

        requireActivity().getSupportFragmentManager().setFragmentResultListener("authorDeleted", this, (requestKey, bundle) -> {
            boolean isDeleted = bundle.getBoolean("authorDeleted", false);
            if (isDeleted) {
                Log.d("AuthorFragment", "Un auteur a été supprimé. Rafraîchissement de la liste.");
                Toast.makeText(getContext(), "L'auteur a bien été supprimé", Toast.LENGTH_LONG).show();
                authorViewModel.loadAuthors();
                // Rafraîchir la liste des auteurs
                authorViewModel.getAuthorResponses().observe(getViewLifecycleOwner(), authorResponses -> {
                    authorAdapter.setAuthorsData(authorResponses);
                    authorAdapter.notifyDataSetChanged(); // Assure-toi de notifier l'adaptateur
                });
            }
        });
        requireActivity().getSupportFragmentManager().setFragmentResultListener("authorCreated", this, (requestKey, bundle) -> {
            boolean isCreated = bundle.getBoolean("authorCreated", false);
            if (isCreated) {
                Log.d("AuthorFragment", "Un auteur a été créé. Rafraîchissement de la liste.");
                Toast.makeText(getContext(), "L'auteur a bien été ajouté dans la base", Toast.LENGTH_LONG).show();

                authorViewModel.loadAuthors();
                // Rafraîchir la liste des auteurs
                authorViewModel.getAuthorResponses().observe(getViewLifecycleOwner(), authorResponses -> {
                    authorAdapter.setAuthorsData(authorResponses);
                    authorAdapter.notifyDataSetChanged();
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}