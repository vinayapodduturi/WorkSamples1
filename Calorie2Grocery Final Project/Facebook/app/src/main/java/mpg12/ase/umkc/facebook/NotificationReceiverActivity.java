package mpg12.ase.umkc.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationReceiverActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Button homeButton = (Button) (findViewById(R.id.btnhome));
        homeButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnhome){

            Intent j = new Intent(this, Home.class);
            startActivity(j);
        }
    }
}
