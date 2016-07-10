package vishal.alumni;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by razintailor on 10/07/16.
 */

public class Main_Screen_Card_Adapter extends RecyclerView.Adapter<Main_Screen_Card_Adapter.ViewHolder> {

    private ArrayList<String>  Title;
    private ArrayList<String>  User;
    private ArrayList<String>  Timestamp;
    private ArrayList<String>  Content;
    private ArrayList<Bitmap> Image ;



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_home_card_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TextView title = (TextView) holder.view.findViewById(R.id.textTitle);
        TextView content = (TextView) holder.view.findViewById(R.id.textViewContent);
        TextView timestamp = (TextView) holder.view.findViewById(R.id.textViewTimeStamp);
        TextView username = (TextView) holder.view.findViewById(R.id.textViewUserName);
        ImageView image = (ImageView)holder.view.findViewById(R.id.imageViewContent);

        title.setText(Title.get(position));
        content.setText(Content.get(position));
        timestamp.setText(Timestamp.get(position));
        username.setText(User.get(position));
        image.setImageBitmap(Image.get(position));

    }

    public Main_Screen_Card_Adapter(ArrayList<String> title, ArrayList<String> user, ArrayList<String> timestamp, ArrayList<String> content, ArrayList<Bitmap> image) {
        Title = title;
        User = user;
        Timestamp = timestamp;
        Content = content;
        Image = image;
    }

    @Override
    public int getItemCount() {

        return Title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View view;
        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
        }
    }
}
