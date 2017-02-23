package ng.codeimpact.firebaseandroid.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ng.codeimpact.firebaseandroid.R;
import ng.codeimpact.firebaseandroid.helper.ChronicaProRegularHelper;
import ng.codeimpact.firebaseandroid.models.List_item;

/**
 * Created by Nsikak  Thompson on 2/15/2017.
 */

public class ListViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;
    Context context;

    public ListViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();

        titleView = (TextView) itemView.findViewById(R.id.list_title);
        //authorView = (TextView) itemView.findViewById(R.id.post_author);
        bodyView = (TextView) itemView.findViewById(R.id.list_body);

        //applying font
        ChronicaProRegularHelper.applyFont(context,titleView);
        ChronicaProRegularHelper.applyFont(context,bodyView);

    }

    public void bindToPost(List_item list, View.OnClickListener starClickListener) {
        titleView.setText(list.title);
        bodyView.setText(list.getBody());
        //authorView.setText(post.author);

        //starView.setOnClickListener(starClickListener);
    }
}

