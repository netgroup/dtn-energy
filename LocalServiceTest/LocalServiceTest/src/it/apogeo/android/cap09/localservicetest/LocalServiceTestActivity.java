package it.apogeo.android.cap09.localservicetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LocalServiceTestActivity extends Activity {
	/*
	 * Riferimento all'Intent associato al Service
	 */
	private Intent serviceIntent;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Inizializziamo l'Intent del servizio
        serviceIntent = new Intent(this,MyLocalService.class);//il secondo argomento si prende la classe scritta
    }
    
    public void startLocalService(View button){
    	// Avviamo il servizio locale
    	startService(serviceIntent);
    }
    
    public void stopLocalService(View button){
    	// Fermiamo il servizio
    	stopService(serviceIntent);
    }    
}