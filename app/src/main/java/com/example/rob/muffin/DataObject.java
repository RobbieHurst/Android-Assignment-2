package com.example.rob.muffin;

/**
 * Created by Rob on 5/25/2017.
 */


public class DataObject {       //DataObject class that is used to display for both the database, and file app.

        private String mText1;      //Declaration of placeholder Strings,
        private String mText2;
        private String mText3;

        DataObject (String text1, String text2, String text3){
            mText1 = text1;
            mText2 = text2;     //Contructor.
            mText3 = text3;
        }
        DataObject(){

        }
        //Getters and Setters.

        public String getmText1() {
            return mText1;
        }

        public void setmText1(String mText1) {
            this.mText1 = mText1;
        }

        public String getmText2() {
            return mText2;
        }

        public void setmText2(String mText2) {
            this.mText2 = mText2;
        }

        public String getmText3() {
        return mText3;
    }

        public void setmText3(String mText3) {
        this.mText3 = mText3;
    }

}
