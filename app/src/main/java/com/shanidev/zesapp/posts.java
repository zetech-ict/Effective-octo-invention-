package com.shanidev.zesapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class posts implements List<posts> {
        public posts() {
        }

        String description;

        public void clear() {
        }

    @Override
    public posts get(int i) {
        return null;
    }

    @Override
    public posts set(int i, posts posts) {
        return null;
    }

    @Override
    public void add(int i, posts posts) {

    }

    @Override
    public posts remove(int i) {
        return null;
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<posts> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<posts> listIterator(int i) {
        return null;
    }

    @NonNull
    @Override
    public List<posts> subList(int i, int i1) {
        return null;
    }

    public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUdp() {
            return udp;
        }

        public void setUdp(String udp) {
            this.udp = udp;
        }

        public String getUemail() {
            return uemail;
        }

        public void setUemail(String uemail) {
            this.uemail = uemail;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUimage() {
            return uimage;
        }

        public void setUimage(String uimage) {
            this.uimage = uimage;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getPlike() {
            return plike;
        }

        public void setPlike(String plike) {
            this.plike = plike;
        }

        String pid;

        public String getPcomments() {
            return pcomments;
        }

        public void setPcomments(String pcomments) {
            this.pcomments = pcomments;
        }

        public posts(String description, String pid, String ptime, String pcomments, String title, String udp, String uemail, String uid, String uimage, String uname, String plike) {
            this.description = description;
            this.pid = pid;
            this.ptime = ptime;
            this.pcomments = pcomments;
            this.title = title;
            this.udp = udp;
            this.uemail = uemail;
            this.uid = uid;
            this.uimage = uimage;
            this.uname = uname;
            this.plike = plike;
        }

        String ptime, pcomments;

        String title;

        String udp;
        String uemail;
        String uid;
        String uimage;

        String uname, plike;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator<posts> iterator() {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        return null;
    }

    public boolean add(posts posts) {
        return false;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends posts> collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, @NonNull Collection<? extends posts> collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        return false;
    }
}
