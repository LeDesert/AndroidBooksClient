package p42.projetabc.author;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import p42.projetabc.R;

public class AuthorViewHolder extends RecyclerView.ViewHolder {


    private TextView _textViewAuthorLastname;
    private TextView _textViewAuthorFirstname;
    private ImageView _imageAuthor;

    public AuthorViewHolder(@NonNull View itemView) {
        super(itemView);
        this._imageAuthor = itemView.findViewById(R.id.imagePicture);
        this._textViewAuthorLastname = itemView.findViewById(R.id.txtAuthorName);
        this._textViewAuthorFirstname = itemView.findViewById(R.id.txtAuthorFirstname);
    }

    public TextView getTextViewAuthorLastname(){
        return _textViewAuthorLastname;
    }

    public TextView getTextViewAuthorFirstname(){
        return _textViewAuthorFirstname;
    }

    public ImageView getImageAuthor(){
        return _imageAuthor;
    }

}
