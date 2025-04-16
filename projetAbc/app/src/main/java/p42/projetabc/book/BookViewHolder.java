package p42.projetabc.book;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import p42.projetabc.R;

public class BookViewHolder extends RecyclerView.ViewHolder{
    private TextView _textViewBookName;
    private TextView _textViewAuthorName;

    private ImageView _imageBook;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        _textViewBookName = itemView.findViewById(R.id.txtBookName);
        _textViewAuthorName = itemView.findViewById(R.id.txtAuthorName);
        _imageBook = itemView.findViewById(R.id.imagePicture);
    }

    public TextView getTextViewBookName(){
        return _textViewBookName;
    }

    public TextView getTextViewAuthor(){
        return _textViewAuthorName;
    }
    public ImageView getImageBook(){
        return _imageBook;
    }

}
