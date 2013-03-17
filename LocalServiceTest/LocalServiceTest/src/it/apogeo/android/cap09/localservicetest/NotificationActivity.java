/**
 * 
 */
package it.apogeo.android.cap09.localservicetest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity di gestione dell'Intent proveniente dalla Status Bar
 * 
 * @author MASSIMO
 * 
 */
public class NotificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_activity);//avvio l'activity di notifica
		// Otteniamo le informazioni associate all'Intent
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			TextView textView = (TextView) findViewById(R.id.outputView);
			textView.setText(extras.getString("notificationType"));
		}
	}

}
