package com.mentormate.word_loader;

import java.util.Set;

public interface WordLoader {

     Set<String> getCategories();
     String getRandomWordByCategory(String category);
}
