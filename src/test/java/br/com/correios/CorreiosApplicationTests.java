package br.com.correios;

import br.com.correios.model.Endereco;
import br.com.correios.service.CorreiosService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.client.MockServerClient;
import org.mockserver.springtest.MockServerTest;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    @Order(1)
    public void testGetCepWhenNotRead() throws Exception {
        mockMvc.perform(get("/cep/31630900")).andExpect(status().isServiceUnavailable());
    }

    @Test
    @Order(2)
    public void testSetupOk() {
        String bodyString = "MG, Belo Horizonte, Serra Verde (Venda Nova), 31630900, Rodovia Papa JoÃ£o Paulo II,,,,,,,,,,";

        mockServerClient.when(HttpRequest.request().withPath("/ceps.csv").withMethod("GET")).respond(HttpResponse.response().withStatusCode(200).withBody(bodyString));

        correiosService.setup();
    }

    @Test
    @Order(3)
    public void testSetupNotOk() {
        mockServerClient.when(HttpRequest.request().withPath("/ceps.csv").withMethod("GET")).respond(HttpResponse.response().withStatusCode(500).withBody("ERRO"));

        Assertions.assertThrows(Exception.class, () -> correiosService.setup());
    }

    @Test
    @Order(4)
    public void testGetCepDoesntExist() throws Exception {
        mockMvc.perform(get("/cep/00000000")).andExpect(status().isNoContent());
    }

    @Test
    @Order(5)
    public void testGetCep() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/cep/31630900")).andExpect(status().isOk()).andReturn();
        String stringResult = mvcResult.getResponse().getContentAsString();

        String enderecoCompare = new ObjectMapper().writeValueAsString(
                Endereco.builder()
                        .cidade("Belo Horizonte")
                        .bairro("Serra Verde (Venda Nova")
                        .estado("MG")
                        .rua("Rodovia Papa JoÃ£o Paulo II")
                        .cep("31630900")
                        .build());

        JSONAssert.assertEquals(enderecoCompare, stringResult, false);
    }
}
