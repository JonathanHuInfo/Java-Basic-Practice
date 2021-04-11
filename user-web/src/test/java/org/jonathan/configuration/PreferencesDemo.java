package org.jonathan.configuration;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @description: 配置Demo
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-03-29 23:22
 **/
public class PreferencesDemo {
    public static void main(String[] args) {
        Preferences userPreferences = Preferences.userRoot();
        try {
            String[] userCildrenNames = userPreferences.childrenNames();
            for (String s : userCildrenNames) {
                System.out.println(s);
            }
            System.out.println(userPreferences.absolutePath());
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }
}
