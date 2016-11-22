package xyz.alteration.shakeplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * Created by blogcin on 2016-11-22.
 */

public class ShakeCaster extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent musicService = new Intent(context, ShakeService.class);
            context.startService(musicService);
        }
    }
}
