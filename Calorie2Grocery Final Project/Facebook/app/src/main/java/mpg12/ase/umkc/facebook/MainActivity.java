package mpg12.ase.umkc.facebook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static String NAME = "";
    private LoginButton loginBtn;
    // private TextView uname;

    private UiLifecycleHelper uiHelper;
    Button login, register;
    EditText user_name, user_pwd;
    String username, userpwd;
    Context ctx = this;

    public EditText u_name;
    public EditText p_swd;
    public String uname;
    public String pswd;
    //public  String output_msg;
    public String out_msg;

    public String add_status_msg = "Success";
    private static byte[] buff = new byte[1024];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*login = (Button) findViewById(R.id.button);
        register = (Button) findViewById(R.id.button2);
        user_name = (EditText) findViewById(R.id.editText);
        user_pwd = (EditText) findViewById(R.id.editText2);
        register.setOnClickListener(this);

        login.setOnClickListener(this);*/
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        //uiHelper.onCreate(savedInstanceState);


        //uname = (TextView) findViewById(R.id.uname);

        loginBtn = (LoginButton) findViewById(R.id.authButton);
        loginBtn.setReadPermissions(Arrays.asList("email"));
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        /* Button btnsearch = (Button) findViewById(R.id.btnsrch);
        btnsearch.setOnClickListener(this); */

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

      /*  loginBtn.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {

                if (user != null) {
                    Intent i = new Intent(MainActivity.this, Home.class);
                    startActivity(i);

                }
            }
        });*/
        u_name = (EditText) findViewById(R.id.editText);
        p_swd = (EditText) findViewById(R.id.editText2);

        Button addbtn1 = (Button) findViewById(R.id.login);
        addbtn1.setOnClickListener(this);

        Button rtn = (Button) findViewById(R.id.register);
        rtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //case R.id.login:
                //Intent j = new Intent(this, Login.class);
                //startActivity(j);
               // break;
           /* case R.id.btnsrch:
                Intent k=new Intent(this, searchRecipe.class);
                startActivity(k);
                break; */
            case R.id.register:
                Intent p = new Intent(this, register.class);
                startActivity(p);
                break;
            default:
                break;
        }
        uname = u_name.getText().toString();
        pswd = p_swd.getText().toString();

        new webServiceRequest(uname, pswd).execute();
    }


    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        public void call(Session session, SessionState state,
                         Exception exception) {

            if (state.isOpened()) {
                Intent i = new Intent(MainActivity.this, Home.class);
                startActivity(i);
                Log.d("Home", "Facebook session opened.");
            } else if (state.isClosed()) {
                Log.d("MainActivity", "Facebook session closed.");
            }

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uiHelper.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }


    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void status_msg(String msg) {
        if (msg.equals("Success")) {
            Intent i = new Intent(MainActivity.this, Home.class);
            startActivity(i);
        } else {


   /* if(msg == "Success"){
        Intent i = new Intent(this,Home.class);
        startActivity(i);
    }*/
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Failed");
            alertDialog.setMessage(msg);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    ((EditText) findViewById(R.id.editText)).setText("");
                    ((EditText) findViewById(R.id.editText2)).setText("");

                }
            });
            alertDialog.show();
        }


    }


    /**
     * Created by Sravani on 3/9/2015.
     */
    public class webServiceRequest extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog1;
        String username;
        String password;
        String units;
        String url = "http://kc-sce-cs551-2.kc.umkc.edu/aspnet_client/MPG12/Leela/Registration/Service1.svc/Login/";
        String url_param;


        public webServiceRequest(String usrname, String pswd) {
            username = usrname;
            password = pswd;

        }

        protected void onPreExecute() {
            super.onPreExecute();
            // dialog1 = ProgressDialog.show(getActivity(), "", "Loading...", true);

            //  Toast.makeText(getApplicationContext(), "Inside webservicerequest class",
            //   Toast.LENGTH_SHORT).show();

            //  Toast.makeText(getApplicationContext(), username + "/" + password,
            // Toast.LENGTH_SHORT).show();
        }


        protected String doInBackground(String... params) {

            url_param = url + username + "/" + password;

            Log.e("Username", username);
            Log.e("Password", password);


            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url_param);

            try {

                Log.e("STEP", "STEP 1");
                HttpResponse httpResponse = httpClient.execute(httpGet);
                StatusLine status = httpResponse.getStatusLine();
                InputStream ist = httpResponse.getEntity().getContent();
                Log.e("Status is", status.toString());

                Log.e("STEP", "STEP 2");
                ByteArrayOutputStream content = new ByteArrayOutputStream();

                int readCount = 0;
                while ((readCount = ist.read(buff)) != -1) {
                    content.write(buff, 0, readCount);
                }
                String response = new String(content.toByteArray());
                Log.e("Response", response);

                return response;

            } catch (Exception exception) {
                Log.e("ERROR", exception.toString());
                return null;
            }


        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            out_msg = parseJson(result);
            Log.e("Parser output:", out_msg);

            status_msg(out_msg);


            /*AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Add Grocery Item Status");
            alertDialog.setMessage(msg);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ((EditText) findViewById(R.id.gname)).setText("");
                    ((EditText) findViewById(R.id.qty1)).setText("");
                }
            });
            alertDialog.show(); */

        }

        protected String parseJson(String result) {
            Log.e("Step 3:", "parseJson");
            try {
                JSONObject login = new JSONObject(result);
                String username = login.getString("UserName");
                String password = login.getString("Password");
                String output_msg = login.getString("opt_msg");
                //  String qty = groceryItem.getString("quantity");
                //String username = groceryItem.getString("user");
                //String errorStatus = groceryItem.getString("errorStatus");
                //Log.e("status", errorStatus);
                Log.e("Output_msg:", output_msg);
                // Toast.makeText(getApplicationContext(), "entered parsejson:" + output_msg,
                //    Toast.LENGTH_SHORT).show();

                return output_msg;
            } catch (JSONException e) {
                e.printStackTrace();
                return e.getMessage().toString();
            }

        }


    }
}



