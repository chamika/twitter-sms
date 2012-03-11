package com.chamika.twittersms;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterSMSActivity extends ListActivity {

	Context context;
	ArrayList<SMS> smsList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		context = this;
		smsList = new ArrayList<SMS>();

		openFromFile();
		String[] tempSmsList;

		tempSmsList = new String[smsList.size()];

		for (int i = 0; i < tempSmsList.length; i++) {
			tempSmsList[(tempSmsList.length - i - 1)] = smsList.get(i).getDateString() + "\n"
					+ smsList.get(i).getBody();
		}

		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, tempSmsList));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String text = ((TextView) view).getText().toString();

				String url = text.substring(text.indexOf("http://"));

				Log.d("TwitterSMSActivity", "url=" + url);

				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}

		});
	}

	private void openFromFile() {
		try {
			FileInputStream fis = context.openFileInput(SmsReceiver.SMS_FILE);
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