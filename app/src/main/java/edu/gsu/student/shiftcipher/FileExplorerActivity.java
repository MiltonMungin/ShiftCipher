package edu.gsu.student.shiftcipher;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileExplorerActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private String root = Environment.getExternalStorageDirectory().getPath();
    String[] files;

    @BindView(R.id.fileList) ListView lv;
    @BindView(R.id.text_file_path) TextView displayPath;
    @BindView(R.id.button_up_to)
    Button upToButton;

    @OnClick(R.id.button_up_to)
    public void upAFolder(){
        upTo();
    }

    @OnClick(R.id.button_back_to_main)
    public void toMainActivity(){
        toActivity(MainActivity.class);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer);
        ButterKnife.bind(this);

        upToButton();

        ArrayList<String> FilesInFolder = GetFiles(root);

        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, FilesInFolder));
        lv.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        itemClick(files[position]);
    }

    private void itemClick(String item){
        root += "/" + item;

        upToButton();

        ArrayList<String> FilesInFolder = GetFiles(root);

        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, FilesInFolder));
        lv.setOnItemClickListener(this);
    }


    private void upTo(){

        root = root.substring(0, root.lastIndexOf("/"));

        upToButton();

        ArrayList<String> FilesInFolder = GetFiles(root);

        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, FilesInFolder));
        lv.setOnItemClickListener(this);
    }

    public ArrayList<String> GetFiles(String DirectoryPath) {
        displayPath.setText(root);

        ArrayList<String> myFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        if(!f.isDirectory()){
            if(f.getName().toLowerCase().endsWith(".txt")){
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("inputFile", root);
                startActivity(intent);
            }else {
                displayPath.setText(root + " is a invalid directory");
            }
        }
        files = f.list();

        if(files != null) {
            for (int i = 0; i < files.length; i++) {
                myFiles.add(files[i]);
            }
        }
        return myFiles;
    }

    private void upToButton(){
        if(root.equals(Environment.getExternalStorageDirectory().getPath())){
            upToButton.setEnabled(false);
        }else{
            upToButton.setEnabled(true);
        }
    }
}
