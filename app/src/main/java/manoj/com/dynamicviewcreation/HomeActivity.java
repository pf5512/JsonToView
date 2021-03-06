package manoj.com.dynamicviewcreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import manoj.com.dynamicview.ConverterJsonToWidget;
import manoj.com.dynamicview.ConverterWidgetToView;
import manoj.com.dynamicview.IOnClickListener;
import manoj.com.dynamicview.widget.Widget;
import manoj.com.dynamicviewcreation.chat.ChatActivity;

public class HomeActivity extends AppCompatActivity implements IOnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.container);
        addDynamicViewInContainer(viewContainer);

        startChatActivity();
    }

    private void startChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    private void addDynamicViewInContainer(FrameLayout viewContainer) {

        JSONObject jsonObject = FileReader.getFileData(this, "testFormat.json");
        if (jsonObject != null) {
            Log.d("manoj", "Input Json Data :" + jsonObject.toString());
        }

        try {
            Widget widget = ConverterJsonToWidget.parseWidgetJsonObject(jsonObject);
            if (widget != null) {
                widget.registerOnClickListener(this);
                Log.d("manoj", "Json to Widget Data :" + widget.toString());
                View view = ConverterWidgetToView.buildView(this, widget, viewContainer);
                if (view != null) {
                    viewContainer.addView(view);
                }
            } else {
                Log.d("manoj", "Widget is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDynamicViewClick(View view, String value) {
        Log.d("manoj", "Dynamic View Clicked : " + value);
        startChatActivity();
    }
}
