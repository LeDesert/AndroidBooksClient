package p42.projetabc.author;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import p42.projetabc.R;
import p42.projetabc.author.authorAlone.OneAuthorFragment;
import p42.projetabc.author.retrofit.AuthorResponseBody;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorViewHolder> {

    List<AuthorResponseBody> authorsData = new ArrayList<>();

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_view_holder, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        AuthorResponseBody author = authorsData.get(position);

        holder.getTextViewAuthorLastname().setText(author.lastname);
        holder.getTextViewAuthorFirstname().setText(author.firstname);
        holder.getImageAuthor().setImageResource(R.drawable.author_pp);
        holder.itemView.setOnClickListener(v -> {
            OneAuthorFragment oneAuthorFragment = new OneAuthorFragment(author);
            FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, oneAuthorFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return authorsData.size();
    }
    public void clear() {
        authorsData.clear();
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged") // sert juste à retirer le surlignage jaune de notifyDataSetChanged()
    public void setAuthorsData(List<AuthorResponseBody> authorsData) {
        this.authorsData = authorsData;
        notifyDataSetChanged();
    }
}
