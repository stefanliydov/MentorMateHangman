package com.mentormate.io;

public interface IO extends AutoCloseable {

    public String read();

    public void write(String str);

}
