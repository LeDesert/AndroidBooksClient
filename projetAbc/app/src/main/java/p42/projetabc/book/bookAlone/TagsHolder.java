package p42.projetabc.book.bookAlone;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import p42.projetabc.R;

public class TagsHolder extends RecyclerView.ViewHolder{
    private TextView _textViewTagsName;

    public TagsHolder(@NonNull View itemView) {
        super(itemView);
        _textViewTagsName = itemView.findViewById(R.id.tagsName);
    }

    public TextView getTextViewTagsName(){
        return _textViewTagsName;
    }
}
