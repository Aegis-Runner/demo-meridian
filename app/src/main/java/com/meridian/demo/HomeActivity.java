package com.meridian.demo;
import android.content.Intent; import android.os.Bundle; import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class HomeActivity extends AppCompatActivity {
  private void open(String e){ Intent i=new Intent(this,ListActivity.class); i.putExtra("entity",e); startActivity(i); }
  @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_home);
    findViewById(R.id.nav_customers).setOnClickListener(v->open("customers"));
    findViewById(R.id.nav_products).setOnClickListener(v->open("products"));
    findViewById(R.id.nav_orders).setOnClickListener(v->open("orders"));
    findViewById(R.id.nav_settings).setOnClickListener(v->startActivity(new Intent(this,SettingsActivity.class)));
    findViewById(R.id.nav_verify).setOnClickListener(v->startActivity(new Intent(this,VerifyActivity.class)));
  }
}
