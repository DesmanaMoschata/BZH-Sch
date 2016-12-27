    package bzh.bzh_sch;

    import android.app.Dialog;
    import android.content.Context;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Environment;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.KeyEvent;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;

    import java.io.BufferedInputStream;
    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.OutputStream;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.net.MalformedURLException;
    import java.net.URLConnection;
    import java.util.ArrayList;
    import java.util.List;
    import android.widget.AdapterView;
    import android.widget.AdapterView.OnItemClickListener;

    import com.google.android.gms.appindexing.Action;
    import com.google.android.gms.appindexing.AppIndex;
    import com.google.android.gms.appindexing.Thing;
    import com.google.android.gms.common.api.GoogleApiClient;

    import static android.R.id.button1;
    import static bzh.bzh_sch.R.id.action_settings;
    import static bzh.bzh_sch.R.id.button;
    import static bzh.bzh_sch.R.menu.menu_main;
    import android.app.ProgressDialog;
    import android.os.AsyncTask;
    import android.os.Bundle;

    public class MainActivity extends AppCompatActivity {
        boolean chkd = true;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            final Button button1 = (Button) findViewById(R.id.button);

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
            final List<String> allinone_name_mod = new ArrayList<String>();
            final List<String> allinone_id_mod = new ArrayList<String>();
            final List<String> crib_id = new ArrayList<String>();
            try {
                InputStream inputStream = new FileInputStream(new File(Environment.getDataDirectory().getAbsoluteFile().toString() + "/data/bzh.schiehallion.bzh/scddata-2.0.sql"));
                if (inputStream != null) {
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;
                    String id_str ="9999999";
                    int cnt = 0;

                    String[] lineparts_crib;
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
                                cnt++;
                            }
                        }
                        else {
                            if (line.startsWith("INSERT INTO 'dancecrib'")) {
                                lineparts_crib = line.split(", ");
                                crib_id.add(lineparts_crib[3]);
                            }
                        }
                        if (line.startsWith("INSERT INTO 'dancecribsource'")) break;

                    }
                    inputStream.close();
                    for(int i = 0; i < cnt; i++){
                        if(!crib_id.contains(allinone_id.get(i))){
                           allinone_name.set(i, allinone_name.get(i)+" [No crib]");
                            if (!chkd){allinone_id_mod.add(allinone_id.get(i)); allinone_name_mod.add(allinone_name.get(i));}
                        }
                        else{
                        allinone_id_mod.add(allinone_id.get(i)); allinone_name_mod.add(allinone_name.get(i));}
                    }
                    /*for (int i = 0; i < cnt; i++)
                    {if (allinone_name.get(i).contains("No crib")){allinone_name.remove(i); allinone_id.remove(i);}}*/
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allinone_name_mod);
                    lv.setAdapter(adapter);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //et.setText("File Not Found");
            } catch (IOException e) {
                e.printStackTrace();
            }
            lv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent2 = new Intent(MainActivity.this, description.class);
                    Intent intent1 = new Intent(MainActivity.this, description.class);
                    intent1 = intent1.putExtra("RRR", allinone_id_mod.get(i));
                    intent2 = intent1.putExtra("KKK", allinone_name_mod.get(i));
                    startActivity(intent1);
                }
            });

        }

        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.action_settings:
                    chkd=!chkd;
                    item.setChecked(chkd);
                    return true;
                case R.id.action_settings2:
                    String file_url = "http://media.strathspey.org/scddata/scddata-2.0.sql";
                    new DownloadFileFromURL().execute(file_url);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }


        /* Download  StachOverflow: http://stackoverflow.com/questions/15758856/android-how-to-download-file-from-webserver */
        // Progress Dialog
        private ProgressDialog pDialog;
        public static final int progress_bar_type = 0;

        // File url to download


        /**
         * Showing Dialog
         * */

        @Override
        protected Dialog onCreateDialog(int id) {
            switch (id) {
                case progress_bar_type: // we set this to 0
                    pDialog = new ProgressDialog(this);
                    pDialog.setMessage("Downloading file. Please wait...");
                    pDialog.setIndeterminate(false);
                    pDialog.setMax(100);
                    pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pDialog.setCancelable(true);
                    pDialog.show();
                    return pDialog;
                default:
                    return null;
            }
        }

        /**
         * Background Async Task to download file
         * */
        class DownloadFileFromURL extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread Show Progress Bar Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(progress_bar_type);
            }

            /**
             * Downloading file in background thread
             * */
            @Override
            protected String doInBackground(String... f_url) {
                int count;
                try {
                    URL url = new URL(f_url[0]);
                    URLConnection conection = url.openConnection();
                    conection.connect();

                    // this will be useful so that you can show a tipical 0-100%
                    // progress bar
                    int lenghtOfFile = conection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream(),
                            8192);

                    // Output stream
                    OutputStream output = new FileOutputStream(Environment.getDataDirectory().getAbsoluteFile().toString()
                            + "/data/bzh.schiehallion.bzh/scddata-2.0.sql");

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

                return null;
            }

            /**
             * Updating progress bar
             * */
            protected void onProgressUpdate(String... progress) {
                // setting progress percentage
                pDialog.setProgress(Integer.parseInt(progress[0]));
            }

            /**
             * After completing background task Dismiss the progress dialog
             * **/
            @Override
            protected void onPostExecute(String file_url) {
                // dismiss the dialog after the file was downloaded
                dismissDialog(progress_bar_type);

            }

        }
    }

