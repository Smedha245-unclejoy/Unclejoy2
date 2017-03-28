package com.example.medhasingh.unclejoy2;

/**
 * Created by medha singh on 6/22/2016.
 */
public class Uncle {

        private String title,id;



        public Uncle() {
        }

        public Uncle(String title, String id) {
            this.title = title;
            this.id=id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String name) {
            this.title = name;
        }

        public String getid(){
            return id;
        }
    public void setid(String l){
        this.id=l;
    }

}
