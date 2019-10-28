package com.ytlz.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {
    private List<Book> bookList = new ArrayList<>();

    private int num = 0;

    private String tag = "empty";

    public AIDLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return server_binder;
    }

    IMyAidlInterface.Stub server_binder = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this) {
                if (bookList == null) return;
                if (book == null) return;
                bookList.add(book);
            }

        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            return bookList;
        }

        @Override
        public void setTag(String tag) throws RemoteException {
            synchronized (this) {
                if (!TextUtils.isEmpty(tag)) {
                    AIDLService.this.tag = tag;
                }
            }
        }

        @Override
        public String getTag() throws RemoteException {
            return tag;
        }

        @Override
        public void setNum(int num) throws RemoteException {
            synchronized (this) {
                AIDLService.this.num = num;
            }
        }

        @Override
        public int getNum() throws RemoteException {
            return num;
        }
    };


}
