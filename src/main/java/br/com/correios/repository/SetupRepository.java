package br.com.correios.repository;

import br.com.correios.model.Endereco;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SetupRepository {

    @Value("${correios.base.url}")
    private String url;
    public List<Endereco> getFromOrigin() throws Exception {
        List<Endereco> resultList = new ArrayList<>();
        String resultStr = "";

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(this.url))) {
            HttpEntity entity = response.getEntity();
            resultStr = EntityUtils.toString(entity);
        }

        for (String atual : resultStr.split("\n")) {
            String[] linhaAtualSplit = atual.split(",");

            resultList.add(Endereco.builder()
                    .estado(linhaAtualSplit[0])
                    .cidade(linhaAtualSplit[1])
                    .estado(linhaAtualSplit[2])
                    .cep(StringUtils.leftPad(linhaAtualSplit[3], 8, "0"))
                    .rua(linhaAtualSplit.length > 4 ? linhaAtualSplit[4] : null)
                    .build());
        }

        return resultList;
    }
}
