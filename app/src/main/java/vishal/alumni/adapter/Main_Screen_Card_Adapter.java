package vishal.alumni.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vishal.alumni.R;

/**
 * Created by razintailor on 10/07/16.
 */

public class Main_Screen_Card_Adapter extends RecyclerView.Adapter<Main_Screen_Card_Adapter.MyViewHolder>  implements RecyclerView.OnClickListener {

    private LayoutInflater inflater;
    private ArrayList<String>  Title;
    private ArrayList<String>  User;
    private ArrayList<String>  Timestamp;
    private ArrayList<String>  Content;
    private ArrayList<Bitmap> Image ;
    private ArrayList<String>  Tag;
    private ArrayList<String>  Branch;
    Context context;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.main_home_card_layout, parent, false);
        MyViewHolder myviewholder = new MyViewHolder(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TextView title = (TextView) holder.view.findViewById(R.id.textTitle);
        TextView content = (TextView) holder.view.findViewById(R.id.textViewContent);
        TextView timestamp = (TextView) holder.view.findViewById(R.id.textViewTimeStamp);
        TextView username = (TextView) holder.view.findViewById(R.id.textViewUserName);
        ImageView image = (ImageView)holder.view.findViewById(R.id.imageViewContent);
        TextView tag = (TextView) holder.view.findViewById(R.id.Tag);
        TextView branch = (TextView) holder.view.findViewById(R.id.Branch);

        title.setText(Title.get(position));
        content.setText(Content.get(position));
        timestamp.setText(Timestamp.get(position));
        username.setText(User.get(position));
        image.setImageBitmap(Image.get(position));
        tag.setText(Tag.get(position));
        branch.setText(Branch.get(position));

        holder.view.setOnClickListener(this);
        holder.view.setTag(position);

    }

    public Main_Screen_Card_Adapter(Context context, ArrayList<String> title, ArrayList<String> user, ArrayList<String> timestamp, ArrayList<String> content, ArrayList<Bitmap> image, ArrayList<String> tag, ArrayList<String> branch) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        Title = title;
        User = user;
        Timestamp = timestamp;
        Content = content;
        Image = image;
        Tag = tag;
        Branch = branch;
    }

    @Override
    public int getItemCount() {

        return Title.size();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context,v.getTag()+ " Touched", Toast.LENGTH_LONG).show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        View view;
        public MyViewHolder(View itemView) {
            super(itemView);

            view = itemView;
        }
    }
}
