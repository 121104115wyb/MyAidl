// IMyAidlInterface.aidl
package com.ytlz.server;
import com.ytlz.server.Book;
// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    //编写客户端可以调用的AIDL接口，
    void addBook(in Book book);//一定要加 in
    List<Book> getBooks();
    void setTag(in String tag);
    String getTag();
    void setNum(in int num);
    int getNum();
}
