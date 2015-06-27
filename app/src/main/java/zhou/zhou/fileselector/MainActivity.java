package zhou.zhou.fileselector;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import zhou.tools.fileselector.FileSelector;
import zhou.tools.fileselector.FileSelectorActivity;
import zhou.tools.fileselector.FileSelectorAlertDialog;
import zhou.tools.fileselector.FileSelectorDialog;
import zhou.tools.fileselector.config.FileConfig;
import zhou.tools.fileselector.config.FileTheme;
import zhou.tools.fileselector.utils.FileFilter;


public class MainActivity extends ActionBarActivity {

    private Spinner spinnerTheme;
    private Spinner spinnerFilter;
    private CheckBox checkBox;
    private String[] themes;
    private String[] filters;
    private FileConfig fileConfig;
    private CheckBox showHit;
    private CheckBox multi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileConfig = new FileConfig();
        initView();
    }

    private void initView() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileSelectorDialog fileDialog = new FileSelectorDialog();
                fileDialog.setOnSelectFinish(new FileSelectorDialog.OnSelectFinish() {
                    @Override
                    public void onSelectFinish(ArrayList<String> paths) {
                        Toast.makeText(getApplicationContext(), paths.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putSerializable(FileConfig.FILE_CONFIG, fileConfig);
                fileDialog.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fileDialog.show(ft, "fileDialog");
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FileSelectorActivity.class);
                fileConfig.startPath = Environment.getExternalStorageDirectory().getPath();

                fileConfig.rootPath = "/";
                intent.putExtra(FileConfig.FILE_CONFIG, fileConfig);
                startActivityForResult(intent, 0);
            }
        });

        spinnerTheme = (Spinner) findViewById(R.id.spinner_theme);
        spinnerFilter = (Spinner) findViewById(R.id.spinner_filter);
        checkBox = (CheckBox) findViewById(R.id.checkBox_mode);

        themes = getResources().getStringArray(R.array.theme_selects);
        filters = getResources().getStringArray(R.array.filter_selects);

        ArrayAdapter<String> adapterTheme = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, themes);
        ArrayAdapter<String> adapterFilter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filters);

        spinnerTheme.setAdapter(adapterTheme);
        spinnerFilter.setAdapter(adapterFilter);

        spinnerTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int t;
                switch (position) {
                    case 1:
                        t = FileTheme.THEME_WHITE;
                        break;
                    case 2:
                        t = FileTheme.THEME_BLACK;
                        break;
                    case 3:
                        t = FileTheme.THEME_GREY;
                        break;
                    default:
                        t = FileTheme.THEME_WHITE;
                }
                fileConfig.theme = t;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int t;
                switch (position) {
                    case 1:
                        t = FileFilter.FILTER_NONE;
                        break;
                    case 2:
                        t = FileFilter.FILTER_IMAGE;
                        break;
                    case 3:
                        t = FileFilter.FILTER_TXT;
                        break;
                    case 4:
                        t = FileFilter.FILTER_VIDEO;
                        break;
                    case 5:
                        t = FileFilter.FILTER_AUDIO;
                        break;
                    default:
                        t = FileFilter.FILTER_NONE;
                }
                fileConfig.filterModel = t;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fileConfig.positiveFiter = isChecked;
            }
        });

        showHit = (CheckBox) findViewById(R.id.checkBox_show_hit);
        showHit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fileConfig.showHiddenFiles = isChecked;
            }
        });

        multi = (CheckBox) findViewById(R.id.checkBox_multi);
        multi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fileConfig.multiModel = isChecked;
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileSelectorAlertDialog fileSelectorAlertDialog=new FileSelectorAlertDialog(MainActivity.this);
                fileSelectorAlertDialog.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> list = data.getStringArrayListExtra(FileSelector.RESULT);
                Toast.makeText(getApplicationContext(), list.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
