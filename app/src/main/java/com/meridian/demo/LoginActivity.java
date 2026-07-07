package com.meridian.demo;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.view.View; import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {
  // Valid demo credentials. Change these if you want a different test account,
  // then rebuild the APK. The Sign in button now VALIDATES against them instead
  // of navigating unconditionally (the old mock let any/no password through).
  private static final String VALID_EMAIL = "demo@meridian.test";
  private static final String VALID_PASSWORD = "demo1234";
  @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_login);
    final EditText email = findViewById(R.id.email);
    final EditText password = findViewById(R.id.password);
    final TextView error = findViewById(R.id.error);
    Button signin = findViewById(R.id.signin);
    signin.setContentDescription("Sign in");
    signin.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
      String e = email.getText().toString().trim();
      String p = password.getText().toString();
      if (VALID_EMAIL.equals(e) && VALID_PASSWORD.equals(p)) {
        if (error != null) error.setVisibility(View.GONE);
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
      } else {
        String msg = "Invalid email or password";
        if (error != null) { error.setText(msg); error.setVisibility(View.VISIBLE); }
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
      }
    }});
  }
}
