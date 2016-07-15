package vishal.alumni;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;


    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myOnClickListener = new MyOnClickListener(this);

        DrawerListView();                    // Left drawer List view
        Drawercallback();                   // on click event on list view on navigation drawer

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i]
            ));
        }

        removedItems = new ArrayList<Integer>();

        adapter = new Card_custom_adapter(data);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //--------------------------Floating action button--------------------------------

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.button_action);


        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        //---------icons for submenu buttons ------------------

        ImageView registerImage = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.button_sub_action_dark);



        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton button1 = itemBuilder.setContentView(registerImage).build();


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .attachTo(actionButton)
                .build();



        //----------------on click listener for subaction button-------------

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);

            }
        });



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
                    Toast.makeText(MainActivity.this, "Right Drawer - Why Join CKP Alumni", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_upcoming_events) {
                    Toast.makeText(MainActivity.this, "Right Drawer - Upcoming Events", Toast.LENGTH_SHORT).show();
                }else if (id == R.id.nav_subscribe) {
                    Toast.makeText(MainActivity.this, "Right Drawer - Subscribtion", Toast.LENGTH_SHORT).show();
                }
                drawer.closeDrawer(GravityCompat.END); /*Important Line*/
                return true;
            }
        });


    // get the listview
        expListView = (ExpandableListView) findViewById(R.id.Exp);

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
                 Toast.makeText(getApplicationContext(),
                 "Group Clicked " + listDataHeader.get(groupPosition),
                 Toast.LENGTH_SHORT).show();
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_openright) {
            drawer.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

                                                /*  Expandable list view with drawer      */

    /*
     * Preparing the list data (Expandable List view (Left Drawer))
     */
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

    /*----------------------  Exapndable List View With Drawer Over -----------------------   */


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
        list.setAdapter(adapter);

        ListView list2 = (ListView) findViewById(R.id.list2);
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
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(myIntent);
                }
                if (position == 1) {
                    //code specific to second list item
                    Intent myIntent5 = new Intent(MainActivity.this, Registration.class);
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
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(MainActivity.this, Contact.class);
                    startActivity(myIntent);
                }
                if (position == 3) {
                    //code specific to second list item
                       Intent myIntent3 = new Intent(MainActivity.this, Registration.class);
                       startActivity(myIntent3);
                }
            }
        });



    }

    /* -------------------------- Left Drawer List view over   ----------------*/



    /*   Back Pressed()  on double back exit application  */
    private static long back_pressed;
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


}

