package edu.gsu.student.shiftcipher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private String inputFile;
    File file;

    @BindView(R.id.text_input)
    TextView inputPathText;

    @BindView(R.id.spinner_key)
    Spinner keySpinner;

    @BindView(R.id.button_encrypt)
    Button buttonEncrypt;


    @OnClick(R.id.button_fileinput)
    public void toFileExplorer(){
        toActivity(FileExplorerActivity.class);
    }

    @OnClick(R.id.button_encrypt)
    public void encrypt(){
        toSaveActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        buttonEncrypt.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            inputFile = extras.getString("inputFile");
            inputPathText.setText(inputFile);
            file = new File(inputFile);
            toastShort(file.getName());
            buttonEncrypt.setEnabled(true);
        }

        String[] keys = new String[]{"guess", "1", "2", "3", "4"
                , "5", "6", "7", "8", "9", "10", "11"
                , "12", "13", "14", "15", "16", "17", "18", "19", "20"
                , "21", "22", "23", "24", "25", "26"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, keys);
        keySpinner.setAdapter(adapter);

    }

    private void toSaveActivity(){
        Intent intent = new Intent(this, FileSaveActivity.class);
        intent.putExtra("inputFile", inputFile);
        intent.putExtra("key", keySpinner.getSelectedItem().toString());
        startActivity(intent);

    }
}
