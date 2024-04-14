package com.riverburg.eUniversity.exception.handler;
import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class FilterExceptionHandler {

    public void handleException(RestException ex, HttpServletResponse response) throws IOException {

        JSONObject responseAsJSON = new JSONObject(BaseResponse
                .builder()
                .statusCode(ex.getStatusCode())
                .message(ex.getMessage())
                .build());

        response.setStatus(ex.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(responseAsJSON.toString());
    }
}
