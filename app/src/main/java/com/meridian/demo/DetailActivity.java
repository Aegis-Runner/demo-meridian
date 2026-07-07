package com.meridian.demo;
import android.graphics.Color; import android.os.Bundle; import android.view.*; import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class DetailActivity extends AppCompatActivity {
  java.util.LinkedHashMap<String,EditText> fields=new java.util.LinkedHashMap<>();
  String entity, recordId;
  static String[][] fieldsFor(String e){
    if("products".equals(e)) return new String[][]{{"name","Name"},{"sku","SKU"},{"price","Price"},{"category","Category"}};
    if("orders".equals(e)) return new String[][]{{"customer","Customer"},{"product","Product"},{"qty","Quantity"},{"status","Status"}};
    return new String[][]{{"name","Name"},{"email","Email"},{"company","Company"},{"status","Status"}};
  }
  private void field(String key,String label,String val){ TextView l=new TextView(this); l.setText(label); l.setTextColor(Color.parseColor("#374151")); l.setTextSize(13);
    EditText e=new EditText(this); e.setHint(label); e.setText(val==null?"":val); e.setContentDescription(label);
    LinearLayout box=findViewById(R.id.detail_box); box.addView(l); box.addView(e); fields.put(key,e); }
  @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_detail);
    entity=getIntent().getStringExtra("entity"); String rec=getIntent().getStringExtra("record");
    org.json.JSONObject o=null; if(rec!=null){ try{ o=new org.json.JSONObject(rec); recordId=o.optString("id"); }catch(Exception ex){} }
    final boolean isNew=(o==null);
    ((TextView)findViewById(R.id.detail_title)).setText(isNew?("New "+ListActivity.singular(entity)):(o.optString(fieldsFor(entity)[0][0])));
    for(String[] f: fieldsFor(entity)) field(f[0], f[1], o!=null?o.optString(f[0]):"");
    final Button save=findViewById(R.id.save_btn); save.setContentDescription("Save changes");
    save.setOnClickListener(v->{
      try{ org.json.JSONObject j=new org.json.JSONObject(); for(java.util.Map.Entry<String,EditText> e:fields.entrySet()) j.put(e.getKey(),e.getValue().getText().toString());
        final String body=j.toString(); save.setEnabled(false);
        new Thread(()->{ try{ if(isNew) Net.post("/"+entity,body); else Net.put("/"+entity+"/"+recordId,body);
          runOnUiThread(()->{ Toast.makeText(this,(isNew?"Created":"Saved")+" — synced to the web",Toast.LENGTH_SHORT).show(); finish(); }); }
          catch(Exception ex){ runOnUiThread(()->{ Toast.makeText(this,"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show(); save.setEnabled(true); }); } }).start();
      }catch(Exception ex){ Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show(); }
    });
    final Button del=findViewById(R.id.delete_btn);
    if(isNew){ del.setVisibility(View.GONE); }
    else { del.setContentDescription("Delete"); del.setOnClickListener(v->{ del.setEnabled(false);
      new Thread(()->{ try{ Net.del("/"+entity+"/"+recordId); runOnUiThread(()->{ Toast.makeText(this,"Deleted — synced to the web",Toast.LENGTH_SHORT).show(); finish(); }); }
        catch(Exception ex){ runOnUiThread(()->{ Toast.makeText(this,"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show(); del.setEnabled(true); }); } }).start(); }); }
  }
}
