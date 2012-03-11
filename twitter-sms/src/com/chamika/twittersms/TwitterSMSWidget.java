package com.chamika.twittersms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class TwitterSMSWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		RemoteViews remoteViews;
        ComponentName watchWidget;
        DateFormat format = SimpleDateFormat.getTimeInstance( SimpleDateFormat.MEDIUM, Locale.getDefault() );

        remoteViews = new RemoteViews( context.getPackageName(), R.layout.widget );
        watchWidget = new ComponentName( context, TwitterSMSWidget.class );
        remoteViews.setTextViewText( R.id.textView_widget, "Time = " + format.format( new Date()));
        appWidgetManager.updateAppWidget( watchWidget, remoteViews );
	}
	
	

}
