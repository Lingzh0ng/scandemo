package com.wearapay.scandemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.wearapay.scandemo.base.BaseActivity;
import com.wearapay.scandemo.utils.ActivityUtils;
import javax.inject.Inject;

//import static net.ezbim.scan.simple.SimpleScanActivity.SIMPLE_SCAN_RESULT;

public class MainActivity extends BaseActivity {

  private TextView tv;
  private Button open;

  @Inject Dest dest1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    App.app.getComponent().inject(this);

    // Example of a call to a native method
    tv = (TextView) findViewById(R.id.sample_text);
    open = (Button) findViewById(R.id.open);
    LinearLayout my_action_bar = (LinearLayout) findViewById(R.id.my_action_bar);
    my_action_bar.setBackgroundColor(getResources().getColor(R.color.test_color));
    open.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override public void onClick(View v) {
        //                startActivityForResult(new Intent(MainActivity.this, SimpleScanActivity.class), 1);
        String test = dest1.show("lyz", "540101");

        Toast.makeText(MainActivity.this, "1:" + test, Toast.LENGTH_SHORT).show();
        //        System.out.println(test);
        //                System.out.println(((UserNetRepositoryImpl) userRepository).getServiceType());
        //
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.STATUS_COLOR, R.color.test_color);
        bundle.putString("title", "test");
        ActivityUtils.startFragment(MainActivity.this, AppConstant.FragmentType.Login, bundle);

        finish();
      }
    });
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
//      String stringExtra = data.getStringExtra(SIMPLE_SCAN_RESULT);
//      Toast.makeText(this, "QR1111119988:" + stringExtra, Toast.LENGTH_SHORT).show();
//      tv.setText(stringExtra);
    }
  }
}
