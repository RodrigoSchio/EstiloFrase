package estilo.frase;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.view.View;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.Toolbar.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.view.Gravity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class MainActivity extends Activity {
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(12, 12, 12, 0);
        LayoutParams lpw = new LayoutParams(LayoutParams.FILL_PARENT, this.getResources().getDisplayMetrics().heightPixels / 2);
        lpw.setMargins(0, 0, 0, 12);
        wv = new WebView(this);
        wv.setLayoutParams(lpw);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.loadUrl(getString(R.string.querystring));
        ll.addView(wv);
        ll.addView(geraBtn(true, getString(R.string.style), new View.OnClickListener() {
            public void onClick(View v) {
                wv.loadUrl("javascript:geraStilo(false,true,true,true);");
            }
        }));

        LinearLayout ll2 = new LinearLayout(this);
        ll2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ll2.setOrientation(LinearLayout.HORIZONTAL);
        ll2.addView(geraBtn(false, "m1", new View.OnClickListener() {
            public void onClick(View v) {
                wv.loadUrl("javascript:geraStilo(false,true,false,false);");
            }
        }));
        ll2.addView(geraBtn(false, "m2", new View.OnClickListener() {
            public void onClick(View v) {
                wv.loadUrl("javascript:geraStilo(false,false,true,false);");
            }
        }));
        ll2.addView(geraBtn(false, "m3", new View.OnClickListener() {
            public void onClick(View v) {
                wv.loadUrl("javascript:geraStilo(false,false,false,true);");
            }
        }));
        ll.addView(ll2);

        ll.addView(geraBtn(true, getString(R.string.reset), new View.OnClickListener() {
            public void onClick(View v) {
                wv.loadUrl("javascript:inicio();");
            }
        }));
        ll.addView(geraBtn(true, getString(R.string.frase), new View.OnClickListener() {
            public void onClick(View v) {
                wv.loadUrl("javascript:geraStilo(true,false,false,false);");
            }
        }));
        ll.addView(geraBtn(true, getString(R.string.save), new View.OnClickListener() {
            public void onClick(View v) {
                wv.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(wv.getDrawingCache());
                wv.setDrawingCacheEnabled(false);
                Intent shareImageIntent = new Intent(Intent.ACTION_SEND);
                shareImageIntent.setType("image/jpeg");
                shareImageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, String.valueOf(System.currentTimeMillis()), "styled phrase")));
                startActivity(Intent.createChooser(shareImageIntent, ""));
            }
        }));
        ScrollView sv = new ScrollView(this);
        sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        sv.addView(ll);
        setContentView(sv);
    }

    Button geraBtn(boolean emcima, String caption, View.OnClickListener fcn) {
        Button b = new Button(this);
        b.setText(caption);
        b.setGravity(Gravity.LEFT);
        b.setPadding(emcima ? 40 : 20, 20, 20, 20);
        b.setLayoutParams(new LayoutParams(emcima ? LayoutParams.FILL_PARENT : LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        b.setOnClickListener(fcn);
        return b;
    }
}