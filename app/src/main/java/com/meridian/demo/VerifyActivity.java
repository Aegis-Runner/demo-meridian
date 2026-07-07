package com.meridian.demo;
import android.os.Bundle; import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class VerifyActivity extends AppCompatActivity {
  @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_verify);
    final EditText code=findViewById(R.id.v_code);
    Button verify=findViewById(R.id.v_btn); verify.setContentDescription("Verify device");
    verify.setOnClickListener(v->{ try{ org.json.JSONObject j=new org.json.JSONObject(); j.put("code",code.getText().toString()); final String body=j.toString();
      new Thread(()->{ try{ org.json.JSONObject o=new org.json.JSONObject(Net.post("/verify",body)); runOnUiThread(()->Toast.makeText(this,o.optString("message","Done"),Toast.LENGTH_SHORT).show()); }catch(Exception ex){ runOnUiThread(()->Toast.makeText(this,"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show()); } }).start();
    }catch(Exception ex){} });
  }
}
