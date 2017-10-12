package com.wearapay.scandemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import static net.ezbim.scan.simple.SimpleScanActivity.SIMPLE_SCAN_RESULT;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private TextView tv;
    private Button open;

    @Inject
    Dest dest1;
//    @Inject
//    UserNetRepositoryImpl repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.app.getComponent().inject(this);

        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);
        open = (Button) findViewById(R.id.open);
        tv.setText(stringFromJN());
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivityForResult(new Intent(MainActivity.this, SimpleScanActivity.class), 1);
                String test = dest1.show("lyz", "540101");

                Toast.makeText(MainActivity.this, "1:" + test, Toast.LENGTH_SHORT).show();
//        System.out.println(test);
//                System.out.println(((UserNetRepositoryImpl) userRepository).getServiceType());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String stringExtra = data.getStringExtra(SIMPLE_SCAN_RESULT);
            Toast.makeText(this, "QR1111119988:" + stringExtra, Toast.LENGTH_SHORT).show();
            tv.setText(stringExtra);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJN();
}
