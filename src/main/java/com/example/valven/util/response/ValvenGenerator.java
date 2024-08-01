package com.example.valven.util.response;

import com.example.valven.util.ValvenUtil;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;


public class ValvenGenerator {

    private ValvenGenerator() {
    }

    public static <T> ValvenResponse<T> generateSignResponse(T payload, Object... parametersWithOrderVersionReferenceIdExtras) {

        ValvenResponse<T> tValvenResponse;

        if (payload instanceof Collection) {
            tValvenResponse = new ValvenResponse.SignResponseBuilder<T>()
                    .withPayload(payload)
                    .build();
        } else if (payload instanceof Map) {
            Map<String, Object> resultMap = (Map) payload;
            tValvenResponse = new ValvenResponse.SignResponseBuilder<T>()
                    .withPayload((T) resultMap)
                    .build();

        } else {
            tValvenResponse = new ValvenResponse.SignResponseBuilder<T>()
                    .withPayload(payload)
                    .build();
        }


        if (parametersWithOrderVersionReferenceIdExtras.length > 0) {
            for (Object object : parametersWithOrderVersionReferenceIdExtras) {
             if (object instanceof String && object == parametersWithOrderVersionReferenceIdExtras[0]) {
                    tValvenResponse.setVersion((String) object);
                } else if (object instanceof String && object == parametersWithOrderVersionReferenceIdExtras[1]) {
                    tValvenResponse.setReferenceId((String) object);
                }
            }
        } else {
            tValvenResponse.setVersion(ResponseContants.SIGN_RESPONSE_VERSION);
            tValvenResponse.setReferenceId(ResponseContants.SIGN_RESPONSE_REFERENCE + ValvenUtil.formatLocalDateTimeAsString(LocalDateTime.now(), ResponseContants.SIGN_KEY_DATE_TIME_FORMAT));
        }

        return tValvenResponse;
    }

}
