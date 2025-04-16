package p42.projetabc.author.authorCreation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import p42.projetabc.R;

public class AuthorCreationFragment extends Fragment {

    private AuthorCreationViewModel authorCreationViewModel;

    public AuthorCreationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_author, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authorCreationViewModel = new ViewModelProvider(this).get(AuthorCreationViewModel.class);

        ImageView imageView = view.findViewById(R.id.authorIconCreate);
        imageView.setImageResource(R.drawable.author_pp);
        ImageView btnRetour = view.findViewById(R.id.btnBack);
        btnRetour.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        ImageView imageCreate = view.findViewById(R.id.btnAddAuthor);
        imageCreate.setOnClickListener(v -> {
            EditText firstnameEdit = view.findViewById(R.id.authorFirstnameCreate);
            EditText lastnameEdit = view.findViewById(R.id.authorLastnameCreate);
            String firstnameString = firstnameEdit.getText().toString();
            String lastnameString = lastnameEdit.getText().toString();
            authorCreationViewModel.setFirstname(firstnameString);
            authorCreationViewModel.setLastname(lastnameString);
            if (!firstnameString.isEmpty() && !lastnameString.isEmpty()) {
                authorCreationViewModel.postCreateAuthor().observe(getViewLifecycleOwner(), result -> {
                    Toast.makeText(getContext(), "L'auteur a bien été ajouté dans la base", Toast.LENGTH_LONG).show();
                    Bundle resultBundle = new Bundle();
                    resultBundle.putBoolean("authorCreated", true);
                    requireActivity().getSupportFragmentManager().setFragmentResult("authorCreated", resultBundle);
                    getParentFragmentManager().beginTransaction().remove(this).commit();
                });
            }
        });


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
    }

}