package com.ytlz.myaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ytlz.server.AIDLService;
import com.ytlz.server.Book;
import com.ytlz.server.IMyAidlInterface;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button bindBtn, unbindBtn, addBook, getAddBook;
    private int bookNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    void initViews() {
        bindBtn = findViewById(R.id.bind);
        unbindBtn = findViewById(R.id.unbind);
        addBook = findViewById(R.id.addbook);
        getAddBook = findViewById(R.id.getbook);

        bindBtn.setOnClickListener(v -> {
            bindAidlService();

        });

        unbindBtn.setOnClickListener(v -> {
            UnbindAidlService();

        });

        addBook.setOnClickListener(v -> {
            addBook_AidlService();

        });
        getAddBook.setOnClickListener(v -> {

            getBooks_AidlService();
        });


    }

    private IMyAidlInterface mService_face;
    private static final String TAG = "Main_Client";
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: ");
            mService_face = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: ");
        }
    };

    public void bindAidlService() {
        Intent intent_service = new Intent();
        intent_service.setPackage("com.ytlz.server.AIDLService"); //设置需要绑定的服务端的包名，不是服务端Service的包名
        intent_service.setAction("server.aidl.service.action1");//设置你所需调用服务的意图
        boolean successful = bindService(intent_service, mConnection, BIND_AUTO_CREATE);
        Log.e(TAG, "bindAidlService: successful=" + successful);
    }

    public void UnbindAidlService() {
        if (mConnection != null) {
            unbindService(mConnection);
            Log.e(TAG, "UnbindAidlService: ");
        }
    }

    public void addBook_AidlService() {
        try {
            if (mService_face != null) {
                int currentNum = bookNum++;
                mService_face.addBook(new Book(currentNum, String.valueOf(currentNum), 30.5));
                Log.e(TAG, "addBook_AidlService: ");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getBooks_AidlService() {
        try {
            if (mService_face != null) {
                List<Book> books = mService_face.getBooks();
                for (Book book : books) {
                    Log.e(TAG, "addBook_AidlService: book=" + book.name);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setTag_AidlService(View view) {
        try {
            if (mService_face != null) {
                mService_face.setTag("setTag_AidlService");
                Log.e(TAG, "setTag_AidlService: ");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getTag_AidlService(View view) {
        try {
            if (mService_face != null) {
                String tag = mService_face.getTag();
                Log.e(TAG, "getTag_AidlService: tag=" + tag);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setNum_AidlService(View view) {
        try {
            if (mService_face != null) {
                mService_face.setNum(27);
                Log.e(TAG, "setNum_AidlService: ");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getNum_AidlService(View view) {
        try {
            if (mService_face != null) {
                int num = mService_face.getNum();
                Log.e(TAG, "getNum_AidlService: num=" + num);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
