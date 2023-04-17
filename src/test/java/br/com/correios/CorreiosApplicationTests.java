package br.com.correios;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import br.com.correios.model.Endereco;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockserver.client.MockServerClient;
import br.com.correios.service.CorreiosService;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.skyscreamer.jsonassert.JSONAssert;
import org.mockserver.springtest.MockServerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@MockServerTest({"setup.origin.url=http://localhost:${mockServerPort}/ceps.csv"})
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class CorreiosApplicationTests {

    private MockServerClient mockServerClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CorreiosService correiosService;

    @Test
    @Order(1)
    public void testGetCepWhenNotRead() throws Exception {
        mockMvc.perform(get("/cep/31630900")).andExpect(status().is(503));
    }

    @Test
    @Order(2)
    public void testSetupOk() {
        String bodyString = "MG, Belo Horizonte, Serra Verde (Venda Nova), 31630900, Rodovia Papa JoÃ£o Paulo II,,,,,,,,,,";

        mockServerClient.when(request().withPath("/ceps.csv").withMethod("GET")).respond(response().withStatusCode(200).withBody(bodyString));

        correiosService.setup();
    }

    @Test
    @Order(3)
    public void testGetCepDoesntExist() throws Exception {
        mockMvc.perform(get("/cep/00000000")).andExpect(status().is(204));
    }

    @Test
    @Order(4)
    public void testGetCep() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/cep/31630900")).andExpect(status().is(200)).andReturn();
        String stringResult = mvcResult.getResponse().getContentAsString();

        String enderecoCompare = new ObjectMapper().writeValueAsString(
                Endereco.builder()
                        .cep("31630900")
                        .rua("Rodovia Papa JoÃ\u0083Â£o Paulo II")
                        .bairro("Serra Verde (Venda Nova)")
                        .estado("MG")
                        .cidade("Belo Horizonte")
                        .build());

        JSONAssert.assertEquals(enderecoCompare, stringResult, false);
    }
}
