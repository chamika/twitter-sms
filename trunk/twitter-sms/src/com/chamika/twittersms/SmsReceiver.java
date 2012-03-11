package com.chamika.twittersms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

	public static final String TWITTER_NO = "40404";
	public static final String SMS_FILE = "smsCache";

	public static final String SMS_EXTRA_NAME = "pdus";
	public static final String SMS_URI = "content://sms";

	public static final String ADDRESS = "address";
	public static final String PERSON = "person";
	public static final String DATE = "date";
	public static final String READ = "read";
	public static final String STATUS = "status";
	public static final String TYPE = "type";
	public static final String BODY = "body";
	public static final String SEEN = "seen";

	public static final int MESSAGE_TYPE_INBOX = 1;
	public static final int MESSAGE_TYPE_SENT = 2;

	public static final int MESSAGE_IS_NOT_READ = 0;
	public static final int MESSAGE_IS_READ = 1;

	public static final int MESSAGE_IS_NOT_SEEN = 0;
	public static final int MESSAGE_IS_SEEN = 1;

	String systemNo;
	Context context;
	ArrayList<SMS> smsList;

	public void onReceive(Context context, Intent intent) {
		// Get SMS map from Intent
		Bundle extras = intent.getExtras();

		this.context = context;

		String address = "";

		if (extras != null) {
			// Get received SMS array
			Object[] smsExtra = (Object[]) extras.get(SMS_EXTRA_NAME);

			for (int i = 0; i < smsExtra.length; ++i) {
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);

				String body = sms.getMessageBody().toString();
				long dateInMilis = sms.getTimestampMillis();
				address = sms.getOriginatingAddress();
				

				systemNo = TWITTER_NO;

				//skip twitter sms
				if (address.equals(systemNo)) {
					smsList = new ArrayList<SMS>();
					openFromFile();
					SMS temp_sms = new SMS(body, new Date(dateInMilis));
					smsList.add(temp_sms);
					saveToFile();
					this.abortBroadcast();
				}

			}

		}
	}

	private void saveToFile() {
		try {
			FileOutputStream fos = context.openFileOutput(SMS_FILE,
					Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(smsList);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			Log.d("SmsReceiver", "File Not found");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("SmsReceiver", "IO Error");
			e.printStackTrace();
		}
	}

	private void openFromFile() {
		try {
			FileInputStream fis = context.openFileInput(SMS_FILE);
			ObjectInputStream ois = new ObjectInputStream(fis);
			smsList = (ArrayList<SMS>) ois.readObject();
			ois.close();
			fis.close();

		} catch (Exception e1) {
			Log.d("SmsReceiver", "File Not found to read");
			e1.printStackTrace();
			smsList = new ArrayList<SMS>();
		}
	}
}
