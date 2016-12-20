    package bzh.bzh_sch;

    import android.content.ClipData;
    import android.content.Context;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Environment;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.KeyEvent;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;
    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.util.ArrayList;
    import java.util.List;
    import android.widget.AdapterView;
    import android.widget.AdapterView.OnItemClickListener;

    import com.google.android.gms.appindexing.Action;
    import com.google.android.gms.appindexing.AppIndex;
    import com.google.android.gms.appindexing.Thing;
    import com.google.android.gms.common.api.GoogleApiClient;

    import static android.R.id.button1;

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Button button1 = (Button) findViewById(R.id.button);
            button1.setText("Поиск");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lookUp();
                }
            });
            final EditText et = (EditText) findViewById(R.id.editText);
            et.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        {
                            lookUp();
                            et.clearFocus();
                            return true;
                        }
                    }
                    return false;
                }
            });
        }

        public void lookUp() {
            final EditText et = (EditText) findViewById(R.id.editText);
            final String string = et.getText().toString();
            ListView lv = (ListView) findViewById(R.id.List_);
            final List<String> allinone_name = new ArrayList<String>();
            final List<String> allinone_id = new ArrayList<String>();
            try {
                File sdcard = Environment.getExternalStorageDirectory();
                String filePath = sdcard.getAbsolutePath();
                //et.setText(filePath);
                InputStream inputStream = new FileInputStream(new File(filePath + "/scddata.scd"));
                if (inputStream != null) {
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;
                    String id_str;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("INSERT INTO 'dance'") && (line.contains(string))) {
                            String ddd;
                            String[] lineparts = line.split("', '");
                            String[] lineparts_fuckit = lineparts[3].split(",");
                            if (lineparts_fuckit[0].contains(string) | (lineparts_fuckit[1] == " The" | lineparts_fuckit[1] == " A")) {
                                int indxofid_s = line.indexOf("(") + 1;
                                int indxofid_e = line.indexOf(", '");
                                id_str = line.substring(indxofid_s, indxofid_e);
                                allinone_id.add(id_str);
                                allinone_name.add(lineparts[2]);
                            }
                        }
                    }
                    inputStream.close();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allinone_name);
                    lv.setAdapter(adapter);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                et.setText("FileNotFoundException");
            } catch (IOException e) {
                e.printStackTrace();
            }
            lv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent2 = new Intent(MainActivity.this, description.class);
                    Intent intent1 = new Intent(MainActivity.this, description.class);
                    intent1 = intent1.putExtra("RRR", allinone_id.get(i));
                    intent2 = intent1.putExtra("KKK", allinone_name.get(i));
                    startActivity(intent1);
                }
            });

        }

        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        public static Context getInstance() {
            return null;
        }


        /**
         * ATTENTION: This was auto-generated to implement the App Indexing API.
         * See https://g.co/AppIndexing/AndroidStudio for more information.
         */
        public Action getIndexApiAction() {
            Thing object = new Thing.Builder()
                    .setName("Main Page") // TODO: Define a title for the content shown.
                    // TODO: Make sure this auto-generated URL is correct.
                    .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                    .build();
            return new Action.Builder(Action.TYPE_VIEW)
                    .setObject(object)
                    .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                    .build();
        }
    }
