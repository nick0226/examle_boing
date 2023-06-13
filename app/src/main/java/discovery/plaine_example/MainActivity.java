package discovery.plaine_example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import discovery.plaine_example.Adapter.CardAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ADD_NEW_ARTICLE = 1;

    int[] imageResArray = {R.drawable.boing_747_image, R.drawable.boing_747_image, R.drawable.boing_747_image};
    String[] mainTextArray = {"Boeing 747", "Boeing 747", "Boeing 747"};
    String[] secondaryTextArray = {"1970", "1970", "1970"};

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardItem> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        cardList = new ArrayList<>();
        cardAdapter = new CardAdapter(this, cardList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardAdapter);
        prepareCardData();


        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                // get position list
                CardItem clickedItem = cardList.get(position);

                // animation
                Animation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f);
                animation.setDuration(200);

                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                if (viewHolder != null) {
                    TextView mainText = viewHolder.itemView.findViewById(R.id.mainText);
                    TextView subText = viewHolder.itemView.findViewById(R.id.subText);
                    ImageView imageView = viewHolder.itemView.findViewById(R.id.imageView);
                    mainText.startAnimation(animation);
                    subText.startAnimation(animation);
                    imageView.startAnimation(animation);
                }

                Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        for (int i = 0; i < mainTextArray.length; i++) {
            menu.add(Menu.NONE, i, Menu.NONE, mainTextArray[i]);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int position = item.getItemId();
        if (position >= 0 && position < mainTextArray.length) {

            String selectedMenuItem = mainTextArray[position];
            Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareCardData() {
        for (int i = 0; i < mainTextArray.length; i++) {
            CardItem cardItem = new CardItem(mainTextArray[i], secondaryTextArray[i], imageResArray[i]);
            cardList.add(cardItem);
        }
        cardAdapter.notifyDataSetChanged();
    }

    // button for add new article
    public void addNewArticle(View view) {
        Intent intent = new Intent(MainActivity.this, AddNewArticle.class);
        startActivityForResult(intent, REQUEST_ADD_NEW_ARTICLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_NEW_ARTICLE && resultCode == RESULT_OK) {
            String newTitle = data.getStringExtra(AddNewArticle.CONST_KEY_TITLE);
            String newContent = data.getStringExtra(AddNewArticle.CONST_KEY_CONTENT);

            int[] newImageResArray = createNewArray(imageResArray, R.drawable.boing_747_image);
            String[] newMainTextArray = createNewArray(mainTextArray, newTitle);
            String[] newSecondaryTextArray = createNewArray(secondaryTextArray, newContent);

            imageResArray = newImageResArray;
            mainTextArray = newMainTextArray;
            secondaryTextArray = newSecondaryTextArray;

            prepareCardData();
        }
    }

    private int[] createNewArray(int[] originalArray, int newItem) {
        int[] newArray = new int[originalArray.length + 1];
        System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);
        newArray[newArray.length - 1] = newItem;
        return newArray;
    }

    private String[] createNewArray(String[] originalArray, String newItem) {
        String[] newArray = new String[originalArray.length + 1];
        System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);
        newArray[newArray.length - 1] = newItem;
        return newArray;
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadArticleData();
    }

    // clean array
    private void loadArticleData() {
        imageResArray = new int[0];
        mainTextArray = new String[0];
        secondaryTextArray = new String[0];
    }

}