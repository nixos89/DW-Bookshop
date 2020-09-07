package com.nikolas.master_thesis.config;

import com.nikolas.master_thesis.DropwizardMasterThesisConfiguration;
import com.zaxxer.hikari.HikariConfig;

public interface HikariConfigurationProvider {
    DropwizardMasterThesisConfiguration getHikariConfiguration();
}
