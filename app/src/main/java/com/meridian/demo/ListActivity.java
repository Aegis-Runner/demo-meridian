package com.meridian.demo;
import android.content.Intent; import android.graphics.Color; import android.os.Bundle; import android.view.*; import android.widget.*;
import androidx.appcompat.app.AppCompatActivity; import androidx.cardview.widget.CardView;
public class ListActivity extends AppCompatActivity {
  @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_list);
    final String entity=getIntent().getStringExtra("entity"); if(entity==null) return;
    ((TextView)findViewById(R.id.list_title)).setText(title(entity));
    Button add=findViewById(R.id.add_btn); add.setText("New "+singular(entity));
    add.setOnClickListener(v->{ Intent i=new Intent(this,DetailActivity.class); i.putExtra("entity",entity); startActivity(i); });
    load(entity);
  }
  @Override protected void onResume(){ super.onResume(); String e=getIntent().getStringExtra("entity"); if(e!=null) load(e); }
  void load(final String entity){
    final LinearLayout box=findViewById(R.id.list_box); box.removeAllViews();
    TextView loading=new TextView(this); loading.setText("Loading…"); loading.setTextColor(Color.parseColor("#6B7280")); box.addView(loading);
    new Thread(()->{ try{ final String json=Net.get("/"+entity);
      runOnUiThread(()->{ try{ box.removeAllViews(); org.json.JSONArray arr=new org.json.JSONArray(json);
        int pad=(int)(getResources().getDisplayMetrics().density*14);
        for(int k=0;k<arr.length();k++){ org.json.JSONObject o=arr.getJSONObject(k); String[] r=mapRow(entity,o); addCard(box,pad,r[0],r[1],r[2],entity,o); }
      }catch(Exception ex){ box.removeAllViews(); TextView t=new TextView(this); t.setText("Parse error: "+ex.getMessage()); box.addView(t);} });
    }catch(Exception ex){ runOnUiThread(()->{ box.removeAllViews(); TextView t=new TextView(this); t.setText("Network error: "+ex.getMessage()); t.setTextColor(Color.parseColor("#991B1B")); box.addView(t);} ); } }).start();
  }
  String[] mapRow(String e, org.json.JSONObject o){
    if("products".equals(e)) return new String[]{o.optString("name"), o.optString("sku")+" · "+o.optString("category"), "$"+o.optString("price")};
    if("orders".equals(e)) return new String[]{o.optString("customer"), o.optString("product")+" × "+o.optString("qty"), o.optString("status")};
    return new String[]{o.optString("name"), o.optString("company")+" · "+o.optString("email"), o.optString("status")};
  }
  void addCard(LinearLayout box,int pad,String t1,String t2,String t3,String entity,org.json.JSONObject o){
    CardView card=new CardView(this); LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=pad; card.setLayoutParams(lp);
    card.setRadius(pad); card.setCardElevation(2); card.setUseCompatPadding(true);
    LinearLayout col=new LinearLayout(this); col.setOrientation(LinearLayout.VERTICAL); col.setPadding(pad+pad,pad,pad+pad,pad);
    TextView a=new TextView(this); a.setText(t1); a.setTextSize(16); a.setTextColor(Color.parseColor("#1F2937")); a.setTypeface(a.getTypeface(),android.graphics.Typeface.BOLD);
    TextView b=new TextView(this); b.setText(t2); b.setTextColor(Color.parseColor("#6B7280")); b.setTextSize(13);
    TextView c=new TextView(this); c.setText(t3); c.setTextColor(Color.parseColor("#4F46E5")); c.setTextSize(13);
    col.addView(a); col.addView(b); col.addView(c); card.addView(col); card.setContentDescription(t1+" "+t3);
    card.setOnClickListener(v->{ Intent i=new Intent(this,DetailActivity.class); i.putExtra("entity",entity); i.putExtra("record",o.toString()); startActivity(i); });
    box.addView(card);
  }
  static String title(String e){ if("products".equals(e))return "Products"; if("orders".equals(e))return "Orders"; return "Customers"; }
  static String singular(String e){ if("products".equals(e))return "product"; if("orders".equals(e))return "order"; return "customer"; }
}
