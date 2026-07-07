package com.meridian.demo;
import android.os.Bundle; import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class SettingsActivity extends AppCompatActivity {
  @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_settings);
    final EditText name=findViewById(R.id.s_name), email=findViewById(R.id.s_email);
    new Thread(()->{ try{ org.json.JSONObject o=new org.json.JSONObject(Net.get("/profile")); runOnUiThread(()->{ name.setText(o.optString("name")); email.setText(o.optString("email")); }); }catch(Exception ex){} }).start();
    Button save=findViewById(R.id.save_settings); save.setContentDescription("Save settings");
    save.setOnClickListener(v->{ try{ org.json.JSONObject j=new org.json.JSONObject(); j.put("name",name.getText().toString()); j.put("email",email.getText().toString()); final String body=j.toString();
      new Thread(()->{ try{ Net.post("/profile",body); runOnUiThread(()->Toast.makeText(this,"Profile saved — synced to the web",Toast.LENGTH_SHORT).show()); }catch(Exception ex){ runOnUiThread(()->Toast.makeText(this,"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show()); } }).start();
    }catch(Exception ex){} });
  }
}
