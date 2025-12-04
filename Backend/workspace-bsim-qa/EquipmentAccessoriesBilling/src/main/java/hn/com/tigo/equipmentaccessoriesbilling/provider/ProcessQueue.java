package hn.com.tigo.equipmentaccessoriesbilling.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import hn.com.tigo.equipmentaccessoriesbilling.dto.*;
import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.LogsServiceModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsServicesService;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import hn.com.tigo.equipmentaccessoriesbilling.utils.ReadFilesConfig;

@Service
public class ProcessQueue {

    private static final Logger LOGGER = LogManager.getLogger(ProcessQueue.class);

    /**
     * The prop.
     */
    private AdditionalProperties prop;

    private final ILogsServicesService logsServicesService;

    public ProcessQueue(ILogsServicesService logsServicesService) {
        this.logsServicesService = logsServicesService;
    }


    /**
     * Gets the props.
     *
     * @return the props
     */
    public void getProps() {
        ReadFilesConfig readFile = new ReadFilesConfig();
        try {
            prop = readFile.readQueueProperties();
        } catch (IOException e) {
            LOGGER.error("No se logro leer archivo de propiedades" + e.getMessage(), e);
        }
    }

    /**
     * Gets the value prop.
     *
     * @param name the name
     * @return the value prop
     */
    private String getValueProp(String name) {

        for (int i = 0; i < prop.getQueueProperties().size(); i++) {
            if (prop.getQueueProperties().get(i).getName().equals(name)) {
                return prop.getQueueProperties().get(i).getValue();
            }
        }
        return null;
    }

    /**
     * Process trama.
     *
     * @param readConfig    the read config
     * @param startTime     the start time
     * @param tramaComplete the trama complete
     * @return the long
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public long processTrama(ReadFilesConfig readConfig, long startTime, String tramaComplete, ChannelEntity channelEntity) throws IOException {
        if (tramaComplete != null) {
            startTime = System.nanoTime();
            String[] trama = tramaComplete.split("\\|");
            String response = "";

            final ConfigEventDTO configEvent = readConfig.readConfigEvent();
            DetailEventDTO detailEvent = readConfig.getDetailEvent(configEvent, trama[0]);

            System.out.println("DETAIL EVENT" + detailEvent);

            if (detailEvent != null) {
                LOGGER.info("Trama: " + tramaComplete + " Evento: " + detailEvent.getName() + " ProductId: "
                        + detailEvent.getDefaultProduct());
                String request = obtainRequest(trama, detailEvent);

                System.out.println("request" + request);
                System.out.println("detailEvent" + detailEvent);

                if (request != null) {
                    response = methodPost(request);
                }

                // Creacion de log
                if (channelEntity.getLogs() == 1) {
                    LogsServiceModel logModel = new LogsServiceModel();
                    logModel.setRequest(request);
                    logModel.setResponse(response);
                    logModel.setReference(null);
                    logModel.setService("GENERATE TRAMA SERVICE");
                    logModel.setUserCreate(null);
                    logModel.setExecutionTime(System.currentTimeMillis());

                    logsServicesService.saveLog(logModel);
                }

            } else {
                LOGGER.info("Trama no aceptada, por no tener evento valido: " + tramaComplete);
            }
        }
        return startTime;
    }

    public ProcessResult processResendTrama(ReadFilesConfig readConfig, long startTime, String tramaComplete) throws IOException {
        if (tramaComplete != null) {
            startTime = System.nanoTime();
            String[] trama = tramaComplete.split("\\|");

            final ConfigEventDTO configEvent = readConfig.readConfigEvent();
            DetailEventDTO detailEvent = readConfig.getDetailEvent(configEvent, trama[0]);

            if (detailEvent != null) {
                LOGGER.info("Trama: " + tramaComplete + " Evento: " + detailEvent.getName() + " ProductId: "
                        + detailEvent.getDefaultProduct());
                String request = obtainRequest(trama, detailEvent);

                if (request != null) {
                    String response = methodPost(request);
                    // Verificar la respuesta del método POST
                    if (response != null && response.contains("\"code\":\"0\"")) {
                        return new ProcessResult(true, "Trama procesada exitosamente.");
                    } else {
                        return new ProcessResult(false, "Error al procesar la trama: " + response);
                    }
                } else {
                    return new ProcessResult(false, "Error al generar la solicitud para la trama.");
                }
            } else {
                LOGGER.info("Trama no aceptada, por no tener evento valido: " + tramaComplete);
                return new ProcessResult(false, "Trama no aceptada, por no tener evento valido: " + tramaComplete);
            }
        }
        return new ProcessResult(false, "La trama está vacía.");
    }

    /**
     * Obtain request.
     *
     * @param trama       the trama
     * @param detailEvent the detail event
     * @return the string
     */
    private String obtainRequest(String[] trama, DetailEventDTO detailEvent) {
        String request = null;

        NotifyMessageDTO notifyMessageDTO = new NotifyMessageDTO();
        String evenType = trama[0];
        String uuid = UUID.randomUUID().toString();
        Calendar cycleCalendar = Calendar.getInstance();
        final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String split = getValueProp("SUBSCRIBER_SPLIT");

        switch (evenType) {

            case "FACTURACION":
                String[] subscriber2 = trama[2].split(split);
                String subscId2 = "";
                if (subscriber2.length > 1) {
                    subscId2 = subscriber2[1];
                } else {
                    subscriber2 = trama[3].split(split);
                    subscId2 = subscriber2[1];
                }
                NotifyMessageDTO object2 = generatedRequest(trama, detailEvent, notifyMessageDTO, evenType, uuid, subscId2,
                        df.format(cycleCalendar.getTime()));
                Gson gson2 = new Gson();
                return gson2.toJson(object2);
            default:

                return request;
        }
    }

    /**
     * Generated request.
     *
     * @param trama            the trama
     * @param detailEvent      the detail event
     * @param notifyMessageDTO the notify message DTO
     * @param evenType         the even type
     * @param uuid             the uuid
     * @param subscriber       the subscriber
     * @param fecha            the fecha
     * @return the notify message DTO
     */
    private NotifyMessageDTO generatedRequest(String[] trama, DetailEventDTO detailEvent,
                                              NotifyMessageDTO notifyMessageDTO, String evenType, String uuid, String subscriber, String fecha) {
        notifyMessageDTO.setEventType(evenType);
        notifyMessageDTO.setChannelId(String.valueOf(detailEvent.getChannelId()));
        notifyMessageDTO.setOrderType(detailEvent.getDefaultOrder());
        notifyMessageDTO.setProductId(Integer.valueOf(detailEvent.getDefaultProduct()));
        notifyMessageDTO.setSubscriberId(subscriber);
        notifyMessageDTO.setDate(fecha);
        notifyMessageDTO.setTransactionId(uuid);
        notifyMessageDTO.setAdditionalsParams(getAdditionalParams(trama));
        return notifyMessageDTO;
    }

    /**
     * Gets the additional params.
     *
     * @param trama the trama
     * @return the additional params
     */
    private List<AttributeValuePair> getAdditionalParams(String[] trama) {

        List<AttributeValuePair> list = new ArrayList<AttributeValuePair>();

        for (int i = 0; i < trama.length; i++) {
            AttributeValuePair attributeValuePair = new AttributeValuePair();
            if (i == 0) {

                attributeValuePair.setAttribute("EVENT");
                attributeValuePair.setValue(trama[0]);
                list.add(attributeValuePair);
            } else if (i == 1) {

                attributeValuePair.setAttribute("SUBEVENT");
                attributeValuePair.setValue(trama[1]);
                list.add(attributeValuePair);
            } else {

                String tramaF = trama[i];
                if ((i + 1) == trama.length) {
                    tramaF = tramaF.replaceAll(" ", "");
                    tramaF = tramaF.replaceAll("\u0000", "");
                    tramaF = tramaF.replaceAll("\u000f", "");
                }

                String[] attribute = tramaF.split(getValueProp("SUBSCRIBER_SPLIT"));
                if (!attribute[0].equals("")) {
                    attributeValuePair.setAttribute(attribute[0]);
                    attributeValuePair.setValue(attribute.length > 1 ? attribute[1] : "");
                    list.add(attributeValuePair);
                }
            }
        }
        return list;
    }

    /**
     * Method post.
     *
     * @param request the request
     * @return the string
     */
    private String methodPost(String request) {
        StringBuilder content = null;
        try {

            String urlFinal = getValueProp("URL_NOTIFY_EVENT");
            LOGGER.info(urlFinal);

            URL url = new URL(urlFinal);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestProperty("Content-Type", "application/json");

            byte[] outputInBytes = request.getBytes("UTF-8");
            OutputStream os = con.getOutputStream();
            os.write(outputInBytes);
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            LOGGER.info(content.toString());
        } catch (Exception e) {
            LOGGER.error("ERROR: en api Notifyevent: " + e.getMessage());
            return "";
        }
        return content.toString();
    }

}
