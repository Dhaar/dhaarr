package com.example.boopathyraj.lims;

/**
 * Created by Boopathyraj on 3/14/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookResponse {

    //@SerializedName("0")
    //@Expose
    //private String _0;
    @SerializedName("book_status")
    @Expose
    private String bookStatus;
    @SerializedName("1")
    @Expose
    private String _1;
    @SerializedName("bookname")
    @Expose
    private String bookname;
    @SerializedName("2")
    @Expose
    private String _2;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("3")
    @Expose
    private String _3;
    @SerializedName("bookid")
    @Expose
    private String bookid;

//    public String get0() {
  //      return _0;
   // }

    //public void set0(String _0) {
      //  this._0 = _0;
    //}

    //public String getBookStatus() {
       // return bookStatus;
    //}

    //public void setBookStatus(String bookStatus) {
       // this.bookStatus = bookStatus;
    //}

    public String get1() {
        return _1;
    }

    public void set1(String _1) {
        this._1 = _1;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String get2() {
        return _2;
    }

    public void set2(String _2) {
        this._2 = _2;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String get3() {
        return _3;
    }

    public void set3(String _3) {
        this._3 = _3;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }
}

