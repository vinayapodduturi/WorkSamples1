package mpg12.ase.umkc.facebook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
 * Created by Sravani on 3/9/2015.
 */
public class addGrocery extends FragmentActivity implements View.OnClickListener{

    EditText g_name, g_qty;
    public String g_units, gname , gqty;
    private String g_user="Sravani";
    private Spinner spin;
    private String[] m_units;
    public String add_status_msg;
    private static byte[] buff = new byte[1024];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_grocery);

        m_units = getResources().getStringArray(R.array.units_list);
        spin =  (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, m_units);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);

        g_name = (EditText) findViewById(R.id.gname);
        g_qty =  (EditText) findViewById(R.id.qty1);

        Button addbtn1 = (Button) findViewById(R.id.addbtn);
        addbtn1.setOnClickListener(this);

        Button rtn = (Button) findViewById(R.id.btnrtn);
        rtn.setOnClickListener(this);

  }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addbtn) {
            g_units = String.valueOf(spin.getSelectedItem());
            gname = g_name.getText().toString();
            gqty = g_qty.getText().toString();

            new webServiceRequest(gname, gqty, g_units).execute();

           // Toast.makeText(getApplicationContext(), gname + "/" + gqty + "/" + g_units,
                 //   Toast.LENGTH_SHORT).show();
        }

        if(v.getId() == R.id.btnrtn){
            //handle the click here

            Intent j = new Intent(this, Home.class);
            startActivity(j);
            //Toast.makeText(getApplicationContext(), "Clicked return", Toast.LENGTH_SHORT).show();
        }
 }


   public void status_msg(String msg) {
       AlertDialog alertDialog = new AlertDialog.Builder(this).create();
       alertDialog.setTitle("Add Grocery Item Status");
       alertDialog.setMessage(msg);
       alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
               ((EditText) findViewById(R.id.gname)).setText("");
               ((EditText) findViewById(R.id.qty1)).setText("");
           }
       });
       alertDialog.show();
    }


    /**
     * Created by Sravani on 3/9/2015.
     */
    public class webServiceRequest extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog1;
        String name;
        String quantity;
        String units;
        String url = "http://kc-sce-cs551-2.kc.umkc.edu/aspnet_client/MPG12/Sravani/AddGrocery/Service1.svc/addgrocery/Sravani/";
        String url_param;


        public webServiceRequest(String nm, String qty, String unts) {
            name = nm;
            quantity = qty;
            units = unts;
        }

        protected void onPreExecute() {
            super.onPreExecute();
           // dialog1 = ProgressDialog.show(getActivity(), "", "Loading...", true);

           // Toast.makeText(getApplicationContext(), "Inside webservicerequest class", Toast.LENGTH_SHORT).show();

           // Toast.makeText(getApplicationContext(), name + "/" + quantity + "/" + units, Toast.LENGTH_SHORT).show();
        }


        protected String doInBackground(String... params) {

            url_param = url + name + "/" + quantity + "/" + units;

            Log.e("Grocery name", name);
            Log.e("Quantity", quantity);
            Log.e("Units", units);
            Log.e("Final URL" ,url_param );

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
                JSONObject groceryItem = new JSONObject(result);
                String units = groceryItem.getString("measure");
                String trackUrl = groceryItem.getString("name");
                String output_msg = groceryItem.getString("opt_msg");
                String qty = groceryItem.getString("quantity");
                String username = groceryItem.getString("user");
                //String errorStatus = groceryItem.getString("errorStatus");
                //Log.e("status", errorStatus);
                Log.e("Output_msg:", output_msg);
              //  Toast.makeText(getApplicationContext(), "entered parsejson:" + output_msg, Toast.LENGTH_SHORT).show();

                return output_msg;
            } catch (JSONException e) {
                e.printStackTrace();
                return e.getMessage().toString();
            }

        }


    }
}