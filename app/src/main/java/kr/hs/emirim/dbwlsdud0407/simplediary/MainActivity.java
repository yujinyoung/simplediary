package kr.hs.emirim.dbwlsdud0407.simplediary;

import android.content.Context;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
     DatePicker datePicker;
    EditText editdiary;
    Button but_save;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker =(DatePicker)findViewById(R.id.date_picker);
        editdiary=(EditText)findViewById(R.id.edit_content);
        but_save=(Button)findViewById(R.id.but_save);

        Calendar calendar=Calendar.getInstance();
        final int nowyear=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH)+1;
        final int date=calendar.get(Calendar.DATE);

        datePicker.init(nowyear, month, date, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                filename=nowyear+"_"+(month+1)+"_"+date+".txt";
                String content=ReadDiary(filename);
                editdiary.setText(content);
                but_save.setEnabled(true);
            }
        });

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream out=openFileOutput(filename, Context.MODE_WORLD_WRITEABLE);
                    String diarycontents=editdiary.getText().toString();
                    out.write(diarycontents.getBytes());
                    out.close();
                    Toast.makeText(getApplicationContext(),"저장이 완료됨", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }
    String readDiary(String filename){
        String diaryContents=null;
        try {
            FileInputStream in=openFileInput(filename);
            byte[] txt=new byte[500];
            in.read(txt);
            in.close();
            diaryContents=new String(txt);
            but_save.setText("수정 하기");
        } catch (IOException e) {
           editdiary.setHint("읽어올 일기가 없습니다.");
            but_save.setText("새로 저장");
        }


        return null;
    }


}
