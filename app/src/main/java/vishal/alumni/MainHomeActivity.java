package vishal.alumni;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vishal.alumni.adapter.UpcomingEventsAdapter;
import vishal.alumni.model.DataModel;
import vishal.alumni.model.UpcomingEvents;
import vishal.alumni.xmlParser.XMLParser_Upcoming_Events;

public class MainHomeActivity extends AppCompatActivity {
    static View.OnClickListener myOnClickListener;
    private static RecyclerView.Adapter adapter;
   // private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    private static ArrayList<Integer> removedItems;
    /*   Back Pressed()  on double back exit application  */
    private static long back_pressed;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    DrawerLayout drawer;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        MyOnClickListener myOnClickListener = new MyOnClickListener(this);

        DrawerListView();                    // Left drawer List view
        Drawercallback();                   // on click event on list view on navigation drawer*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*-------------------------  Navigation On Right View------------------------- */

        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.right_view);
        assert rightNavigationView != null;
        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Right navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_why_join_ckp_alumni) {
                    //Intent intent = new Intent(MainActivity.this, RegistrationPhaseTwo.class);
                    //startActivity(intent);
                } else if (id == R.id.nav_upcoming_events) {
                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.main_container, new UpcomingEventsCorner()).commit();

                    //Toast.makeText(MainActivity.this, "Right Drawer - Upcoming Events", Toast.LENGTH_SHORT).show();
                }else if (id == R.id.nav_subscribe) {
                    Toast.makeText(MainHomeActivity.this, "Right Drawer - Subscribtion", Toast.LENGTH_SHORT).show();
                }
                drawer.closeDrawer(GravityCompat.END); /*Important Line*/
                return true;
            }
        });


        // get the listview
         expListView = (ExpandableListView) findViewById(R.id.Exp);

        //removes the standard group state indicator
        assert expListView != null;
        expListView.setGroupIndicator(null);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                if(id == R.id.nav_contact)
                {
                    Toast.makeText(getApplicationContext(),
                            "Group Clicked ",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Group Clicked " + listDataHeader.get(groupPosition),
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });


    }

    /*----------------------  Exapndable List View With Drawer Over -----------------------   */

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        //    listDataHeader.add("Home");
        //    listDataHeader.add("Awards");
        listDataHeader.add("CONNECT ");
        //    listDataHeader.add("Showcase ");
        //    listDataHeader.add("Career ");
        //    listDataHeader.add("Register");
        //    listDataHeader.add("Contact");
        //    listDataHeader.add("Blog");
        //    listDataHeader.add("About Us");

        // Adding child data
        List<String> connect = new ArrayList<String>();
        connect.add("EVENT");
        connect.add("Gallery");
        connect.add("NEWS");
        connect.add("POST");

   /*     List<String> showcase = new ArrayList<String>();
        showcase.add("Student club");
        showcase.add("Magazine");
        showcase.add("Alumni Contribution");
        showcase.add("BLOGS");

        List<String> career = new ArrayList<String>();
        career.add("Internship");
        career.add("JOB");
*/
        listDataChild.put(listDataHeader.get(0), connect);              //(" Connect ") Header, Child data
//        listDataChild.put(listDataHeader.get(3), showcase);
//        listDataChild.put(listDataHeader.get(4), career);
    }


    /* -----------------------------Drawer List view----------------------------  */

    public void DrawerListView() {
        // Create List View
        String[] myItems = {" HOME ", " AWARDS "};
        String[] myItems2 ={" CONTACT ", " BLOG ", " ABOUT US ", " REGISTER "};

        //Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_left_drawer, myItems);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.listview_left_drawer, myItems2);


        // Configure The Listview
        ListView list = (ListView) findViewById(R.id.list);
        assert list != null;
        list.setAdapter(adapter);

        ListView list2 = (ListView) findViewById(R.id.list2);
        assert list2 != null;
        list2.setAdapter(adapter2);
    }

    public void Drawercallback() {
        ListView list = (ListView) findViewById(R.id.list);
        ListView list2 = (ListView) findViewById(R.id.list2);
        assert list != null;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textview = (TextView) viewClicked;
                String message = "Clicked" + position + " name " + textview.getText().toString();
                Toast.makeText(MainHomeActivity.this, message, Toast.LENGTH_LONG).show();
                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(MainHomeActivity.this, MainActivity.class);
                    startActivity(myIntent);
                }
                if (position == 1) {
                    //code specific to second list item
                    Intent myIntent5 = new Intent(MainHomeActivity.this, Registration.class);
                    startActivity(myIntent5);
                }
            }
        });
        assert list2 != null;
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textview = (TextView) viewClicked;
                String message = "Clicked" + position + " name " + textview.getText().toString();
                Toast.makeText(MainHomeActivity.this, message, Toast.LENGTH_LONG).show();
                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(MainHomeActivity.this, MainHomeActivity.class);
                    startActivity(myIntent);
                }
                if (position == 3) {
                    //code specific to second list item
                    Intent myIntent3 = new Intent(MainHomeActivity.this, Registration.class);
                    startActivity(myIntent3);
                }
            }
        });



    }

    /* -------------------------- Left Drawer List view over   ----------------*/

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if(back_pressed + 2000>System.currentTimeMillis()) super.onBackPressed();
            else
                Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
            //super.onBackPressed();
            //System.exit(0);
        }
    }

    /* -----------------------On Click Card View------------------------------*/
    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, CardViewContent.class);
            context.startActivity(intent);

        }


    }


}




/*
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
}*/
