package vishal.alumni;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> Title;
    ArrayList<String> Content;
    ArrayList<String> Timestamp;
    ArrayList<String> Username;
    ArrayList<Bitmap> Image;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);


        Title.add("Post 1");
        Title.add("Post 2");
        Title.add("Post 3");
        Title.add("Post 4");
        Title.add("Post 5");
        Title.add("Post 6");

        Content.add("Content 1     jsdfahjdf askjdhf alsdhfj dsjhf asdkjfh asdjkfh sdkfjh sadfkljhas dkfjh ");
        Content.add("Content 2     jsdfahjdf askjdhf alsdhfj dsjhf asdkjfh asdjkfh sdkfjh sadfkljhas dkfjh ");
        Content.add("Content 3     jsdfahjdf askjdhf alsdhfj dsjhf asdkjfh asdjkfh sdkfjh sadfkljhas dkfjh ");
        Content.add("Content 4     jsdfahjdf askjdhf alsdhfj dsjhf asdkjfh asdjkfh sdkfjh sadfkljhas dkfjh ");
        Content.add("Content 5     jsdfahjdf askjdhf alsdhfj dsjhf asdkjfh asdjkfh sdkfjh sadfkljhas dkfjh ");
        Content.add("Content 6     jsdfahjdf askjdhf alsdhfj dsjhf asdkjfh asdjkfh sdkfjh sadfkljhas dkfjh ");


        Timestamp.add("TimeStamp 1");
        Timestamp.add("TimeStamp 2");
        Timestamp.add("TimeStamp 3");
        Timestamp.add("TimeStamp 4");
        Timestamp.add("TimeStamp 5");
        Timestamp.add("TimeStamp 6");

        Username.add("User 1");
        Username.add("User 2");
        Username.add("User 3");
        Username.add("User 4");
        Username.add("User 5");
        Username.add("User 6");

        recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        Main_Screen_Card_Adapter main_screen_card_adapter = new Main_Screen_Card_Adapter();




    }
}
