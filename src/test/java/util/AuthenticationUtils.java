package util;

import com.br.mhm.libraryapi.controller.auth.AuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class AuthenticationUtils {

    public static String authenticateAndGetAccessToken(MockMvc mockMvc) throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("admin@test.com", "admin");

        String requestBody = new ObjectMapper().writeValueAsString(request);

        String responseJson = mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

       return new ObjectMapper().readTree(responseJson).get("token").asText();
    }
}
