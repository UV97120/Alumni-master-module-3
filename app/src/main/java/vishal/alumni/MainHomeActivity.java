package vishal.alumni;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import vishal.alumni.adapter.UpcomingEventsAdapter;
import vishal.alumni.model.UpcomingEvents;
import vishal.alumni.xmlParser.XMLParser_Upcoming_Events;

public class MainHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);


        recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String url = "http://jarvismedia.tech/mayankwa/alumni/xml/upcoming_events.xml";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onResponse(String response) {
                //response = response.replaceAll("(?s)<!--.*?-->", "").trim();
                //response = response.replaceAll("(?s)ï»¿<?.*?>", "").trim();
                response = response.trim();
                XMLParser_Upcoming_Events parser = new XMLParser_Upcoming_Events();
                ArrayList<UpcomingEvents> list = parser.parse(response);
                Log.d("Log", list.size()+"");
                UpcomingEventsAdapter adapter = new UpcomingEventsAdapter(list, getBaseContext());
                recyclerView.setAdapter(adapter);
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}
