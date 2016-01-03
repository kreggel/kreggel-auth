package de.kreggel.auth.service.status;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonTypeName(value = "version")
public class Version {
    private String branch;
    private String number;
    private String version;
    private String timestamp;

    public String getBranch() {
        return branch;
    }

    public String getNumber() {
        return number;
    }

    public String getVersion() {
        return version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public static Version fromBuildInfo() {
        Version version = new Version();
        version.branch = de.kreggel.auth.core.api.Version.getBranch();
        version.number = de.kreggel.auth.core.api.Version.getScmRevision();
        version.version = de.kreggel.auth.core.api.Version.getVersion();
        version.timestamp = de.kreggel.auth.core.api.Version.getBuildTimestamp();

        return version;
    }

}
