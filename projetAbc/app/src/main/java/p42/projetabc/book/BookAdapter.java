package p42.projetabc.book;

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
import p42.projetabc.book.bookAlone.OneBookFragment;
import p42.projetabc.book.retrofit.BookResponseBody;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private List<BookResponseBody> bkResponse = new ArrayList<>();

    public BookAdapter() {
        // Initialisation de l'appel réseau dans le constructeur
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_view_holder, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookResponseBody book = bkResponse.get(position);

        // Remplissage des données dans le ViewHolder
        if (book.author != null) {
            holder.getTextViewAuthor().setText(book.author.lastname);
        } else {
            holder.getTextViewAuthor().setText("");
        }
        holder.getTextViewBookName().setText(book.name);
        holder.getImageBook().setImageResource(R.drawable.imagelivre2);
            holder.itemView.setOnClickListener(v -> {
                OneBookFragment oneBookFragment = new OneBookFragment(book);
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, oneBookFragment)
                        .addToBackStack(null)
                        .commit();
            });
    }

    @Override
    public int getItemCount() {
        return bkResponse.size();
    }

    // Mise à jour des données lorsque l'appel réseau réussit
    public void setBookData(List<BookResponseBody> newData) {
        this.bkResponse = newData;
        notifyDataSetChanged();
    }

}
