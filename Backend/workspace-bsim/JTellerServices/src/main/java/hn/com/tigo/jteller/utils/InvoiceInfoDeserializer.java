package hn.com.tigo.jteller.utils;

import com.google.gson.*;
import hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced.ArsInvoiceInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InvoiceInfoDeserializer implements JsonDeserializer<List<ArsInvoiceInfo>> {
    @Override
    public List<ArsInvoiceInfo> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<ArsInvoiceInfo> invoiceInfos = new ArrayList<>();
        if (json.isJsonArray()) {
            for (JsonElement element : json.getAsJsonArray()) {
                invoiceInfos.add(context.deserialize(element, ArsInvoiceInfo.class));
            }
        } else if (json.isJsonObject()) {
            invoiceInfos.add(context.deserialize(json, ArsInvoiceInfo.class));
        }
        return invoiceInfos;
    }
}