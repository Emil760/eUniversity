package com.riverburg.eUniversity.service.configuration;

import java.util.Date;

public interface ConfigurationService {

    void addDateConfig(String name, Date value, String pattern);

    void addStringConfig(String name, String value);

    void addIntegerConfig(String name, String value);

    String getStringConfig(String name);

    int getIngConfig(String name);

    Date getDateConfig(String name);

    boolean isActive(String name);
}
