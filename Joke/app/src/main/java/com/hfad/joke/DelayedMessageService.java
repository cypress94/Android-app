package com.hfad.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.os.Handler;
import android.widget.Toast;


public class DelayedMessageService extends IntentService {

    public static final String EXTRA_MESSAGE = "message";
    private Handler handler;
    public static final int NOTIFICATION_ID = 5453;

    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    /**Метод выполняется в основном потоке и до onHandleIntent.
     * Для обеспечения дальнейшего доступа к интерфейсу из фонового потока
     * создаётся объект Handle*/
    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        handler = new Handler();// создаётся поток для отображения на View
        return super.onStartCommand(intent, flags, startId);
    }
    /**Метод выполняется в фоновом потоке. Нет доступа к интерфейсу приложения*/
    @Override
    protected void onHandleIntent(Intent intent) {
       synchronized (this){
           try{
               wait(10_000);
           }catch (InterruptedException e){
               e.printStackTrace();
           }
       }
       String text = intent.getStringExtra(EXTRA_MESSAGE);
       showText(text);
    }

    private void showText(final String text){

        Log.v("DelayedMessageService", "The message is" + text);
        // Для отображения временного окна
        /*handler.post(new Runnable(){
            @Override
            public void run(){
                Toast toast = Toast.makeText(getApplicationContext(),
                        text, Toast.LENGTH_LONG);
                toast.show();
            }
        });*/

        // Создание уведомления через службу уведомлений


        // 1. Создание отложенного интента для запуска приложения из уведомления
        // Создаётся простой явный интент
        Intent intent = new Intent(this, MainActivity.class);
        // Передача интента в TaskStackBuilder для обеспечения корректного стека возврата
        TaskStackBuilder stackBuilder= TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);// получаем стек возврата относящийсяк активности
        stackBuilder.addNextIntent(intent); // и добавляем туда интент
        // Получение отложенного интента от TaskStackBuilder
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // Добавление интента в уведомление

        // 2. Создание объекта уведомления
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(text)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .build();
        // Отправка уведомления службой
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }
}
