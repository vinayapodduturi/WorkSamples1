package mpg12.ase.umkc.facebook;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNotificationActivity extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);
        Button homeButton = (Button) (findViewById(R.id.btnhome));
        homeButton.setOnClickListener(this);

    }

    public void createNotification(View view) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti1 = new Notification.Builder(this)
                .setContentTitle("Grocery" + "Expiry days")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .addAction(R.drawable.ic_launcher, "Call", pIntent)
                .addAction(R.drawable.ic_launcher, "More", pIntent)
                .addAction(R.drawable.ic_launcher, "And more", pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti1.flags |= Notification.FLAG_AUTO_CANCEL;
        //noti2.flags |=Notification.FLAG_AUTO_CANCEL;


        notificationManager.notify(0, noti1);
       // notificationManager.notify(0,noti2);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnhome){

            Intent j = new Intent(this, Home.class);
            startActivity(j);
        }
    }
}
