package com.eazybytes.eazyschool.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SchoolInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("App Name", "SchoolApp");
        map.put("App Description", "School Web App for Students and Admin");
        map.put("App Version", "1.0.0");
        map.put("Contact Email", "info@school.com");
        map.put("Contact Mobile", "+90 123 456 123");
        builder.withDetail("school-info", map);
    }
}
