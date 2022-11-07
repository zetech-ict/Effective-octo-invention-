package com.shanidev.zesapp;

import androidx.appcompat.app.AppCompatActivity;

public class users extends AppCompatActivity {
    public Object getUid() {
        return null;
    }

    public String getName() {
        return null;
    }

    public String getEmail() {
        return null;
    }

    public String getImage() {
        return null;
    }

    public class ModelUsers {
        String name;

        public void users() {

        }

        String onlineStatus;
        String typingTo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTypingTo() {
            return typingTo;
        }

        public void setTypingTo(String typingTo) {
            this.typingTo = typingTo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public ModelUsers(String name, String onlineStatus, String typingTo, String email, String image, String uid) {
            this.name = name;
            this.onlineStatus = onlineStatus;
            this.typingTo = typingTo;
            this.email = email;
            this.image = image;
            this.uid = uid;
        }

        String email;

        String image;

        String uid;
    }
}