package vishal.alumni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nononsenseapps.filepicker.FilePickerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class Registration extends Activity implements AdapterView.OnItemSelectedListener {


    String ek;
    boolean flag;
    String filepath;
    ArrayList<Integer> arrlist = new ArrayList<Integer>(80);
    Spinner spinner;
    Button choose;
    TextView loginLink;
    TextView name;
    TextView email;
    Button register;
    String FileName;
    String bs64;//to convert filedata to bs64
    ImageView imgView;
    String newPath;

    private static final int RESULT_LOAD_IMAGE = 1;

    private static final String ServerAddress = "http://www.jarvismedia.tech/mayankwa/alumni/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        int base_year = 1950;

        for (int i = 0; i < 80; i++) {
            int nextYear = 1950;
            nextYear = base_year + i;
            arrlist.add(i, nextYear);
        }

        choose = (Button) findViewById(R.id.btn_file_pick);
        loginLink = (TextView) findViewById(R.id.link_login);
        spinner = (Spinner) findViewById(R.id.spinner);
        name = (TextView) findViewById(R.id.input_name);
        email = (TextView) findViewById(R.id.input_email);
        register = (Button) findViewById(R.id.btn_signup);

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrlist);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(Registration.this);


        //-------------Login Redirect------------------------

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);

            }

        });


        //---------Register ServerSide Logic---------------


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String name = name.getText().toString();
                JSONObject jsonObject = new JSONObject();




                try {
                    InputStream inputStream = new FileInputStream(newPath);

                    byte [] bytes;

                    byte[] buffer = new byte[8192];

                    int bytesRead;

                    ByteArrayOutputStream output = new ByteArrayOutputStream();



                    while((bytesRead = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }



                    bytes = output.toByteArray();
                    bs64 = Base64.encodeToString(bytes, Base64.DEFAULT);

                } catch (FileNotFoundException e) {
                    Log.d("bs64", e.toString());
                    //e.printStackTrace();
                }catch (Exception e){
                    Log.d("bs64_2", e.toString());
                    //e.printStackTrace();
                }



                String url = "http://www.jarvismedia.tech/mayankwa/alumni/json.php?name=" + name.getText().toString() + "&email=" + email.getText().toString() + "&year=" + ek.toString() + "&filename=" + FileName + "&filedata=" + bs64;//"http://192.168.1.6:5000/register";//";
                Log.d("URL", url);

                try {
                    //jsonObject.put("fileData", bs64);

                    jsonObject.put("Name", name.getText().toString());
                    jsonObject.put("email", email.getText().toString());

                    jsonObject.put("year", ek);


                } catch (JSONException e) {
                    e.printStackTrace();
                }




                Log.d("Test", jsonObject.toString());
                Log.d("", jsonObject.toString());


                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(Registration.this, response.toString(), Toast.LENGTH_LONG).show();

                                Log.d("bhosdike", response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Registration.this, error.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
                RequestQueue queue = Volley.newRequestQueue(Registration.this);  // this = context
                if (flag) {
                    queue.add(jsObjRequest);//year selected
                }
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Registration.this, FilePickerActivity.class);
                // This works if you defined the intent filter
                // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

                // Set these depending on your use case. These are the defaults.
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

                // Configure initial directory by specifying a String.
                // You could specify a String like "/storage/emulated/0/", but that can
                // dangerous. Always use Android's API calls to get paths to the SD-card or
                // internal memoryi.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

                startActivityForResult(i, 1);





                //------------Another Logic for file picker--------------

            }
        });
    }

    ;



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ghus benchod", "gaya lode");
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Log.d("if me ghus benchod", "gaya lode");
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                // For JellyBean and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();

                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();
                            // Do something with the URI

                            filepath = uri.toString();

                        }
                    }
                    // For Ice Cream Sandwich
                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra
                            (FilePickerActivity.EXTRA_PATHS);

                    if (paths != null) {
                        for (String path: paths) {
                            Uri uri = Uri.parse(path);
                            // Do something with the URI
                            filepath = uri.toString();
                        }
                    }
                }

            } else {
                Uri uri = data.getData();
                // Do something with the URI

                filepath = uri.toString();
            }
            Log.d("path dekh benchod", filepath);
        }
        else
        {
            Log.d("path dekh benchod lode", resultCode + "" + requestCode);
        }
        Toast.makeText(Registration.this, filepath.toString(),Toast.LENGTH_LONG).show();

        newPath = filepath.substring(7,filepath.length());

        Log.d("LatestPath" , newPath);

        String[] name = filepath.split("/");

        String fileNameTemp = name[(name.length)-1];
        FileName = fileNameTemp;





    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView mytext = (TextView) view;

        // ek = mytext.getText().toString();

        flag = true;

        ek = arrlist.get(position).toString();


       /* if(mytext.getText().toString().equals("1950"))
        {

        }
        else
        {
            JSONObject jsonObject = new JSONObject();
            String url = "http://192.168.0.104:5000/register"; //"http://www.jarvismedia.tech/mayankwa/alumni/json.php?name=";

            try {
                jsonObject.put("year",mytext.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();

                Log.d("Test", jsonObject.toString());

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(Registration.this, response.toString(), Toast.LENGTH_LONG).show();


                                JSONObject jsonObjectnew=new JSONObject();
                                if(jsonObjectnew.has("name") && !jsonObjectnew.isNull("name"))
                                {
                                    Toast.makeText(Registration.this, "Success Bitch", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(Registration.this, response.toString(), Toast.LENGTH_LONG).show();
                                }

                                Log.d("bhosdike", response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Registration.this, error.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
                RequestQueue queue = Volley.newRequestQueue(Registration.this);  // this = context

                queue.add(jsObjRequest);

            }

        }*/
        Toast.makeText(Registration.this, "Year Selected is" + mytext.getText(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
