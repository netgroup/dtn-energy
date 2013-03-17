/**
 * 
 */
package it.apogeo.android.cap09.localservicetest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;
import java.io.*;
import java.net.*;

/**
 * Esempio di un Service locale che permette di inviare delle notifiche
 * ad intervalli casuali di tempo
 *  
 * @author MASSIMO
 *
 */
public class MyLocalService extends Service {
	/*
	 * Tag del Log
	 */
	private final static String LOG_TAG = "MyLocalService";
	//private BatteryManager battery=new BatteryManager();
	/*
	 * Numero massimo di notifiche da inviare dopo il quale il servizio muore
	 */
	
	//sfrutto questa variabile, la uso per te
	private final static int MAX_NOTIFICATION_NUMBER = 10;
	
	/*
	 * Identifciatori delle notifiche
	 */
	private final static int SIMPLE_NOTIFICATION_ID = 1;
	/*
	 * Riferimento al NotificationManager
	 */
	private NotificationManager notificationManager;	
	/*
	 * Riferimento al Thread in background
	 */
	private BackgroundThread backgroundThread;
	
	/*
	 * Notifica da inviare piu' volte
	 */
	private Notification notification;
	/*
	 * Intent della Notification
	 */
	private PendingIntent pIntent;
	
	/*
	 * Numero di notifiche inviate
	 */
	private int notificationNumber;
	
	/*public Handler h=new Handler(){ vedere looper
		ridefinire receiiveMessage(

		invio del paccheto,reschedule processo)};*/
	
	
	
	private final static long MIN_DELAY = 2000L;		
	/*
	 * Ampiezza massima della parte casuale del delay
	 */
	private final static long MAX_RANDOM_DELAY = 10000L;
	
	@Override
	public void onCreate() {
		super.onCreate();
		//Log.i(LOG_TAG, "Service Created");
		// Facciamo partire il BackgroundThread
		backgroundThread = new BackgroundThread();
		backgroundThread.start();
		System.out.println("thread avviato");
		// Otteniamo il riferimento al NotificationManager
		
		/*notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Log.i(LOG_TAG, "Service Created");
		// Creiamo la Notification
		notification = new Notification(R.drawable.icon,
				"Simple Notification", System.currentTimeMillis());		
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// Impostiamo le altre informazioni tra cui l'Intent
		Intent intent = new Intent(this, NotificationActivity.class);
		intent.putExtra("notificationType", "Simple Notification");
		pIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
				*/	
	}
	
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Solo per debugging
		Log.i(LOG_TAG, "Service Started");
		// Inizializziamo il numero di notifiche inviate
		notificationNumber = 0;
		return super.onStartCommand(intent, flags, startId);
	/*
	h.sendMessageAtTime(new message)
*/
	}



	@Override
	public void onDestroy() {
		backgroundThread.running = false;
		super.onDestroy();
		Log.i(LOG_TAG, "Service Destroyed");
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// Ritorniamo null in quanto non si vuole permettere
		// l'accesso al servizio da una applicazione diversa
		return null;
	}
	
	public void sendNotification() {
		// Aggiorniamo il contatore
		notificationNumber++;
		notification.number=notificationNumber;
		notification.setLatestEventInfo(this, "Simple Notification",
				"Simple Notification Extended", pIntent);
		// La lanciamo attraverso il Notification Manager
		notificationManager.notify(SIMPLE_NOTIFICATION_ID, notification);
	}	
	
	private final class BackgroundThread extends Thread {
		/*
		 * Parte fissa del delay
		 */
	

		public boolean running= true;
		
		public void run() {
			String sentence = null;
			  String modifiedSentence;
			  //BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
			  DatagramSocket clientSocket;
			  //sendNotification();
			try {
				clientSocket = new DatagramSocket();
				//il server python è sulla porta 2000
				  //clientSocket = new Socket("192.168.150.1",2000);
				 //clientSocket = new Socket("192.168.150.1",2002);  
				 //BatteryManager battery = new BatteryManager();
				InetAddress IPAddress = InetAddress.getByName("192.168.150.1");
			      byte[] sendData = new byte[1024];
				 
				while(true && running){
					  //sendNotification();
					 //TODO modificare da tcp a udp 
				      //System.out.println("dentro al ciclo");
					  
				      //DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
					  //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					  //sentence = inFromUser.readLine();
					  //battery.
					  //b.EXTRA_LEVEL;
					
					
					// livello batteria ma non funziona
				      /*double level = Double.parseDouble(BatteryManager.EXTRA_LEVEL);
					  double scale = Double.parseDouble(BatteryManager.EXTRA_SCALE);
					  //String scale = BatteryManager.EXTRA_SCALE;

					  float batteryPct = (float) (level / (float)scale);*/
					  GregorianCalendar calendar=new GregorianCalendar(TimeZone.getDefault());
					  
					  //sentence=calendar.HOUR_OF_DAY+":"+calendar.MINUTE+"."calendar.;
					  //sentence=Long.toString(calendar.getTimeInMillis());
					  long now=calendar.getTimeInMillis();
					  //invio dati al server 
					  //outToServer.writeBytes(Long.toString(calendar.getTimeInMillis()) + " livello batteria:"+Float.toString(batteryPct));
					  
					  
					  
				      //byte[] receiveData = new byte[1024];
				      
				      //sendData = (Long.toString(calendar.getTimeInMillis()) + " livello batteria:"+Float.toString(batteryPct)).getBytes();
				      sendData = (Long.toString(calendar.getTimeInMillis())).getBytes();
				      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 50005);
				      clientSocket.send(sendPacket);
					  
					  //modifiedSentence = inFromServer.readLine();
					  //System.out.println("FROM SERVER: " + modifiedSentence);
					  Thread.sleep((now+300000)-(calendar.getTimeInMillis())); // dormo 5 minuti dopo aver inviato
				  }
				 clientSocket.close();
			  
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*try {
				udp_sock=new DatagramSocket(60000);
				InetAddress addr = InetAddress.getByName("localhost");
				//Creazione del pacchetto da inviare al Server
				DatagramPacket hi = new DatagramPacket(null, 0, addr, 7777);//invio stringa nulla
				
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			
			
			/*AlertDialog.Builder builder=new AlertDialog.Builder(new ContextThemeWrapper());
			builder.setTitle("Avviso");
			builder.setMessage("Attenzione!Questo è un avviso");
			builder.setCancelable(true);
			AlertDialog alert=builder.create();
			
			Log.i(LOG_TAG, "BackgroundThread Started");	
			
			Random random = new Random();
			//dovrò modificare in modo che 
			while(running && notificationNumber<MAX_NOTIFICATION_NUMBER){
				long randomDelay = MIN_DELAY + Math.abs(random.nextInt() %MAX_RANDOM_DELAY);
				Log.i(LOG_TAG, "Delay is (ms) "+randomDelay);
				try{Thread.sleep(randomDelay);}catch(InterruptedException ie){}
				sendNotification();//devo modificare in modo che mandi pacchetti udp o tcp 
			}
			// Al termine del metodo run terminiamo il servizio
			stopSelf();*/
		}

	}
	

}
