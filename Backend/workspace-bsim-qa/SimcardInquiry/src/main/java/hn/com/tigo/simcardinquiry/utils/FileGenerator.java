package hn.com.tigo.simcardinquiry.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {

    public String generateFile(String fileName, String orderDate, String customerName, Long quantity,
                               String type, String artwork, String profile, String batch,
                               String transportKey, String simReference, String graphRef,
                               String imsi, String serNb) {
        StringBuilder content = new StringBuilder();
        content.append("************************************************************************************\n");
        content.append("*      HEADER   \n");
        content.append("************************************************************************************\n");
        content.append("File Name: ").append(fileName).append("\n");
        content.append("Order Date: ").append(orderDate).append("\n");
        content.append("Customer Name: ").append(customerName).append("\n");
        content.append("Quantity: ").append(quantity).append("\n");
        content.append("Type: ").append(type).append("\n");
        content.append("ArtWork: ").append(artwork).append("\n");
        content.append("Profile: ").append(profile).append("\n");
        content.append("Batch: ").append(batch).append("\n");
        content.append("Transport Key: ").append(transportKey).append("\n");
        content.append("SIM-Reference: ").append(simReference).append("\n");
        content.append("Graph ref: ").append(graphRef).append("\n");
        content.append("************************************************************************************\n");
        content.append("*           INPUT VARIABLES DESCRIPTION    \n");
        content.append("************************************************************************************\n");
        content.append("Var in list:\n");
        content.append("IMSI: ").append(imsi).append("\n");
        content.append("Ser NB: ").append(serNb).append("\n");
        content.append("************************************************************************************\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content.toString());
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating file";
        }
    }
}
