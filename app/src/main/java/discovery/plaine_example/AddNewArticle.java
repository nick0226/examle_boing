package discovery.plaine_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewArticle extends AppCompatActivity {

    public static final String CONST_KEY_TITLE= "New Title";
    public static final String CONST_KEY_CONTENT = "New Content";
    private EditText ed_new_title;
    private EditText add_new_content;
    private Button btn_choice_img;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_article);

        ed_new_title = findViewById(R.id.ed_new_title);
        add_new_content = findViewById(R.id.add_new_content);
        btn_choice_img = findViewById(R.id.btn_choice_img);

    }

    public void newArticleToMain(View view) {
        Intent intent = new Intent();
        intent.putExtra(CONST_KEY_TITLE, ed_new_title.getText().toString());
        intent.putExtra(CONST_KEY_CONTENT, add_new_content.getText().toString());
        setResult(RESULT_OK, intent);
        finish();

        Toast toast = Toast.makeText(this, "New article created", Toast.LENGTH_LONG);
        toast.show();
    }

}
