package mpg12.ase.umkc.facebook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Leela Gajula on 04/26/15.
 */
public class register extends FragmentActivity implements View.OnClickListener{
    public EditText regname;

    public EditText regusername;
    public EditText regpassword;
    public  EditText cnfpassword;
    public String name;
    public String uname;
    public String pswd;
    public String cpswd;
    public String add_status_msg;
    private static byte[] buff = new byte[1024];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);



        regname = (EditText) findViewById(R.id.regname);
        regusername =(EditText)findViewById(R.id.regusername);
        regpassword = (EditText)findViewById(R.id.regpassword);
        cnfpassword =  (EditText) findViewById(R.id.cnfpassword);

        Button addbtn1 = (Button) findViewById(R.id.submit);
        addbtn1.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submit) {
name = regname.getText().toString();
            uname = regusername.getText().toString();
            pswd = regpassword.getText().toString();
            cpswd = cnfpassword.getText().toString();

            new webServiceRequest(name,uname,pswd,cpswd).execute();

            Toast.makeText(getApplicationContext(), name + "/" + uname + "/" + pswd + "/" + cpswd,
                    Toast.LENGTH_SHORT).show();
        }

        // if(v.getId() == R.id.register){
        //handle the click here

        //Intent j = new Intent(this, MainActivity.class);
        //startActivity(j);
        //Toast.makeText(getApplicationContext(), "Clicked return",
        //Toast.LENGTH_SHORT).show();
        //}
    }


    public void status_msg(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Register Success");
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((EditText) findViewById(R.id.regname)).setText("");
                ((EditText) findViewById(R.id.regusername)).setText("");
                ((EditText) findViewById(R.id.regpassword)).setText("");
                ((EditText) findViewById(R.id.cnfpassword)).setText("");
            }
        });
        alertDialog.show();
    }


    /**
     * Created by Sravani on 3/9/2015.
     */
    public class webServiceRequest extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog1;
        String name1;
        String username;
        String password;
        String cpassword;
        String url = "http://kc-sce-cs551-2.kc.umkc.edu/aspnet_client/MPG12/Leela/Registration/Service1.svc/Registration/";
        String url_param;


        public webServiceRequest(String name,String uname, String pswd , String cpswd) {
            name1=name;
            username = uname;
            password = pswd;
            cpassword= cpswd;

        }

        protected void onPreExecute() {
            super.onPreExecute();
            // dialog1 = ProgressDialog.show(getActivity(), "", "Loading...", true);

            Toast.makeText(getApplicationContext(), "Inside webservicerequest class",
                    Toast.LENGTH_SHORT).show();

            Toast.makeText(getApplicationContext(), name1 + "/" + username + "/" + password +"/"  +cpassword,
                    Toast.LENGTH_SHORT).show();
        }


        protected String doInBackground(String... params) {

            url_param = url + name1 +"/"+ username + "/" + password+"/"  +cpassword;
            Log.e("Username", name1);
            Log.e("Username", username);
            Log.e("Password", password);

            Log.e("Password", cpassword);


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
                String response = new String (content.toByteArray());
                Log.e("Response", response);

                return response;

            } catch (Exception exception) {
                Log.e("ERROR", exception.toString());
                return null;
            }


        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String out_msg = parseJson(result);
            Log.e("Parser output:" , out_msg);
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
            Log.e("Step 3:" , "parseJson");
            try {
                JSONObject login = new JSONObject(result);
                String usname = login.getString("UserName");
                String pwd = login.getString("Password");
                String output_msg = login.getString("opt_msg");
                //  String qty = groceryItem.getString("quantity");
                //String username = groceryItem.getString("user");
                //String errorStatus = groceryItem.getString("errorStatus");
                //Log.e("status", errorStatus);
                Log.e("Output_msg:", output_msg);
                Toast.makeText(getApplicationContext(), "entered parsejson:" + output_msg,
                        Toast.LENGTH_SHORT).show();

                return output_msg;
            } catch (JSONException e) {
                e.printStackTrace();
                return e.getMessage().toString();
            }

        }


    }
}
