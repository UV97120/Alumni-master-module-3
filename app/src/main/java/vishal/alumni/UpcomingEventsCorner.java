package vishal.alumni;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Created by Razin on 7/6/2016.
 */
public class UpcomingEventsCorner extends Fragment {

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notice_corner, container, false);

        final RecyclerView rvn = (RecyclerView) view.findViewById(R.id.rvn);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        rvn.setLayoutManager(lm);

        String url = "http://jarvismedia.tech/mayankwa/alumni/xml/upcoming_events.xml";

        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onResponse(String response) {
                response = response.replaceAll("(?s)<!--.*?-->", "").trim();
                response = response.replaceAll("(?s)ï»¿<?.*?>", "").trim();
                XMLParser_Upcoming_Events parser = new XMLParser_Upcoming_Events();
                ArrayList<UpcomingEvents> list = parser.parse(response);
                UpcomingEventsAdapter adapter = new UpcomingEventsAdapter(list, getContext());
                rvn.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        return view;
    }
}
