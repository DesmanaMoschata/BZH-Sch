package bzh.bzh_sch;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent intent = getIntent();
        RelativeLayout descrlayout = (RelativeLayout)findViewById(R.id.descrl);
        String fName = intent.getStringExtra("RRR");
        String dName = intent.getStringExtra("KKK");
        this.setTitle(dName);
        TextView textView = (TextView)findViewById(R.id.textView);
        try {
            TextView tvbar_ = (TextView) findViewById(R.id.tvbar);
            TextView tvtype_ = (TextView) findViewById(R.id.tvtype);
            TextView tvform_ = (TextView) findViewById(R.id.tvform);
            File sdcard = Environment.getExternalStorageDirectory();
            String filePath = sdcard.getAbsolutePath();
            InputStream inputStream = new FileInputStream(new File(filePath + "/scddata.scd"));
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                String id_str;
                boolean findit = false;
                while ((line = reader.readLine()) != null) {
                    if(line.startsWith("INSERT INTO 'dance'" )&&(line.contains('('+fName+',')))
                    {
                        String []lineparts_2 = line.split (", ");
                        if (line.contains(", The'") | line.contains(", A'"))
                        {
                            tvtype_.setText(getType(lineparts_2[6]));
                            tvbar_.setText(" " + lineparts_2[7]);
                            tvform_.setText(" " + getCouples(lineparts_2[10]) + ", " + getFormation(lineparts_2[9]) + " " + getProgression(lineparts_2[8]));
                        }
                        else {
                            tvtype_.setText(getType(lineparts_2[5]));
                            tvbar_.setText(" " + lineparts_2[6]);
                            tvform_.setText(" " + getCouples(lineparts_2[9]) + ", " + getFormation(lineparts_2[8]) + " " + getProgression(lineparts_2[7]));
                        }
                    }
                    if(line.startsWith("INSERT INTO 'dancealias' VALUES")){break;}

                }
                while ((line = reader.readLine()) != null) {
                    if(line.startsWith("INSERT INTO 'dancecrib' VALUES")&&(line.contains(", " + fName+", ")))
                    {
                        findit = true;
                        String []lineparts = line.split(", '");
                        String ddd = "";
                        ddd = ddd + lineparts[4];
                        line = reader.readLine();
                        while (!(line.startsWith("INSERT INTO 'dancecrib' VALUES")))
                        {
                            ddd = ddd + "\n" + line;
                            line = reader.readLine();
                            if(line.contains(");")){line = line.substring(0, line.indexOf(");")-4);}
                        }
                        if (!ddd.contains("::\n")) ddd = ddd.replace("::", "::\n");
                        if (ddd.contains("9--")) ddd = ddd.replace("9--", "9-16");
                        if (ddd.contains("1--")) ddd = ddd.replace("1--", "1-8");
                        if (ddd.contains("17--")) ddd = ddd.replace("17--", "17-24");
                        if (ddd.contains("25--")) ddd = ddd.replace("25--", "25-32");
                        if (ddd.contains("33--")) ddd = ddd.replace("33--", "33-40");
                        if (ddd.contains("41--")) ddd = ddd.replace("41--", "41-48");
                        if (ddd.contains("49--")) ddd = ddd.replace("49--", "49-56");
                        if (ddd.contains("57--")) ddd = ddd.replace("57--", "57-64");
                        textView.setText(ddd);
                        break;
                    }
                }
                if(!findit){textView.setText("No description in database.\nCheck http://my.strathspey.org/");}
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getProgression (String str) {
        switch (str){
        case ("1"): return("2341");
        case ("2"): return("2413");
        case ("3"): return("OnceOnly");
        case ("4"): return("ChangePtnr");
        case ("5"): return("3421");
        case ("6"): return("53412");
        case ("7"): return("24153");
        case ("9"): return("234561");
        case ("10"): return("31524");
        case ("11"): return("3142");
        case ("12"): return("236145");
        case ("13"): return("4123");
        case ("14"): return("3412");
        case ("15"): return("4312");
        case ("16"): return("23451");
        case ("17"): return("231");
        case ("18"): return("312");
        case ("19"): return("43521");
        case ("20"): return("24531");
        case ("21"): return("25413");
        case ("8"): return("Canon");
        case ("49"): return("546132");
        case ("50"): return("21345");
        case ("51"): return("615324");
        case ("52"): return("2134");
        case ("53"): return("Becket");
        case ("54"): return("none");
        case ("22"): return("2143");
        case ("24"): return("213465");
        case ("25"): return("231645");
        case ("26"): return("25134");
        case ("27"): return("2345671");
        case ("28"): return("4321");
        case ("29"): return("3214");
        case ("30"): return("other");
        case ("31"): return("34251");
        case ("32"): return("531642");
        case ("33"): return("45123");
        case ("34"): return("465213");
        case ("35"): return("51234");
        case ("36"): return("2416375");
        case ("37"): return("54132");
        case ("38"): return("45231");
        case ("39"): return("254163");
        case ("40"): return("53124");
        case ("41"): return("23514");
        case ("42"): return("213");
        case ("44"): return("31452");
        case ("45"): return("52341");
        case ("46"): return("Top Moves");
        case ("47"): return("362514");
        case ("48"): return("43152");
        case ("55"): return("41253");
        case ("56"): return("3756214");
        case ("57"): return("41532");
        case ("58"): return("34512");
        case ("59"): return("35214");
        case ("60"): return("264513");
        case ("61"): return("412563");
        case ("62"): return("541632");
        case ("63"): return("3124");
        case ("64"): return("2314");
        case ("65"): return("526341");
        case ("66"): return("7563412");
        case ("67"): return("645312");
        case ("68"): return("345612");
            case ("9999"): return ("");
        case ("69"): return("365214");} return "";
    }
    public String getType(String str) {
        switch (str) {
            case ("1"):
                return ("   " + "Reel");
            case ("2"):
                return("   " + "Jig");
            case ("3"):
                return("   " + "Strathspey");
            case ("4"):
                return("   " + "Medley");
            case ("5"):
                return("   " + "Waltz");
            case ("6"):
                return("   " + "Hornpipe");
            case ("7"):
                return("   " + "Step/Highland");
            case ("10"):
                return("   " + "Quadrille");
            case ("11"):
                return("   " + "March");
            case ("13"):
                return("   " + "Polka");
            case ("50"):
                return("   " + "Other");
            case ("98"):
                return("   " + "Listening");
            case ("99"):
                return("   " + "Unknown");
            case ("100"):
                return("   " + "3/4 time");
            case ("101"):
                return("   " + "Jig (9/8)");
        }return "";
    }
    public String getFormation (String str) {
        switch (str){
            case("103"): return("Longwise - any");
            case("104"): return("Longwise - 2");
            case("105"): return("Lines");
            case("1"): return("Longwise - 4");
            case("2"): return("Longwise - 3");
            case("3"): return("Longwise - 5");
            case("4"): return("Square");
            case("5"): return("Round the room");
            case("6"): return("Triangular");
            case("7"): return("Circle");
            case("8"): return("Longwise - 6");
            case("9"): return("Longwise - 7");
            case("10"): return("Longwise - 8");
            case("11"): return("Other");
            case("99"): return("Unknown");
            case("100"): return("Longwise & Square");
            case("101"): return("Hexagonal");
            case("102"): return("Pentagonal");
        }return "";
    }
    public String getCouples(String str) {
        switch (str) {
            case ("105"):
                return ("6 couples (4x,5x,6x)");
            case ("108"):
                return ("16 couples");
            case ("109"):
                return ("2 couples (1x)");
            case ("110"):
                return ("3 couples (1x)");
            case ("111"):
                return ("9 persons");
            case ("112"):
                return ("7 persons");
            case ("101"):
                return ("4w+2m");
            case ("113"):
                return ("any");
            case ("1"):
                return ("1 couple");
            case ("2"):
                return ("2 couples");
            case ("3"):
                return ("3 couples");
            case ("4"):
                return ("4 couples");
            case ("5"):
                return ("5 couples");
            case ("6"):
                return ("6 couples");
            case ("7"):
                return ("7 couples");
            case ("8"):
                return ("8 couples");
            case ("51"):
                return ("1 person");
            case ("53"):
                return ("3 persons");
            case ("54"):
                return ("4 persons");
            case ("55"):
                return ("5 persons");
            case ("56"):
                return ("2 trios");
            case ("98"):
                return ("other");
            case ("99"):
                return ("unknown");
            case ("100"):
                return ("4 couples (3x,4x)");
            case ("102"):
                return ("4 couples (2x,4x)");
            case ("104"):
                return ("3 couples (2x)");
            case ("114"):
                return ("4 couples (1x,3x)");
            case ("115"):
                return ("12 persons");
            case ("116"):
                return ("4 couples (1x,2x)");
            case ("106"):
                return ("2 couples (Glasgow Highl)");
            case ("107"):
                return ("4 couples (Glasgow Highl)");
        } return "";
    }
}
