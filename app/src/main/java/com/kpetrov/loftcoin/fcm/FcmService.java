package com.kpetrov.loftcoin.fcm;

import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kpetrov.loftcoin.BaseComponent;
import com.kpetrov.loftcoin.LoftApp;
import com.kpetrov.loftcoin.R;
import com.kpetrov.loftcoin.ui.main.MainActivity;
import com.kpetrov.loftcoin.util.Notifier;
import java.util.Objects;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

public class FcmService extends FirebaseMessagingService {

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject Notifier notifier;

    @Override
    public void onCreate() {
        super.onCreate();
        final BaseComponent baseComponent = ((LoftApp) getApplication()).getComponent();
        DaggerFcmComponent.builder().baseComponent(baseComponent).build().inject(this);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        final RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            disposable.add(notifier.sendMessage(
                    Objects.toString(notification.getTitle(), getString(R.string.app_name)),
                    Objects.toString(notification.getBody(),"Something wrong!"),
                    MainActivity.class
            ).subscribe());
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
