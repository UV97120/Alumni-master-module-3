package vishal.alumni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vishal.alumni.R;
import vishal.alumni.model.UpcomingEvents;


public class UpcomingEventsAdapter extends RecyclerView.Adapter<UpcomingEventsAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<UpcomingEvents> list;
    private Context context;



    public UpcomingEventsAdapter(ArrayList<UpcomingEvents> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_home_card_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView title = (TextView) holder.view.findViewById(R.id.textTitle);
        title.setText(list.get(position).getHeading().trim());
        TextView news = (TextView) holder.view.findViewById(R.id.textViewContent);
        news.setText(list.get(position).getCategory());
        TextView date = (TextView) holder.view.findViewById(R.id.Tag);
        date.setText(list.get(position).getDate());

        //holder.view.setTag(list.get(position).getDate());
        //holder.view.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        //if(list.size() < 20) {
          //  return list.size();
      //  }

        //return 20;
        return list.size();
    }

    @Override
    public void onClick(View view) {

        //  Intent intent = new Intent(context, Webview.class);
//        intent.putExtra("URL", (String) view.getTag());
//        Activity activity = (Activity) context;
//        activity.startActivity(intent);

    }
}
