package hn.com.tigo.equipmentaccessoriesbilling.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import com.google.gson.Gson;

import hn.com.tigo.equipmentaccessoriesbilling.dto.AdditionalProperties;
import hn.com.tigo.equipmentaccessoriesbilling.dto.ConfigEventDTO;
import hn.com.tigo.equipmentaccessoriesbilling.dto.ConfigQueueDTO;
import hn.com.tigo.equipmentaccessoriesbilling.dto.DetailEventDTO;

public class ReadFilesConfig {
    private static final String PATH_CONFIG_EVENT = "/configEventOCEP.json";
    private static final String PATH_CONFIG_QUEUE = "/configQueue.json";
    private static final String PATH_PROP_QUEUE = "/queueProp.json";

    private final Gson json = new Gson();

    public ReadFilesConfig() {
    }

    public ConfigEventDTO readConfigEvent() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PATH_CONFIG_EVENT)))) {
            return json.fromJson(bufferedReader, ConfigEventDTO.class);
        }
    }

    public DetailEventDTO getDetailEvent(final ConfigEventDTO obj, final String eventType) {
        for (DetailEventDTO objDetail : obj.getEvents()) {
            if (eventType.equalsIgnoreCase(objDetail.getName())) {
                return objDetail;
            }
        }
        return null;
    }

    public ConfigQueueDTO readConfigQueue() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PATH_CONFIG_QUEUE)))) {
            return json.fromJson(bufferedReader, ConfigQueueDTO.class);
        }
    }

    public AdditionalProperties readQueueProperties() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PATH_PROP_QUEUE)))) {
            return json.fromJson(bufferedReader, AdditionalProperties.class);
        }
    }
}
