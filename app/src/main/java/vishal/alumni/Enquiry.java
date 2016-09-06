package vishal.alumni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Enquiry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry2);
        EditText editText1 = (EditText)findViewById(R.id.FirstName);

        EditText editText2 = (EditText)findViewById(R.id.LastName);
        EditText editText3 = (EditText)findViewById(R.id.userEmailId);
        EditText editText4 = (EditText)findViewById(R.id.mobileNumber);
        EditText editText5 = (EditText)findViewById(R.id.multi);
    }
}
