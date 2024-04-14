package com.riverburg.eUniversity.service.configuration;

import com.riverburg.eUniversity.repository.ConfigurationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    @Override
    public void addDateConfig(String name, Date value, String pattern) {
        var config = configurationRepository.findByName(name);

        if (value == null) {
            config.setValue(null);
        } else {
            String dateFormat = new SimpleDateFormat(pattern).format(value);

            config.setValue(dateFormat);
        }

        configurationRepository.save(config);
    }

    @Override
    public void addStringConfig(String name, String value) {
        var config = configurationRepository.findByName(name);

        config.setValue(value);

        configurationRepository.save(config);
    }

    @Override
    public void addIntegerConfig(String name, String value) {
        var config = configurationRepository.findByName(name);

        config.setValue(value);

        configurationRepository.save(config);
    }

    @Override
    public String getStringConfig(String name) {
        var value = configurationRepository.getValueByName(name);
        return value;
    }

    @Override
    public int getIngConfig(String name) {
        var value = configurationRepository.getValueByName(name);
        return Integer.parseInt(value);
    }

    @Override
    public Date getDateConfig(String name) {
        var value = configurationRepository.getValueByName(name);
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(value);
        } catch (ParseException ex) {
            return null;
        }
    }

    @Override
    public boolean isActive(String name) {
        var value = configurationRepository.getValueByName(name);

        return value != "false" && value != "False" && value != null;
    }
}
