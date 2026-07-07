package com.meridian.demo;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.view.View; import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {
  @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_login);
    Button signin = findViewById(R.id.signin);
    signin.setContentDescription("Sign in");
    signin.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
      startActivity(new Intent(LoginActivity.this, HomeActivity.class)); }});
  }
}
