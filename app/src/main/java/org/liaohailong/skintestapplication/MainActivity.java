package org.liaohailong.skintestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import org.liaohailong.framelibrary.skin.SkinManager;

import java.io.File;

public class MainActivity extends BaseActivity implements LayoutInflater.Factory2,
        View.OnClickListener {
    private Button skinChangeBtn;
    private Button skinDefaultBtn;
    private Button jumpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skinChangeBtn = findViewById(R.id.skin_change_btn);
        skinDefaultBtn = findViewById(R.id.skin_default_btn);
        jumpBtn = findViewById(R.id.jump_btn);
        skinChangeBtn.setOnClickListener(this);
        skinDefaultBtn.setOnClickListener(this);
        jumpBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int result = 0;
        switch (v.getId()) {
            case R.id.skin_change_btn://替换皮肤
                //从服务器上下载
                String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "red.skin";
                //换肤
                result = SkinManager.getInstance().loadSkin(this, skinPath);
                break;
            case R.id.skin_default_btn://恢复默认皮肤
                //恢复默认皮肤
                result = SkinManager.getInstance().restoreDefault();
                break;
            case R.id.jump_btn://跳转
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
