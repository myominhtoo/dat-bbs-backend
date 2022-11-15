// package com.penta.aiwmsbackend.filter;

// import java.io.IOException;
// import java.time.LocalDate;

// import javax.servlet.ServletException;
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import org.springframework.http.HttpStatus;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.security.web.access.AccessDeniedHandler;
// import org.springframework.stereotype.Component;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.penta.aiwmsbackend.model.bean.HttpResponse;

// @Component
// public class CustomAccessDeniedHandler implements AccessDeniedHandler {

//     @Override
//     public void handle(HttpServletRequest req , HttpServletResponse res ,
//         AccessDeniedException accessDeniedException) throws IOException, ServletException {
//         HttpResponse<Boolean> httpResponse = new HttpResponse<>(
//             LocalDate.now(),
//             HttpStatus.UNAUTHORIZED,
//             HttpStatus.UNAUTHORIZED.value(),
//             "Unauthorize",
//             HttpStatus.UNAUTHORIZED.getReasonPhrase(),
//             false,
//             false
//         );
//         res.setContentType("application/json");
//         res.setStatus(HttpStatus.UNAUTHORIZED.value());
//         ServletOutputStream outputStream = res.getOutputStream();
//         ObjectMapper mapper = new ObjectMapper();
//         mapper.writeValue( outputStream , httpResponse );
//         outputStream.flush();
//     }
    
// }
