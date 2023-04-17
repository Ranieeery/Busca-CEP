package br.com.correios;

import br.com.correios.service.CorreiosService;

import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockserver.client.MockServerClient;
import org.mockserver.springtest.MockServerTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockServerTest({"correios.base.url=http://localhost:${mockServerPort}/ceps.csv"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class CorreiosApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private MockServerClient mockServerClient;

    @Autowired
    private CorreiosService correiosService;

    @Test
    public void testGetCepWhenNotRead() throws Exception {
        mockMvc.perform(get("cep/31630900")).andExpect(status().isServiceUnavailable());
    }

    public void testSetupOk() {
        String bodyString = "MG, Belo Horizonte, Serra Verde (Venda Nova), 31630900, Rodovia Papa JoÃ£o Paulo II,,,,,,,,,,";

        mockServerClient.when(HttpRequest.request().withPath("/ceps.csv").withMethod("GET")).respond(HttpResponse.response().withStatusCode(200).withBody(bodyString));
    }

    public void testSetupNotOk() {

    }

    public void testGetCepDoesntExist() {

    }

    public void testGetCep() {

    }
}
