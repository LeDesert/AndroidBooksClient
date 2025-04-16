package p42.projetabc.book.bookAlone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import p42.projetabc.R;
import p42.projetabc.book.retrofit.TagsResponseBody;

public class OneBookAdapter extends RecyclerView.Adapter<TagsHolder> {

    private List<TagsResponseBody.Tag> tagList = new ArrayList<>(); // Stocker directement les tags

    @NonNull
    @Override
    public TagsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_view_holder, parent, false);
        return new TagsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsHolder holder, int position) {
        TagsResponseBody.Tag tag = tagList.get(position);
        System.out.println("Un TextView a été ajouté nom du tag : " + tag.name + " id = " + tag.id);
        holder.getTextViewTagsName().setText(tag.name);
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    // Mise à jour des données
    public void setTagsData(List<TagsResponseBody> newData) {
        tagList.clear(); // Vider la liste avant d'ajouter les nouveaux tags
        for (TagsResponseBody tagsResponseBody : newData) {
            tagList.addAll(tagsResponseBody.Tags); // Ajouter tous les tags de chaque réponse
        }
        System.out.println("Notify a ete appele avec " + tagList.size() + " tags.");
        notifyDataSetChanged();
    }
}
