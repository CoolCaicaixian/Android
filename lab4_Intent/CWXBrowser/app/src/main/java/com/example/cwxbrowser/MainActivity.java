package com.example.cwxbrowser;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import android.content.Intent;
import java.net.URL;
import android.webkit.WebViewClient;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String strUriHome = "http://www.baidu.com";//默认首页
    WebView webView;//浏览器
    EditText edtttUri;//地址栏
    Button btnGo;//打开网页按钮
    Button btnHome;//首页按钮
    Button btnBack;//返回按钮
    Button btnForward;//前进按钮
    Button btnRefresh;//刷新按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView)findViewById(R.id.webView);
        edtttUri = (EditText)findViewById(R.id.edtttUri);
        btnGo = (Button)findViewById(R.id.btnGo);
        btnHome = (Button)findViewById(R.id.btnHome);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnForward = (Button)findViewById(R.id.btnForeward);
        btnRefresh = (Button)findViewById(R.id.btnRefresh);

        webView.getSettings().setJavaScriptEnabled(true);//让浏览器支持javascript

        Intent intent = getIntent();//获取意图

        Uri data = intent.getData();//获取uri
        URL url = null;

        try {
            url = new URL(data.getScheme(), data.getHost(),
                    data.getPath());//返回当前链接使用的协议、主机、文件路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        startBrowser(url);


        btnHome.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnForward.setOnClickListener(this); btnGo.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //转到
            case R.id.btnGo:
                try {
                    String strUri = uriHttpFirst(edtttUri.getText().toString());//网址协议判断
                    webView.loadUrl(strUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //首页
            case R.id.btnHome:
                try {
                    webView.loadUrl(strUriHome);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //返回
            case R.id.btnBack:
                try {
                    webView.goBack();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //前进
            case R.id.btnForeward:
                try {
                    webView.goForward();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //刷新
            case R.id.btnRefresh:
                try {
                    webView.reload();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    //地址HTTP协议判断，无HTTP打头的，增加http://，并返回。
    private String uriHttpFirst(String strUri){

        if(strUri.indexOf("http://",0) != 0 && strUri.indexOf("https://",0) != 0 ){
            strUri = "http://" + strUri;
        }

        return strUri;
    }
    private void startBrowser(URL url) {
        WebView webView = (WebView) findViewById(R.id.webView);
        //WebView加载web资源
        webView.loadUrl(url.toString());
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });}

}
